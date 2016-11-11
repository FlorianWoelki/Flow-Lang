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

    @Override
    public void run(Console console, Block block, String[] args) throws InvalidCodeException
    {
        Variable v = block.getVariable(args[0]);
        String input = console.prompt();

        v.getType().validateValue(input, block);
        v.setValue(input);
    }
}
