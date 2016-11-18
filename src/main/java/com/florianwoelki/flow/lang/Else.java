package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 11.11.16.
 */
public class Else extends Block {

    public Else(Block superBlock) {
        super( superBlock );
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException {
    }

    @Override
    public String toString() {
        return "Else";
    }

}
