package com.florianwoelki.flow.command;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Print extends Command
{
    public Print()
    {
        super("print");
    }

    @Override
    public void run(Console console, Block block, String[] args) throws InvalidCodeException
    {
        console.write(FlowLang.implode(args, block));
    }
}
