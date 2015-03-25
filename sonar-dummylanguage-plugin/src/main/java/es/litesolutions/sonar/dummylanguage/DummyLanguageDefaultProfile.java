package es.litesolutions.sonar.dummylanguage;

import org.sonar.api.profiles.AnnotationProfileParser;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;

public final class DummyLanguageDefaultProfile
    extends ProfileDefinition
{
    private final AnnotationProfileParser parser;

    public DummyLanguageDefaultProfile(final AnnotationProfileParser parser)
    {
        this.parser = parser;
    }

    @Override
    public RulesProfile createProfile(final ValidationMessages messages)
    {
        final String repositoryKey = DummyLanguageChecks.REPOSITORY_KEY;
        final String profileName = DummyLanguageChecks.SONAR_WAY_PROFILE;
        final String languageKey = DummyLanguageLanguage.KEY;

        return parser.parse(repositoryKey, profileName, languageKey,
            DummyLanguageChecks.all(), messages);
    }
}
