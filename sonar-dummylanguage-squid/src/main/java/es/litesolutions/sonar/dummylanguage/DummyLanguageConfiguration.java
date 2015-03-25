package es.litesolutions.sonar.dummylanguage;

import org.sonar.squidbridge.api.SquidConfiguration;

import java.nio.charset.StandardCharsets;

/**
 * Configuration for the AST scanner
 *
 * <p>At least I think so.</p>
 */
public final class DummyLanguageConfiguration
    extends SquidConfiguration
{
    public DummyLanguageConfiguration()
    {
        super(StandardCharsets.UTF_8);
    }
}
