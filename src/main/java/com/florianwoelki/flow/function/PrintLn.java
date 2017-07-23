package com.florianwoelki.flow.function;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 21.07.17.
 */
public class PrintLn extends Function {

    public PrintLn() {
        super("println");
    }

    /*
        Usage: println("<message>", <variable>, "<message>")
         */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException {
        console.writeLine(FlowLang.implode(args[0], block));
    }

}
