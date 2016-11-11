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
public class Var extends Function
{
    public Var()
    {
        super("var");
    }

    @Override
    public void run(Console console, Block block, String[] args) throws InvalidCodeException
    {
        Variable.VariableType t = Variable.VariableType.match(args[0]);
        String name = args[1];

        Object value = null;

        if (args.length >= 4)
        {
            if (t == Variable.VariableType.STRING)
            {
                value = FlowLang.implode(Arrays.copyOfRange(args, 3, args.length), block);
            }
            else
            {
                t.validateValue(args[3], block);
                value = args[3];
            }
        }

        block.addVariable(t, name, value);
    }
}
