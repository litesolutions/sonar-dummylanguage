package es.litesolutions.sonar.dummylanguage;

import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

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

            DummyLanguageProfile.class,
            DummyLanguageDefaultProfile.class
        );
    }
}
