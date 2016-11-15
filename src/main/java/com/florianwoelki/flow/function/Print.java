package com.florianwoelki.flow.function;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Print extends Function
{
    public Print()
    {
        super("print");
    }

    /*
    Usage: print(<message>)
     */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException
    {
        console.write(FlowLang.implode(new String[]{args[0]}, block));
    }
}
