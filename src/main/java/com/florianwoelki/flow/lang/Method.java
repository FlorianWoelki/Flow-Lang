package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Method extends Block
{
    public Method(Block superBlock)
    {
        super(superBlock);
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException
    {

    }
}
