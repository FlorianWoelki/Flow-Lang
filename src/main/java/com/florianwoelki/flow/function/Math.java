package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by Florian Woelki on 22.07.17.
 */
public class Math extends Function {

    private ScriptEngine scriptEngine;

    public Math() {
        super("math");
    }

    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException {
        if(scriptEngine == null) {
            scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
        }

        Variable variable = block.getVariable(args[0]);

        if(variable.getType() != Variable.VariableType.INTEGER) {
            throw new InvalidCodeException("Attempted to assign math output to non-integer.");
        }

        try {
            variable.setValue(new Double(Double.parseDouble(scriptEngine.eval(args[1]).toString())).intValue());
        } catch(Exception e) {
            throw new InvalidCodeException("Invalid math expression!");
        }
    }

}
