package com.florianwoelki.flow.function;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

import java.util.Arrays;

/**
 * Created by Florian Woelki on 11.11.16.
 */
public class Set extends Function
{
    public Set()
    {
        super("set");
    }

    @Override
    public void run(Console console, Block block, String[] args) throws InvalidCodeException
    {
        Variable v = block.getVariable(args[0]);

        if (v.getType() != Variable.VariableType.STRING)
        {
            v.getType().validateValue(args[2], block);
            v.setValue(args[2]);
        }
        else
        {
            v.setValue(FlowLang.implode(Arrays.copyOfRange(args, 2, args.length), block));
        }
    }
}
