package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 11.11.16.
 */
public class ElseIf extends ConditionalBlock
{
    public ElseIf(Block superBlock, String aVal, String bVal, CompareOperation compareOp)
    {
        super(superBlock, aVal, bVal, compareOp);
    }

    public boolean runElseIf() throws InvalidCodeException
    {
        boolean opSuccess = false;

        if (this.compareOp == CompareOperation.EQUALS)
        {
            if (FlowLang.implode(new String[]{this.aVal}, this).equals(FlowLang.implode(new String[]{this.bVal}, this)))
            {
                this.doBlocks();
                opSuccess = true;
            }
        }
        else if (this.compareOp == CompareOperation.NOTEQUALS)
        {
            if (!FlowLang.implode(new String[]{this.aVal}, this).equals(FlowLang.implode(new String[]{this.bVal}, this)))
            {
                this.doBlocks();
                opSuccess = true;
            }
        }
        else if (this.compareOp == CompareOperation.GREATERTHAN)
        {
            int a, b;

            try
            {
                a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
            }
            catch (Exception e)
            {
                throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers");
            }

            if (a > b)
            {
                this.doBlocks();
                opSuccess = true;
            }
        }
        else if (this.compareOp == CompareOperation.LESSTHAN)
        {
            int a, b;

            try
            {
                a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
            }
            catch (Exception e)
            {
                throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers");
            }

            if (a < b)
            {
                this.doBlocks();
                opSuccess = true;
            }
        }

        return opSuccess;
    }

    @Override
    public void runAfterParse() throws InvalidCodeException
    {
    }

    @Override
    public String toString()
    {
        return "ElseIf aVal=" + this.aVal + " bVal=" + this.bVal + " compareOp=" + this.compareOp.name();
    }
}
