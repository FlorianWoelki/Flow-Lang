package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Class;
import com.florianwoelki.flow.lang.Method;
import com.florianwoelki.flow.lang.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class FunctionManager
{
    private final List<Function> functions = new ArrayList<>();

    private final Console console;

    public FunctionManager(Console console)
    {
        this.console = console;

        this.functions.add(new Print());
        this.functions.add(new Declare());
        this.functions.add(new Random());
        this.functions.add(new GetInput());
        this.functions.add(new Set());
    }

    public void parse(Block block, String input) throws InvalidCodeException
    {
        String funct = input.substring(0, input.indexOf("(")).trim();

        String[] args = input.substring(input.indexOf("(") + 1, input.indexOf(")")).replaceAll(" ", "").split(",");

        Variable receiver = null;

        try
        {
            receiver = block.getVariable(input.substring(input.indexOf(")") + 1).trim());
        }
        catch (InvalidCodeException e)
        {
        }

        try
        {
            Method method = ((Class) block.getBlockTree()[0]).getMethod(funct);
            Object retValue = method.invoke(args);
            if (receiver != null)
            {
                if (method.getReturnType() == Variable.VariableType.VOID)
                {
                    throw new InvalidCodeException("Attempted to store result of void method to variable.");
                }

                receiver.getType().validateValue(retValue, block);
                receiver.setValue(retValue);
            }
        }
        catch (InvalidCodeException e)
        {
            Function fun = null;

            for (Function f : this.functions)
            {
                if (f.getName().equals(funct))
                {
                    fun = f;
                }
            }

            if (fun == null)
            {
                throw new InvalidCodeException("Function " + funct + " does not exist.");
            }
            else
            {
                fun.run(this.console, block, args, receiver);
            }
        }
    }
}
