package es.litesolutions.sonar.dummylanguage;


import com.sonar.sslr.api.Grammar;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputPath;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.indexer.QueryByType;
import sonarhack.com.google.common.collect.Lists;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Squid sensor
 *
 * <p>This is what will be called by the Sonar runner when you analyze a
 * project.</p>
 */
@SuppressWarnings("UnnecessaryFullyQualifiedName")
public final class DummyLanguageSquidSensor
    implements Sensor
{
    private static final QueryByType SOURCE_FILES
        = new QueryByType(SourceFile.class);

    private final Checks<SquidAstVisitor<Grammar>> checks;

    /*
     * Yes, unfortunately, Sonar has "taken over" a name used by the JDK here.
     */
    private final org.sonar.api.batch.fs.FileSystem fs;

    private final FilePredicate predicate;
    private final ResourcePerspectives perspectives;

    public DummyLanguageSquidSensor(final org.sonar.api.batch.fs.FileSystem fs,
        final CheckFactory checkFactory,
        final ResourcePerspectives perspectives)
    {
        this.fs = fs;
        this.perspectives = perspectives;

        predicate = fs.predicates().hasLanguage(DummyLanguageLanguage.KEY);

        checks = checkFactory.
            <SquidAstVisitor<Grammar>>create(DummyLanguageChecks.REPOSITORY_KEY)
            .addAnnotatedChecks(DummyLanguageChecks.all());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void analyse(final Project module, final SensorContext context)
    {
        final Collection<SquidAstVisitor<Grammar>> all = checks.all();
        final SquidAstVisitor[] visitors
            = all.toArray(new SquidAstVisitor[all.size()]);

        final DummyLanguageConfiguration cfg = new DummyLanguageConfiguration();
        final AstScanner<Grammar> scanner = DummyLanguageAstScanner.create(cfg,
                visitors);

        /*
         * You MUST do it this way!
         *
         * scanner.scanFile() delegates to .scanFiles(), which triggers all of
         * the visitors to .init() themselves... Therefore if you scan one by
         * one, your visitor will run once on the first file, twice on the
         * second one, etc etc...
         */
        final List<File> files = Lists.newArrayList(fs.files(predicate));

        scanner.scanFiles(files);

        for (final SourceCode code: scanner.getIndex().search(SOURCE_FILES))
            save((SourceFile) code);
    }

    private void save(final SourceFile squidFile)
    {
        final File file = new File(squidFile.getKey());
        final FilePredicate byName = fs.predicates().is(file);
        final InputFile inputFile = fs.inputFile(byName);
        final Set<CheckMessage> messages = squidFile.getCheckMessages();

        recordIssues(inputFile, messages);
    }

    private void recordIssues(final InputPath inputFile,
        final Iterable<CheckMessage> messages)
    {
        SquidAstVisitor<Grammar> visitor;
        RuleKey ruleKey;
        Issuable issuable;
        Issue issue;

        for (final CheckMessage message: messages) {
            issuable = perspectives.as(Issuable.class, inputFile);

            // Uh?
            if (issuable == null)
                continue;

            //noinspection unchecked
            visitor = (SquidAstVisitor<Grammar>) message.getCheck();
            ruleKey = checks.ruleKey(visitor);

            issue = issuable.newIssueBuilder()
                .ruleKey(ruleKey)
                .line(message.getLine())
                .message(message.getText(Locale.ENGLISH))
                .build();

            issuable.addIssue(issue);
        }
    }

    @Override
    public boolean shouldExecuteOnProject(final Project project)
    {
        return fs.hasFiles(predicate);
    }
}
