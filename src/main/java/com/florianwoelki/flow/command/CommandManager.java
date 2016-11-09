package com.florianwoelki.flow.command;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class CommandManager
{
    private final List<Command> commands = new ArrayList<>();

    private final Console console;

    public CommandManager(Console console)
    {
        this.console = console;

        this.commands.add(new Print());
        this.commands.add(new Var());
        this.commands.add(new Random());
        this.commands.add(new GetInput());
    }

    public void parse(Block block, String input) throws InvalidCodeException
    {
        String[] all = input.split(" ");
        String cmd = all[0];
        String[] args = Arrays.copyOfRange(all, 1, all.length);

        Command command = null;

        for (Command cm : this.commands)
        {
            if (cm.getName().equals(cmd))
            {
                command = cm;
            }
        }

        if (command == null)
        {
            throw new InvalidCodeException("Function " + cmd + " does not exist.");
        }
        else
        {
            command.run(this.console, block, args);
        }
    }
}
