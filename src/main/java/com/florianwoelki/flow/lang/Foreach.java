package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 23.07.17.
 */
public class Foreach extends Block {

    private final String varName, arrayName;

    public Foreach(Block superBlock, String varName, String arrayName) {
        super(superBlock);
        this.varName = varName;
        this.arrayName = arrayName;
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException {
        Variable arrayVar = getSuperBlock().getVariable(arrayName);

        if(!arrayVar.isArray()) {
            throw new InvalidCodeException("Attempted to use foreach on non-array.");
        }

        addVariable(arrayVar.getType(), varName, false);

        for(Object value : arrayVar.getValues()) {
            getVariable(varName).setValue(value);
            doBlocks();
        }
    }

}
