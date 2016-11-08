package com.florianwoelki.flow;

import com.florianwoelki.flow.gui.IDE;

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
}
