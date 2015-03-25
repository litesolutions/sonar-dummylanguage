package es.litesolutions.sonar.dummylanguage;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.Parser;
import es.litesolutions.sonar.dummylanguage.grammars.DummyLanguageGrammar;
import es.litesolutions.sonar.dummylanguage.parsers.DummyLanguageParser;
import es.litesolutions.sonar.grappa.GrappaChannel;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

/**
 * Dummy language SSLR parser
 */
public final class DummyLanguageSslrParser
{
    private static final GrammarRuleKey FULL_GRAMMAR_KEY
        = DummyLanguageGrammar.SOURCE;

    private DummyLanguageSslrParser()
    {
        throw new Error("nice try!");
    }

    /**
     * Return the grammar builder for the language's grammar
     *
     * <p>This method injects the language grammar into the returned builder;
     * what it <strong>will not</strong> do, however, is {@link
     * LexerfulGrammarBuilder#setRootRule(GrammarRuleKey) set the root rule}.
     * </p>
     *
     * <p>This is what will allow you, along with selecting the appropriate rule
     * from your {@link DummyLanguageParser parser}, to only test parts of your
     * grammar using the toolkit.</p>
     *
     * @return see description
     */
    public static LexerfulGrammarBuilder getGrammarBuilder()
    {
        final LexerfulGrammarBuilder builder = LexerfulGrammarBuilder.create();
        DummyLanguageGrammar.injectInto(builder);
        return builder;
    }

    /**
     * Return the grappa parser for that language
     *
     * @return see description
     */
    public static DummyLanguageParser getParser()
    {
        return Parboiled.createParser(DummyLanguageParser.class);
    }

    /**
     * Build an SSLR parser for a particular (Grappa) rule and grammar key
     *
     * @param rule the parser rule
     * @param key the grammar key
     * @return a new SSLR parser
     */
    public static Parser<Grammar> buildSslrParser(final Rule rule,
        final GrammarRuleKey key)
    {
        final LexerfulGrammarBuilder builder = getGrammarBuilder();
        builder.setRootRule(key);

        final GrappaChannel channel = new GrappaChannel(rule);

        final Lexer lexer = Lexer.builder()
            .withFailIfNoChannelToConsumeOneCharacter(true)
            .withChannel(channel)
            .build();

        return Parser.builder(builder.build())
            .withLexer(lexer)
            .build();
    }

    /**
     * Return a full fledged SSLR parser (parse a full source)
     *
     * <p>This is equivalent to calling {@link #buildSslrParser(Rule,
     * GrammarRuleKey)} with {@link DummyLanguageParser#expression()} as the
     * rule and {@link DummyLanguageGrammar#EXPRESSION} as the key.</p>
     *
     * @return see description
     */
    public static Parser<Grammar> getFullParser()
    {
        final DummyLanguageParser parser = getParser();
        return buildSslrParser(parser.sourcefile(), FULL_GRAMMAR_KEY);
    }
}
