package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Method extends Block {

    private final String name;
    private final Variable.VariableType returnType;
    private final String[] params;

    private Object returnValue;

    public Method(Block superBlock, String name, Variable.VariableType returnType, String[] params) {
        super(superBlock);

        registerCustomLineHandler(new CustomLineHandler("return") {
            public boolean run(String line, Block sB) throws InvalidCodeException {
                if(getReturnType() == Variable.VariableType.VOID) {
                    return true;
                }

                getReturnType().validateValue(line.split(" ")[1], sB);
                returnValue = FlowLang.implode(line.split(" ")[1], sB);
                return true;
            }
        });

        this.name = name;
        this.returnType = returnType;
        this.params = params;
    }

    public synchronized Object invoke(Object[] invokeParams) throws InvalidCodeException {
        for(int i = 0; i < params.length; i++) {
            String[] args = params[i].split(":");
            addVariable(Variable.VariableType.match(args[0]), args[1], invokeParams[i]);
        }

        run();
        doBlocks();

        if(getReturnType() != Variable.VariableType.VOID && returnValue == null) {
            throw new InvalidCodeException("No return for method " + getName());
        }

        Object localReturnValue = returnValue;
        returnValue = null;
        return localReturnValue;
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException {
    }

    public String getName() {
        return name;
    }

    public Variable.VariableType getReturnType() {
        return returnType;
    }

}
