package es.litesolutions.sonar.dummylanguage;

import es.litesolutions.sonar.SonarConstants;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;

import java.util.List;

/**
 * A {@link ProfileDefinition} using a {@link RuleFinder}
 *
 * <p>This is one way of registering your checks to a given profile. In this
 * case, the argument to the constructor is a {@link RuleFinder}.</p>
 *
 * <p>You will need to annotate your checks with {@link ActivatedByDefault} if
 * you want them to be applied by default for your profile and language.</p>
 *
 * <p>This implementation stores all the rules for that language in the default
 * quality profile</p>
 *
 * @see AnnotationBasedProfileBuilder
 * @see SonarConstants#DEFAULT_QUALITY_PROFILE
 */
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
        final String profileName = SonarConstants.DEFAULT_QUALITY_PROFILE;
        final String languageKey = DummyLanguageLanguage.KEY;

        @SuppressWarnings("rawtypes")
        final List<Class> checks = DummyLanguageChecks.all();

        return new AnnotationBasedProfileBuilder(finder).build(repositoryKey,
            profileName, languageKey, checks, messages);
    }
}
