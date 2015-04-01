package es.litesolutions.sonar.dummylanguage;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import es.litesolutions.sonar.dummylanguage.grammars.DummyLanguageGrammar;
import es.litesolutions.sonar.dummylanguage.metrics.DummyLanguageMetrics;
import es.litesolutions.sonar.dummylanguage.parsers.DummyLanguageParser;
import es.litesolutions.sonar.grappa.GrappaSslrFactory;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.CommentAnalyser;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.SquidAstVisitorContextImpl;
import org.sonar.squidbridge.api.SourceProject;

import javax.annotation.ParametersAreNonnullByDefault;

public final class DummyLanguageAstScanner
{
    private DummyLanguageAstScanner()
    {
        throw new Error("nice try!");
    }

    /**
     * Create an AstScanner
     *
     * @param cfg the configuration (unused at the moment)
     * @param visitors the list of squid AST visitors (checks? Others?)
     * @return an AST scanner
     */
    @SafeVarargs
    public static AstScanner<Grammar> create(
        final DummyLanguageConfiguration cfg,
        final SquidAstVisitor<Grammar>... visitors)
    {
        final SourceProject project = new SourceProject("ObjectScript project");
        final SquidAstVisitorContextImpl<Grammar> context
            = new SquidAstVisitorContextImpl<>(project);

        final GrappaSslrFactory factory = GrappaSslrFactory.builder(
            DummyLanguageParser.class, DummyLanguageGrammar.class
        ).build();
        final Parser<Grammar> parser = factory.getFullParser();

        final AstScanner.Builder<Grammar> builder = AstScanner.builder(context)
            .setBaseParser(parser);

        for (final SquidAstVisitor<Grammar> visitor: visitors)
            builder.withSquidAstVisitor(visitor);

        builder.setCommentAnalyser(new DummyCommentAnalyzer());

        builder.setFilesMetric(DummyLanguageMetrics.FILES);

        return builder.build();
    }

    @ParametersAreNonnullByDefault
    private static final class DummyCommentAnalyzer
        extends CommentAnalyser
    {
        @Override
        public boolean isBlank(final String line)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getContents(final String comment)
        {
            throw new UnsupportedOperationException();
        }
    }
}
