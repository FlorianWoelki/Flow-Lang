package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public abstract class ConditionalBlock extends Block
{
    enum ConditionalBlockType
    {
        IF,
        ELSE,
        ELSEIF,
        WHILE;
    }

    enum CompareOperation
    {
        EQUALS,
        NOTEQUALS,
        GREATERTHAN,
        LESSTHAN;

        public static CompareOperation match(String str) throws InvalidCodeException
        {
            for (CompareOperation op : values())
            {
                if (op.name().toLowerCase().equals(str)) return op;
            }

            throw new InvalidCodeException("Comparison operation " + str + " doesn't exist.");
        }
    }

    final String aVal;
    final String bVal;
    final CompareOperation compareOp;

    ConditionalBlock(Block superBlock, String aVal, String bVal, CompareOperation compareOp)
    {
        super(superBlock);

        this.aVal = aVal;
        this.bVal = bVal;
        this.compareOp = compareOp;
    }

    public abstract void runAfterParse() throws InvalidCodeException;
}
