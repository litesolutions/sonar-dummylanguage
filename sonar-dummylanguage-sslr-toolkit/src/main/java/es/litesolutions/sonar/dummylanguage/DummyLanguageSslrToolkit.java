package es.litesolutions.sonar.dummylanguage;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import es.litesolutions.sonar.dummylanguage.grammars.DummyLanguageGrammar;
import es.litesolutions.sonar.dummylanguage.parsers.DummyLanguageParser;
import es.litesolutions.sonar.grappa.GrappaSslrFactory;
import org.sonar.colorizer.Tokenizer;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.sonar.sslr.toolkit.Toolkit;

import java.util.Collections;
import java.util.List;

/**
 * SSLR toolkit for the language
 *
 * <p>In order to only test parts of your grammar, change the method call to the
 * constructed {@link GrappaSslrFactory}.</p>
 */
public final class DummyLanguageSslrToolkit
{
    private DummyLanguageSslrToolkit()
    {
        throw new Error("nice try!");
    }

    public static void main(final String... args)
    {
        final GrappaSslrFactory factory = GrappaSslrFactory.builder(
            DummyLanguageParser.class, DummyLanguageGrammar.class
        ).build();

        final Parser<Grammar> parser = factory.getFullParser();

        final DummyConfigurationModel model
            = new DummyConfigurationModel(parser);

        final Toolkit toolkit = new Toolkit("foo", model);

        toolkit.run();
    }

    private static final class DummyConfigurationModel
        extends AbstractConfigurationModel
    {
        private final Parser<Grammar> parser;

        private DummyConfigurationModel(final Parser<Grammar> parser)
        {
            this.parser = parser;
        }

        @Override
        public Parser doGetParser()
        {
            return parser;
        }

        @Override
        public List<Tokenizer> doGetTokenizers()
        {
            return Collections.emptyList();
        }

        @Override
        public List<ConfigurationProperty> getProperties()
        {
            return Collections.emptyList();
        }
    }
}
