package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.command.CommandManager;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Class extends Block
{
    private List<Method> methods;
    public CommandManager commandManager;

    private final String[] code;

    public Class(String[] code)
    {
        super(null);

        this.code = code;
    }

    public void run(Console console) throws InvalidCodeException
    {
        this.methods = new ArrayList<>();
        this.commandManager = new CommandManager(console);

        Method currentMethod = null;

        for (String line : this.code)
        {
            line = this.trimComments(line);

            if (line.startsWith("method "))
            {
                currentMethod = new Method(this, line.split(" ")[1]);
            }
            else if (currentMethod != null && line.equals("end " + currentMethod.getName()))
            {
                this.methods.add(currentMethod);

                currentMethod = null;
            }
            else if (line.startsWith("var"))
            {
                this.commandManager.parse(this, line);
            }
            else
            {
                if (currentMethod != null && !line.equals("") && !line.equals(" "))
                {
                    currentMethod.addLine(line);
                }
            }
        }

        this.getMethod("main").run();

        console.write("--Terminated.");
    }

    private String trimComments(String str)
    {
        StringBuilder fin = new StringBuilder();

        for (String word : str.split(" "))
        {
            if (word.startsWith("//"))
            {
                return fin.toString().trim();
            }
            else
            {
                fin.append(word).append(" ");
            }
        }

        return fin.toString().trim();
    }

    public Method getMethod(String name) throws InvalidCodeException
    {
        for (Method m : this.methods)
        {
            if (m.getName().equals(name))
            {
                return m;
            }
        }

        throw new InvalidCodeException("Method " + name + " does not exist.");
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException
    {
    }
}
