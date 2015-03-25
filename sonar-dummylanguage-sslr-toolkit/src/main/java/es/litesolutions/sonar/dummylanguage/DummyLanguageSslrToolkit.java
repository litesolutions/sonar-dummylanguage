package es.litesolutions.sonar.dummylanguage;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import es.litesolutions.sonar.dummylanguage.grammars.DummyLanguageGrammar;
import es.litesolutions.sonar.dummylanguage.parsers.DummyLanguageParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.sonar.colorizer.Tokenizer;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.sonar.sslr.toolkit.Toolkit;

import java.util.Collections;
import java.util.List;

public final class DummyLanguageSslrToolkit
{
    private DummyLanguageSslrToolkit()
    {
        throw new Error("nice try!");
    }

    public static void main(final String... args)
    {
        final DummyLanguageParser parser
            = Parboiled.createParser(DummyLanguageParser.class);

        final Rule rule = parser.expression();
        final DummyLanguageGrammar key = DummyLanguageGrammar.EXPRESSION;

        final Parser<Grammar> grammarParser
            = DummyLanguageSslrParser.buildSslrParser(rule, key);

        final Toolkit toolkit = new Toolkit("foo",
            new DummyConfigurationModel(grammarParser));

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
