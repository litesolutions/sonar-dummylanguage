package es.litesolutions.sonar.dummylanguage.grammars;

import es.litesolutions.sonar.dummylanguage.tokens.Operands;
import es.litesolutions.sonar.dummylanguage.tokens.Operators;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

public enum DummyLanguageGrammar
    implements GrammarRuleKey
{
    OPERAND,
    OPERATOR,
    EXPRESSION,
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
    }
}
