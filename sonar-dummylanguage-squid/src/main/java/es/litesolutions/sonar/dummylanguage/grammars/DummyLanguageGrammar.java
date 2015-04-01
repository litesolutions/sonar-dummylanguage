package es.litesolutions.sonar.dummylanguage.grammars;

import es.litesolutions.sonar.dummylanguage.tokens.Operands;
import es.litesolutions.sonar.dummylanguage.tokens.Operators;
import es.litesolutions.sonar.grappa.GrappaSslrFactory;
import es.litesolutions.sonar.grappa.lookup.EntryPoint;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

/**
 * Grammar for the dummy language
 *
 * <p>Since this plugin uses <a
 * href="https://github.com/litesolutions/sonar-sslr-grappa">sonar-sslr-grappa</a>,
 * we make use of its capabilities here and:</p>
 *
 * <ul>
 *     <li>annotate the main rule with {@link EntryPoint},</li>
 *     <li>create an injection method with the required prototype.</li>
 * </ul>
 *
 * <p>SSLR parsers are then generated using a {@link GrappaSslrFactory}.</p>
 */
public enum DummyLanguageGrammar
    implements GrammarRuleKey
{
    OPERAND,
    OPERATOR,
    EXPRESSION,

    @EntryPoint
    SOURCE,
    ;

    public static void injectInto(final LexerfulGrammarBuilder builder)
    {
        builder.rule(OPERAND).is(Operands.NUMBER).skip();

        builder.rule(OPERATOR).is(
            builder.firstOf(
                Operators.PLUS,
                Operators.MINUS,
                Operators.MULTIPLY,
                Operators.DIVIDE
            )
        ).skip();

        builder.rule(EXPRESSION).is(
            OPERAND,
            OPERATOR,
            OPERAND
        );

        builder.rule(SOURCE).is(builder.oneOrMore(EXPRESSION));
    }
}
