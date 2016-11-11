package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;

/**
 * Created by Florian Woelki on 11.11.16.
 */
public class RunMethod extends Function
{
    public RunMethod()
    {
        super("run");
    }

    @Override
    public void run(Console console, Block block, String[] args) throws InvalidCodeException
    {
        System.out.println(((com.florianwoelki.flow.lang.Class) block.getBlockTree()[0]).getMethod(args[0]).getName());
        ((com.florianwoelki.flow.lang.Class) block.getBlockTree()[0]).getMethod(args[0]).run();
    }
}
