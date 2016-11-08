package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Class extends Block
{
    private final String[] code;

    public Class(Block superBlock, String[] code)
    {
        super(null);

        this.code = code;
    }

    public void run(Console console) throws InvalidCodeException
    {
        for (String line : this.code)
        {
            line = this.trimComments(line);
        }

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

    @Override
    protected void runAfterParse() throws InvalidCodeException
    {
    }
}
