package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class If extends ConditionalBlock
{
    public If(Block superBlock, String aVal, String bVal, CompareOperation compareOp)
    {
        super(superBlock, aVal, bVal, compareOp);
    }

    @Override
    public void runAfterParse() throws InvalidCodeException
    {
        if (this.compareOp == CompareOperation.EQUALS)
        {

        }
        else if (this.compareOp == CompareOperation.NOTEEQUALS)
        {

        }
        else if (this.compareOp == CompareOperation.GREATERTHAN)
        {
            int a, b;
        }
        else if (this.compareOp == CompareOperation.LESSTHAN)
        {
            int a, b;
        }
    }
}
