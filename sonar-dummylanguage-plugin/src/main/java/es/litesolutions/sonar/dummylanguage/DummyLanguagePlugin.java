package es.litesolutions.sonar.dummylanguage;

import org.sonar.api.SonarPlugin;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.server.rule.RulesDefinition;
import sun.management.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * A plugin for a language, with minimal analysis capabilities
 *
 * <p>You need at least the following elements:</p>
 *
 * <ul>
 *     <li>an implementation of an {@link AbstractLanguage} (here, {@link
 *     DummyLanguageLanguage};</li>
 *     <li>an implementation of a {@link Sensor} (here, {@link
 *     DummyLanguageSquidSensor});</li>
 *     <li>an implementation of a {@link RulesDefinition} (and the associated
 *     rules, of course; here, {@link DummyLanguageRulesDefinition});</li>
 *     <li>an implementation of a {@link ProfileDefinition} (there are two, but
 *     only one is used: {@link DummyLanguageProfile}; see also {@link
 *     DummyLanguageProfileAlternate}).</li>
 * </ul>
 *
 * <p>Once you have those, you need to return the <em>class</em> of each of them
 * in {@link #getExtensions()}.</p>
 *
 * <p><strong>CAUTION:</strong> dependencies are all injected via inversion of
 * control; the arguments you will need to pass to this or that component are
 * entirely dependent on what you want to do. See the javadoc of the respective
 * classes above for a tentative explanation; I don't fully understand it all
 * yet...</p>
 */
public final class DummyLanguagePlugin
    extends SonarPlugin
{
    @Override
    public List<?> getExtensions()
    {
        return Arrays.asList(
            DummyLanguageLanguage.class,

            DummyLanguageSquidSensor.class,

            DummyLanguageRulesDefinition.class,

            DummyLanguageProfile.class
            //DummyLanguageDefaultProfile.class
        );
    }
}
