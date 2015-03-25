package es.litesolutions.sonar.dummylanguage;

import es.litesolutions.sonar.SonarConstants;
import org.sonar.api.profiles.AnnotationProfileParser;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.check.BelongsToProfile;

/**
 * Another way to build a profile definition for your language
 *
 * <p>In this case, your checks need to be annotated with {@link
 * BelongsToProfile}. The {@link AnnotationProfileParser} passed as an argument
 * is also used to return the list of rules.</p>
 */
public final class DummyLanguageProfileAlternate
    extends ProfileDefinition
{
    private final AnnotationProfileParser parser;

    public DummyLanguageProfileAlternate(final AnnotationProfileParser parser)
    {
        this.parser = parser;
    }

    @Override
    public RulesProfile createProfile(final ValidationMessages messages)
    {
        final String repositoryKey = DummyLanguageChecks.REPOSITORY_KEY;
        final String profileName = SonarConstants.DEFAULT_QUALITY_PROFILE;
        final String languageKey = DummyLanguageLanguage.KEY;

        return parser.parse(repositoryKey, profileName, languageKey,
            DummyLanguageChecks.all(), messages);
    }
}
