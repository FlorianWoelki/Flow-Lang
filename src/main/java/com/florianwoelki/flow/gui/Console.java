package com.florianwoelki.flow.gui;

import com.florianwoelki.flow.exception.InvalidCodeException;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Console extends JTextPane {

    private String lastInput;

    private boolean waiting;
    private String result;

    public Console() {
        Filter filter = new Filter();
        ((AbstractDocument) getDocument()).setDocumentFilter(filter);
        setEditable(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if(event.getKeyCode() == KeyEvent.VK_UP) {
                    event.consume();
                } else if(event.getKeyCode() == KeyEvent.VK_ENTER) {
                    lastInput = getText().split("\n")[getText().split("\n").length - 1];

                    if(waiting) result = lastInput;
                }
            }
        });
    }

    public void run(final com.florianwoelki.flow.lang.Class clazz) {
        new Thread(() ->
        {
            try {
                clazz.run(Console.this);
            } catch(InvalidCodeException e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }).start();
    }

    public String prompt() {
        waiting = true;
        setEditable(true);

        while(result == null) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        waiting = false;
        setEditable(false);

        String localResult = result;
        result = null;
        return localResult;
    }

    public void clear() {
        SwingUtilities.invokeLater(() ->
        {
            setText("");
            setCaret();
        });
    }

    public void write(String text) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                getDocument().insertString(getDocument().getLength(), text + "\n", null);
            } catch(BadLocationException e) {
                e.printStackTrace();
            }

            setCaret();
        });
    }

    public void remove(final DocumentFilter.FilterBypass fb, final int offset, final int length) throws BadLocationException {
        if(getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(getDocument().getLength()))) {
            remove(fb, offset, length);
        }
        setCaret();
    }

    public void replace(final DocumentFilter.FilterBypass fb, final int offset, final int length, final String string, final AttributeSet attrs) throws BadLocationException {
        if(getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(getDocument().getLength()))) {
            replace(fb, offset, length, string, null);
        }
        setCaret();
    }

    private int getLineOfOffset(int offset) throws BadLocationException {
        Document doc = getDocument();
        if(offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if(offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    private int getLineStartOffset(int line) throws BadLocationException {
        Element map = getDocument().getDefaultRootElement();
        if(line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if(line > map.getElementCount()) {
            throw new BadLocationException("Given line too big", getDocument().getLength() + 1);
        } else {
            Element lineElement = map.getElement(line);
            return lineElement.getStartOffset();
        }
    }

    private void setCaret() {
        setCaretPosition(getDocument().getLength());
    }

    private class Filter extends DocumentFilter {
        public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) {
            try {
                if(getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(getDocument().getLength()))) {
                    super.insertString(fb, getDocument().getLength(), string, null);
                }
            } catch(BadLocationException e) {
                e.printStackTrace();
            }
            setCaret();
        }
    }

}
