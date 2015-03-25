package es.litesolutions.sonar.dummylanguage.tokens;

import com.sonar.sslr.api.AstNode;
import es.litesolutions.sonar.grappa.ValueTokenType;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum  Operators
    implements ValueTokenType
{
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    ;

    private final String value;

    Operators(final String value)
    {
        this.value = value;
    }

    @Override
    public String getName()
    {
        return name();
    }

    @Override
    public String getValue()
    {
        return value;
    }

    @Override
    public boolean hasToBeSkippedFromAst(final AstNode node)
    {
        return false;
    }
}
