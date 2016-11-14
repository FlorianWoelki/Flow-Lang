package com.florianwoelki.flow;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.IDE;
import com.florianwoelki.flow.lang.Block;

import javax.swing.*;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class FlowLang
{
    public static void main(String[] args)
    {
        Thread.setDefaultUncaughtExceptionHandler((thread, e) ->
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        });

        new IDE();
    }

    public static String implode(String[] strs, Block block) throws InvalidCodeException
    {
        StringBuilder builder = new StringBuilder();

        boolean inQuotes = false;

        for (String str : strs)
        {
            if (str.startsWith("\""))
            {
                inQuotes = true;
            }

            if (inQuotes)
            {
                builder.append(str.replaceAll("\"", ""));
            }
            else
            {
                if (block != null)
                {
                    try
                    {
                        builder.append(block.getVariable(str).getValue());
                    }
                    catch (InvalidCodeException e)
                    {
                        builder.append(str);
                    }
                }
            }

            builder.append(" ");

            if (str.endsWith("\""))
            {
                inQuotes = false;
            }
        }

        return builder.toString().trim();
    }
}
