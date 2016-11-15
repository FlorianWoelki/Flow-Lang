package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 09.11.16.
 */
public class GetInput extends Function
{
    public GetInput()
    {
        super("getinput");
    }

    /*
    Usage: getinput() <var>
     */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException
    {
        String input = console.prompt();

        if (receiver != null)
        {
            receiver.getType().validateValue(input, block);
            receiver.setValue(input);
        }
    }
}
