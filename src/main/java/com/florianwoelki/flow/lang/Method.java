package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Method extends Block
{
    private final String name;
    private final Variable.VariableType returnType;
    private final String[] params;

    private Object returnValue;

    public Method(Block superBlock, String name, Variable.VariableType returnType, String[] params)
    {
        super(superBlock);

        this.registerCustomLineHandler(new CustomLineHandler("return")
        {
            public boolean run(String line, Block sB) throws InvalidCodeException
            {
                if (getReturnType() == Variable.VariableType.VOID)
                {
                    return true;
                }

                getReturnType().validateValue(line.split(" ")[1], sB);
                returnValue = FlowLang.implode(new String[]{line.split(" ")[1]}, sB);
                return true;
            }
        });

        this.name = name;
        this.returnType = returnType;
        this.params = params;
    }

    public synchronized Object invoke(Object[] invokeParams) throws InvalidCodeException
    {
        for (int i = 0; i < this.params.length; i++)
        {
            String[] args = this.params[i].split(":");
            this.addVariable(Variable.VariableType.match(args[0]), args[1], invokeParams[i]);
        }

        this.run();
        this.doBlocks();

        if (this.getReturnType() != Variable.VariableType.VOID && this.returnValue == null)
        {
            throw new InvalidCodeException("No return for method " + getName());
        }

        Object localReturnValue = this.returnValue;
        this.returnValue = null;
        return localReturnValue;
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException
    {
    }

    public String getName()
    {
        return name;
    }

    public Variable.VariableType getReturnType()
    {
        return returnType;
    }
}
