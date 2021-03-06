package com.florianwoelki.flow.gui;

import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Florian Woelki on 15.11.16.
 */
public class Preferences extends WebPanel {

    public Preferences(final IDE ide) {
        WebLabel text = new WebLabel("Click here to change.");
        text.setCursor(new Cursor(Cursor.HAND_CURSOR));
        text.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFontChooser chooser = new JFontChooser();

                if(chooser.showDialog(Preferences.this) == WebOptionPane.OK_OPTION) {
                    Font font = chooser.getSelectedFont();
                    ide.setIDEFont(font);
                }
            }
        });

        addLabeledPanel("Text", text);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(320, 140));
    }

    private void addLabeledPanel(String str, JComponent comp) {
        WebPanel labeledPanel = new WebPanel();
        labeledPanel.add(new JLabel(str));
        labeledPanel.add(Box.createHorizontalStrut(130));
        labeledPanel.add(comp);
        add(labeledPanel);
    }

}
