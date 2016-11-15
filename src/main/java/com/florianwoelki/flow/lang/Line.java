package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Line extends Block
{
    private final String line;

    public Line(Block superBlock, String line)
    {
        super(superBlock);

        this.line = line;
    }

    public void runAfterParse() throws InvalidCodeException
    {
        ((Class) this.getBlockTree()[0]).functionManager.parse(this.getSuperBlock(), this.line);
    }
}
