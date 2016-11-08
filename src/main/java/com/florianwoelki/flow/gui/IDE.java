package com.florianwoelki.flow.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class IDE extends JFrame
{
    private final JTextPane text;

    public IDE()
    {
        super("Flow - IDE");

        this.text = new JTextPane();
        this.text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JButton run = new JButton("Run");
        run.addActionListener((event) -> new Console()); // TODO: Add Class

        JButton save = new JButton("Save");
        save.addActionListener((event) ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Flow Code", "flow"));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(chooser.getSelectedFile().getAbsolutePath() + ".flow")));

                    for (String line : this.text.getText().split("\n"))
                    {
                        writer.write(line + "\n");
                    }

                    writer.close();
                }
                catch (IOException e)
                {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });

        JButton load = new JButton("Load");
        load.addActionListener((event) ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Flow Code", "flow"));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                this.text.setText("");

                try
                {
                    BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));

                    while (reader.ready())
                    {
                        this.text.getDocument().insertString(this.text.getDocument().getLength(), reader.readLine() + "\n", null);
                    }

                    reader.close();
                }
                catch (Exception e)
                {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(this.text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        JPanel toolBar = new JPanel();
        toolBar.setMaximumSize(new Dimension(640, 30));
        toolBar.add(run);
        toolBar.add(save);
        toolBar.add(load);

        this.add(toolBar);
        this.add(scroll);

        this.setSize(640, 480);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
