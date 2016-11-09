package com.florianwoelki.flow.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class IDE extends JFrame
{
    private final Console console;
    private final JTextPane text;

    public IDE()
    {
        super("Flow - IDE");

        this.text = new JTextPane();
        this.text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(this.text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        this.console = new Console();

        JScrollPane consoleScroll = new JScrollPane(this.console);
        consoleScroll.setBorder(null);
        consoleScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, consoleScroll);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(320);

        this.add(split);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem run = new JMenuItem("Run"), save = new JMenuItem("Save"), load = new JMenuItem("Load");

        menuBar.add(menu);

        menu.add(run);
        menu.add(save);
        menu.add(load);

        this.setJMenuBar(menuBar);

        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.META_DOWN_MASK));
        run.addActionListener((e) -> this.console.run(new com.florianwoelki.flow.lang.Class(this.text.getText().split("\n"))));

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.META_DOWN_MASK));
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

        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.META_DOWN_MASK));
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

        this.setSize(640, 480);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
