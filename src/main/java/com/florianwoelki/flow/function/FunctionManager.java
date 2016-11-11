package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class FunctionManager
{
    private final List<Function> functions = new ArrayList<>();

    private final Console console;

    public FunctionManager(Console console)
    {
        this.console = console;

        this.functions.add(new Print());
        this.functions.add(new Var());
        this.functions.add(new Random());
        this.functions.add(new GetInput());
        this.functions.add(new RunMethod());
    }

    public void parse(Block block, String input) throws InvalidCodeException
    {
        String[] all = input.split(" ");
        String cmd = all[0];
        String[] args = Arrays.copyOfRange(all, 1, all.length);

        Function function = null;

        for (Function f : this.functions)
        {
            if (f.getName().equals(cmd))
            {
                function = f;
            }
        }

        if (function == null)
        {
            throw new InvalidCodeException("Function " + cmd + " does not exist.");
        }
        else
        {
            function.run(this.console, block, args);
        }
    }
}
