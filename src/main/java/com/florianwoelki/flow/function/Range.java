package com.florianwoelki.flow.function;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 19.11.17.
 */
public class Range extends Function {

    public Range() {
        super("range");
    }

    /*
    Usage: range(<name>, <start value>, <end value>)
     */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException {
        Variable.VariableType variableType = Variable.VariableType.STRING;
        String name = args[0];

        int startValue;
        int endValue;

        try {
            startValue = Integer.parseInt(args[1]);
            endValue = Integer.parseInt(args[2]);
        } catch(Exception e) {
            throw new InvalidCodeException("Wrong or missing start value and end value. Both need to be integers.");
        }

        StringBuilder rangeBuilder = new StringBuilder();
        for(int i = startValue; i < endValue; i++) {
            if(i + 1 == endValue) {
                rangeBuilder.append("").append(i);
            } else {
                rangeBuilder.append("").append(i).append(",");
            }
        }

        variableType.validateValue(rangeBuilder.toString(), block);
        block.addVariable(variableType, name, true, rangeBuilder.toString());
    }

}
