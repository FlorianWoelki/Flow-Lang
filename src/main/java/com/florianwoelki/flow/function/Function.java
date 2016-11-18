package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public abstract class Function {

    private final String name;

    public Function(String name) {
        this.name = name;
    }

    public abstract void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException;

    public String getName() {
        return name;
    }

}
