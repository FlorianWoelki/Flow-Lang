package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Method extends Block
{
    private final String name;

    public Method(Block superBlock, String name)
    {
        super(superBlock);

        this.name = name;
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException
    {
        this.doBlocks();
    }

    public String getName()
    {
        return name;
    }
}
