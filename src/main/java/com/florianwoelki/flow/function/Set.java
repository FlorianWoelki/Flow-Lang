package com.florianwoelki.flow.function;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

import java.util.Arrays;

/**
 * Created by Florian Woelki on 11.11.16.
 */
public class Set extends Function {

    public Set() {
        super("set");
    }

    /*
    Usage: set(<value>) <var>
     */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException {
        if(receiver == null) {
            throw new InvalidCodeException("Attempted to set variable but no variable specified.");
        }

        receiver.getType().validateValue(args[0], block);
        receiver.setValue(FlowLang.implode(Arrays.copyOfRange(args, 1, args.length), block));
    }

}
