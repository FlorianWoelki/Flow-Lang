package com.florianwoelki.flow.command;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public abstract class Command
{
    private final String name;

    public Command(String name)
    {
        this.name = name;
    }

    public abstract void run(Console console, Block block, String[] args) throws InvalidCodeException;

    public String getName()
    {
        return this.name;
    }
}
