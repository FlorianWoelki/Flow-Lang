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
public class Console extends JFrame
{
    private final JTextPane text;

    private String lastInput;

    private boolean waiting = false;
    private String result = null;

    public Console(final com.florianwoelki.flow.lang.Class clazz) // TODO: Add Class.
    {
        super("Flow - Console");

        this.text = new JTextPane();
        Filter filter = new Filter();
        ((AbstractDocument) text.getDocument()).setDocumentFilter(filter);
        this.text.setEditable(false);
        this.text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        this.text.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent event)
            {
                if (event.getKeyCode() == KeyEvent.VK_UP)
                {
                    event.consume();
                }
                else if (event.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    lastInput = text.getText().split("\n")[text.getText().split("\n").length - 1];

                    if (waiting) result = lastInput;
                }
            }
        });

        JScrollPane scroll = new JScrollPane(this.text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        new SmartScroller(scroll);

        this.add(scroll);

        this.setSize(640, 480);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        new Thread(() ->
        {
            try
            {
                clazz.run(Console.this);
            }
            catch (InvalidCodeException e)
            {
                this.dispose();

                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }).start();
    }

    public String prompt()
    {
        this.waiting = true;
        this.text.setEditable(true);

        while (this.result == null)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        this.waiting = false;
        this.text.setEditable(false);

        String localResult = this.result;
        this.result = null;
        return localResult;
    }

    public void write(String text)
    {
        SwingUtilities.invokeLater(() ->
        {
            try
            {
                this.text.getDocument().insertString(this.text.getDocument().getLength(), text + "\n", null);
            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
            }

            this.setCaret();
        });
    }

    private class Filter extends DocumentFilter
    {
        public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr)
        {
            try
            {
                if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(text.getDocument().getLength())))
                {
                    super.insertString(fb, text.getDocument().getLength(), string, null);
                }
            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
            }
            setCaret();
        }
    }

    public void remove(final DocumentFilter.FilterBypass fb, final int offset, final int length) throws BadLocationException
    {
        if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(this.text.getDocument().getLength())))
        {
            remove(fb, offset, length);
        }
        this.setCaret();
    }

    public void replace(final DocumentFilter.FilterBypass fb, final int offset, final int length, final String string, final AttributeSet attrs) throws BadLocationException
    {
        if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(this.text.getDocument().getLength())))
        {
            replace(fb, offset, length, string, null);
        }
        this.setCaret();
    }

    private int getLineOfOffset(int offset) throws BadLocationException
    {
        Document doc = this.text.getDocument();
        if (offset < 0)
        {
            throw new BadLocationException("Can't translate offset to line", -1);
        }
        else if (offset > doc.getLength())
        {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        }
        else
        {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    private int getLineStartOffset(int line) throws BadLocationException
    {
        Element map = this.text.getDocument().getDefaultRootElement();
        if (line < 0)
        {
            throw new BadLocationException("Negative line", -1);
        }
        else if (line > map.getElementCount())
        {
            throw new BadLocationException("Given line too big", this.text.getDocument().getLength() + 1);
        }
        else
        {
            Element lineElement = map.getElement(line);
            return lineElement.getStartOffset();
        }
    }

    private void setCaret()
    {
        this.text.setCaretPosition(this.text.getDocument().getLength());
    }
}
