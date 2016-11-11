package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class While extends ConditionalBlock
{
    public While(Block superBlock, String aVal, String bVal, CompareOperation compareOp)
    {
        super(superBlock, aVal, bVal, compareOp);
    }

    @Override
    public void runAfterParse() throws InvalidCodeException
    {
        if (this.compareOp == CompareOperation.EQUALS)
        {
            int a, b;

            do
            {
                try
                {
                    a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                    b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
                }
                catch (Exception e)
                {
                    throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers");
                }

                this.doBlocks();
            }
            while (a == b);
        }
        else if (this.compareOp == CompareOperation.NOTEEQUALS)
        {
            int a, b;

            do
            {
                try
                {
                    a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                    b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
                }
                catch (Exception e)
                {
                    throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers");
                }

                this.doBlocks();
            }
            while (a != b);
        }
        else if (this.compareOp == CompareOperation.GREATERTHAN)
        {
            int a, b;

            do
            {
                try
                {
                    a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                    b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
                }
                catch (Exception e)
                {
                    throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers.");
                }

                this.doBlocks();
            }
            while (a > b);
        }
        else if (this.compareOp == CompareOperation.LESSTHAN)
        {
            int a, b;

            do
            {
                try
                {
                    a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                    b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
                }
                catch (Exception e)
                {
                    throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers.");
                }

                this.doBlocks();
            }
            while (a < b);
        }
    }
}
