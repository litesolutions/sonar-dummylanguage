package es.litesolutions.sonar.dummylanguage.parsers;

import es.litesolutions.sonar.dummylanguage.tokens.Operands;
import es.litesolutions.sonar.dummylanguage.tokens.Operators;
import es.litesolutions.sonar.grappa.SonarParserBase;
import org.parboiled.Rule;

@SuppressWarnings("AutoBoxing")
public class DummyLanguageParser
    extends SonarParserBase
{
    Rule spaces()
    {
        return zeroOrMore(wsp());
    }

    public Rule operand()
    {
        return sequence(oneOrMore(digit()), pushToken(Operands.NUMBER));
    }

    public Rule operator()
    {
        return firstOf(
            valueToken(Operators.PLUS),
            valueToken(Operators.MINUS),
            valueToken(Operators.MULTIPLY),
            valueToken(Operators.DIVIDE)
        );
    }

    public Rule expression()
    {
        return sequence(
            operand(),
            spaces(),
            operator(),
            spaces(),
            operand()
        );
    }

    Rule nl()
    {
        return oneOrMore(optional(cr()), lf());
    }

    Rule comment()
    {
        return sequence('#', zeroOrMore(noneOf("\r\n")));
    }

    Rule oneLine()
    {
        return firstOf(expression(), comment());
    }

    public Rule sourcefile()
    {
        return sequence(
            join(oneLine()).using(nl()).min(1),
            optional(nl())
        );
    }
}
