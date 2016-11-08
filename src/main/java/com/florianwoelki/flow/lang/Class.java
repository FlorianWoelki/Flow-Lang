package com.florianwoelki.flow.lang;

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

    private final String[] code;

    public Class(String[] code)
    {
        super(null);

        this.code = code;
    }

    public void run(Console console) throws InvalidCodeException
    {
        this.methods = new ArrayList<>();

        Method currentMethod = null;

        for (String line : this.code)
        {
            line = this.trimComments(line);
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
