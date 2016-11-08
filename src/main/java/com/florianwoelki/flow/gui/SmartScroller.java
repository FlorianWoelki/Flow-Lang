package com.florianwoelki.flow.gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class SmartScroller implements AdjustmentListener
{
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public static final int START = 0;
    public static final int END = 1;

    private int viewportPosition;

    private JScrollBar scrollBar;
    private boolean adjustScrollBar = true;

    private int previousValue = -1;
    private int previousMaximum = -1;

    public SmartScroller(JScrollPane scrollPane)
    {
        this(scrollPane, VERTICAL, END);
    }

    public SmartScroller(JScrollPane scrollPane, int viewportPosition)
    {
        this(scrollPane, VERTICAL, viewportPosition);
    }

    public SmartScroller(JScrollPane scrollPane, int scrollDirection, int viewportPosition)
    {
        if (scrollDirection != HORIZONTAL && scrollDirection != VERTICAL)
        {
            throw new IllegalArgumentException("invalid scroll direction specified");
        }

        if (viewportPosition != START && viewportPosition != END)
        {
            throw new IllegalArgumentException("invalid viewport position specified");
        }

        this.viewportPosition = viewportPosition;

        if (scrollDirection == HORIZONTAL)
        {
            this.scrollBar = scrollPane.getHorizontalScrollBar();
        }
        else
        {
            this.scrollBar = scrollPane.getVerticalScrollBar();
        }

        this.scrollBar.addAdjustmentListener(this);

        Component view = scrollPane.getViewport().getView();

        if (view instanceof JTextComponent)
        {
            JTextComponent textComponent = (JTextComponent) view;
            DefaultCaret caret = (DefaultCaret) textComponent.getCaret();
            caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        SwingUtilities.invokeLater(() -> this.checkScrollBar(e));
    }

    private void checkScrollBar(AdjustmentEvent event)
    {
        JScrollBar scrollBar = (JScrollBar) event.getSource();
        BoundedRangeModel listModel = scrollBar.getModel();
        int value = listModel.getValue();
        int extent = listModel.getExtent();
        int maximum = listModel.getMaximum();

        boolean valueChanged = this.previousValue != value;
        boolean maximumChanged = this.previousMaximum != maximum;

        if (valueChanged && !maximumChanged)
        {
            if (this.viewportPosition == START)
            {
                this.adjustScrollBar = value != 0;
            }
            else
            {
                this.adjustScrollBar = value + extent >= maximum;
            }
        }

        if (this.adjustScrollBar && this.viewportPosition == START)
        {
            scrollBar.removeAdjustmentListener(this);
            value = value + maximum - this.previousMaximum;
            scrollBar.setValue(value);
            scrollBar.addAdjustmentListener(this);
        }

        this.previousValue = value;
        this.previousMaximum = maximum;
    }
}
