package es.litesolutions.sonar.dummylanguage.tokens;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum Operands
    implements TokenType
{
    NUMBER,
    ;

    @Override
    public String getName()
    {
        return name();
    }

    @Override
    public String getValue()
    {
        return name();
    }

    @Override
    public boolean hasToBeSkippedFromAst(final AstNode node)
    {
        return false;
    }
}
