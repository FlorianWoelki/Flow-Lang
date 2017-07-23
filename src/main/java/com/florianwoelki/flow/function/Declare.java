package com.florianwoelki.flow.function;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

import java.util.Arrays;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Declare extends Function {

    public Declare() {
        super("declare");
    }

    /*
    Usage: declare(<type>([]), <varname>, [value])
     */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException {
        boolean isArray = args[0].endsWith("[]");

        if(isArray) {
            args[0] = args[0].substring(0, args[0].length() - 2);
        }

        Variable.VariableType t = Variable.VariableType.match(args[0]);

        if(t == Variable.VariableType.VOID) {
            throw new InvalidCodeException("Attempted to declare void variable.");
        }

        String name = args[1];

        Object value = null;

        if(args.length >= 3) {
            if(t == Variable.VariableType.STRING) {
                value = FlowLang.implode(args[2], block);
            } else {
                t.validateValue(args[2], block);
                value = args[2];
            }
        }

        block.addVariable(t, name, isArray, value);
    }

}
