package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Random extends Function
{
    private java.util.Random random;

    public Random()
    {
        super("random");
    }

    /**
     * random varname [ceiling]
     */
    @Override
    public void run(Console console, Block block, String[] args) throws InvalidCodeException
    {
        if (this.random == null)
        {
            this.random = new java.util.Random();

            Variable v = block.getVariable(args[0]);

            if (v.getType() != Variable.VariableType.INTEGER)
            {
                throw new InvalidCodeException("Attempted to assign random number to non-integer.");
            }

            int ceil = -1;

            if (args.length == 2)
            {
                try
                {
                    ceil = Integer.parseInt(args[1]);
                }
                catch (Exception e)
                {
                    throw new InvalidCodeException("Invalid ceiling.");
                }
            }

            if (ceil == -1)
            {
                v.setValue(this.random.nextInt());
            }
            else
            {
                v.setValue(this.random.nextInt(ceil));
            }
        }
    }
}
