package es.litesolutions.sonar.dummylanguage;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;

import java.util.List;

public final class DummyLanguageProfile
    extends ProfileDefinition
{
    private final RuleFinder finder;

    public DummyLanguageProfile(final RuleFinder finder)
    {
        this.finder = finder;
    }

    @Override
    public RulesProfile createProfile(final ValidationMessages messages)
    {
        final String repositoryKey = DummyLanguageChecks.REPOSITORY_KEY;
        final String profileName = DummyLanguageChecks.SONAR_WAY_PROFILE;
        final String languageKey = DummyLanguageLanguage.KEY;

        @SuppressWarnings("rawtypes")
        final List<Class> checks = DummyLanguageChecks.all();

        return new AnnotationBasedProfileBuilder(finder).build(repositoryKey,
            profileName, languageKey, checks, messages);
    }
}
