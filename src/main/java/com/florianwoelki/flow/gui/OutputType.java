package com.florianwoelki.flow.gui;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * Created by Florian Woelki on 23.07.17.
 */
public enum OutputType {

    OUTPUT(Color.BLACK),

    INFO(Color.GREEN),

    ERROR(Color.RED);

    private SimpleAttributeSet attributes;

    OutputType(Color color) {
        this.attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, color);
    }

    public SimpleAttributeSet getAttributes() {
        return attributes;
    }

}
