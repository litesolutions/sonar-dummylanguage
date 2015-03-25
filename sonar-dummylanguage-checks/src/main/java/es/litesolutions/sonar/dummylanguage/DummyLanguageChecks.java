package es.litesolutions.sonar.dummylanguage;

import es.litesolutions.sonar.dummylanguage.checks.ZeroCheck;

import java.util.Collections;
import java.util.List;

/**
 * The list of checks
 */
public final class DummyLanguageChecks
{
    public static final String REPOSITORY_KEY = "dummylanguage";

    private DummyLanguageChecks()
    {
        throw new Error("nice try!");
    }

    @SuppressWarnings("rawtypes")
    public static List<Class> all()
    {
        return Collections.<Class>singletonList(ZeroCheck.class);
    }
}
