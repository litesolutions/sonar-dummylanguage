package es.litesolutions.sonar.dummylanguage.parsers;

import es.litesolutions.sonar.dummylanguage.DummyLanguageSslrParser;
import es.litesolutions.sonar.dummylanguage.tokens.Operands;
import es.litesolutions.sonar.dummylanguage.tokens.Operators;
import es.litesolutions.sonar.grappa.GrappaChannel;
import es.litesolutions.sonar.grappa.SonarParserBase;
import org.parboiled.Rule;

/**
 * Grappa parser for the full language
 *
 * <p>Modify this parser if you want to parse more of the language etc.</p>
 *
 * <p>Note that the process is iterative; start with something simple then add
 * rules (and tokens/grammar keys) as your language evolves.</p>
 *
 * <p>An instance of this parser needs to be instantiated by your SSLR parser;
 * for this language, the class is {@link DummyLanguageSslrParser}.</p>
 *
 * <p>Note: the channel using this parser is a {@link GrappaChannel}; it will
 * generate a trace file which you can analyze using the <a
 * href="https://github.com/fge/grappa-debugger>Grappa debugger</a>. The path is
 * currently <strong>hardcoded</strong> to {@code /tmp/trace.zip}.</p>
 */
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
