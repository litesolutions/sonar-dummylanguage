package es.litesolutions.sonar.dummylanguage;

import org.sonar.api.resources.AbstractLanguage;

/**
 * Language definition
 */
public final class DummyLanguageLanguage
    extends AbstractLanguage
{
    public static final String KEY = "dummylanguage";
    private static final String[] DEFAULT_SUFFIXES = { "dummy" };

    public DummyLanguageLanguage()
    {
        super(KEY, "dummylanguage");
    }
    @Override
    public String[] getFileSuffixes()
    {
        return DEFAULT_SUFFIXES;
    }
}
