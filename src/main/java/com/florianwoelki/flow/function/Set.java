package com.florianwoelki.flow.function;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 11.11.16.
 */
public class Set extends Function {

    public Set() {
        super("set");
    }

    /*
    Usage: set(<value>, [index]) <var>
     */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException {
        if(receiver == null) {
            throw new InvalidCodeException("Attempted to set variable but no variable specified.");
        }

        if(receiver.isArray()) {
            receiver.setValue(FlowLang.implode(args[0], block), Integer.parseInt(args[1]));
        } else {
            receiver.setValue(FlowLang.implode(args[0], block));
        }
    }

}
