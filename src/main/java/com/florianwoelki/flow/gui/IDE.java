package com.florianwoelki.flow.gui;

import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.text.WebTextPane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class IDE extends WebFrame {

    public static final String[] FLOW_KEYWORDS = new String[]{"declare", "getinput", "print", "random", "set", "method", "void", "for", "end", "integer", "while", "if", "elseif", "else", "string", "boolean"};
    public static final String FLOW_KEYWORDS_REGEX;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    static {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("(");
        for(String keyword : FLOW_KEYWORDS) {
            stringBuilder.append("\\b").append(keyword).append("\\b").append("|");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(")");
        FLOW_KEYWORDS_REGEX = stringBuilder.toString();
    }

    private final Console console;
    private final WebTextPane text;
    private final Preferences prefs;
    private StyledDocument textEditorDoc;

    public IDE() {
        super("Flow - IDE");

        text = new WebTextPane();
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                clearTextColors();
                Pattern pattern = Pattern.compile(FLOW_KEYWORDS_REGEX);
                Matcher matcher = pattern.matcher(text.getText());
                while(matcher.find()) {
                    updateTextColor(matcher.start(), matcher.end() - matcher.start());
                }
            }
        });
        text.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
        textEditorDoc = text.getStyledDocument();

        WebScrollPane scroll = new WebScrollPane(text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        console = new Console();

        prefs = new Preferences(this);

        WebScrollPane consoleScroll = new WebScrollPane(console);
        consoleScroll.setBorder(null);
        consoleScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        WebSplitPane split = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, scroll, consoleScroll);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(860);

        add(split);

        WebMenuBar menuBar = new WebMenuBar();
        WebMenu menu = new WebMenu("File");
        WebMenuItem run = new WebMenuItem("Run"), save = new WebMenuItem("Save"), load = new WebMenuItem("Load"), preferences = new WebMenuItem("Preferences");

        menuBar.add(menu);

        menu.add(run);
        menu.add(save);
        menu.add(load);
        menu.addSeparator();
        menu.add(preferences);

        setJMenuBar(menuBar);

        int meta = System.getProperty("os.name").startsWith("Mac") ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK;

        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, meta));
        run.addActionListener((e) -> console.run(new com.florianwoelki.flow.lang.Class(text.getText().split("\n"))));

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, meta));
        save.addActionListener((event) -> {
            WebFileChooser chooser = new WebFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Flow Code", "flow"));
            chooser.setFileSelectionMode(WebFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);

            if(chooser.showSaveDialog(this) == WebFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(chooser.getSelectedFile().getAbsolutePath() + ".flow")));

                    for(String line : text.getText().split("\n")) {
                        writer.write(line + "\n");
                    }

                    writer.close();
                } catch(IOException e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });

        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, meta));
        load.addActionListener((event) -> {
            WebFileChooser chooser = new WebFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Flow Code", "flow"));
            chooser.setFileSelectionMode(WebFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);

            if(chooser.showOpenDialog(this) == WebFileChooser.APPROVE_OPTION) {
                text.setText("");

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));

                    while(reader.ready()) {
                        text.getDocument().insertString(text.getDocument().getLength(), reader.readLine() + "\n", null);
                    }

                    reader.close();
                } catch(Exception e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }

                clearTextColors();
                Pattern pattern = Pattern.compile(FLOW_KEYWORDS_REGEX);
                Matcher matcher = pattern.matcher(text.getText());
                while(matcher.find()) {
                    updateTextColor(matcher.start(), matcher.end() - matcher.start());
                }
            }
        });

        preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, meta));
        preferences.setEnabled(true);
        preferences.addActionListener((e) -> {
            WebOptionPane.showMessageDialog(IDE.this, prefs, "Preferences", WebOptionPane.PLAIN_MESSAGE);
        });

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateTextColor(int offset, int length, Color color) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        textEditorDoc.setCharacterAttributes(offset, length, attributeSet, true);
    }

    public void clearTextColors() {
        updateTextColor(0, text.getText().length(), Color.BLACK);
    }

    public void updateTextColor(int offset, int length) {
        updateTextColor(offset, length, Color.BLUE);
    }

    public void setIDEFont(Font font) {
        text.setFont(font);
    }

}
