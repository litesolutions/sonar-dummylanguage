package es.litesolutions.sonar.dummylanguage.parsers;

import com.github.fge.grappa.rules.Rule;
import es.litesolutions.sonar.dummylanguage.tokens.Operands;
import es.litesolutions.sonar.dummylanguage.tokens.Operators;
import es.litesolutions.sonar.grappa.GrappaChannel;
import es.litesolutions.sonar.grappa.GrappaSslrFactory;
import es.litesolutions.sonar.grappa.SonarParserBase;
import es.litesolutions.sonar.grappa.lookup.MainRule;

/**
 * Grappa parser for the full language
 *
 * <p>Modify this parser if you want to parse more of the language etc.</p>
 *
 * <p>Note that the process is iterative; start with something simple then add
 * rules (and tokens/grammar keys) as your language evolves.</p>
 *
 * <p>An instance of this parser needs to be instantiated by your SSLR parser;
 * this uses a {@link GrappaSslrFactory} to do the job.</p>
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
            token(Operators.PLUS),
            token(Operators.MINUS),
            token(Operators.MULTIPLY),
            token(Operators.DIVIDE)
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

    @MainRule
    public Rule sourcefile()
    {
        return sequence(
            join(oneLine()).using(nl()).min(1),
            optional(nl())
        );
    }
}
