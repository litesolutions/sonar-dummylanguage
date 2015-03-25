package es.litesolutions.sonar.dummylanguage.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Grammar;
import es.litesolutions.sonar.dummylanguage.grammars.DummyLanguageGrammar;
import es.litesolutions.sonar.dummylanguage.tokens.Operands;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * The only one, dummy check
 *
 * <p>This only checks whether in an expression, the number 0 is found; nothing
 * else.</p>
 *
 * <p>For this, this checks {@link #subscribeTo(AstNodeType...) subscribes} to
 * the {@link DummyLanguageGrammar#EXPRESSION} grammar key, collects children
 * which are only of type {@link Operands#NUMBER} and uses {@link
 * Integer#parseInt(String)} to check the {@link AstNode#getTokenValue() value}
 * of the token; if found, it generates the warning.</p>
 */
@Rule(
    key = "ZeroCheck",
    priority = Priority.MAJOR,
    name = "Zero in expression",
    status = "BETA"
)
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("1min")
@ParametersAreNonnullByDefault
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
