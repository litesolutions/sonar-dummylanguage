package es.litesolutions.sonar.dummylanguage.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import es.litesolutions.sonar.dummylanguage.grammars.DummyLanguageGrammar;
import es.litesolutions.sonar.dummylanguage.tokens.Operands;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.checks.SquidCheck;

import java.util.List;

@Rule(
    key = "ZeroCheck",
    priority = Priority.MAJOR,
    name = "Zero in expression",
    status = "BETA"
)
public final class ZeroCheck
    extends SquidCheck<Grammar>
{
    private static final String MESSAGE = "Avoid zero in expressions";

    @Override
    public void init()
    {
        subscribeTo(DummyLanguageGrammar.EXPRESSION);
    }

    @Override
    public void visitNode(final AstNode astNode)
    {
        final List<AstNode> operands
            = astNode.getChildren(Operands.NUMBER);

        boolean zeroFound = false;

        // Yeah, I know
        for (final AstNode operand: operands)
            if (Integer.parseInt(operand.getTokenValue()) == 0)
                zeroFound = true;

        if (!zeroFound)
            return;

        getContext().createLineViolation(this, MESSAGE, astNode);
    }
}
