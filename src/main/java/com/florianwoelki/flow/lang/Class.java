package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.function.FunctionManager;
import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Class extends Block {

    private final String[] code;
    public FunctionManager functionManager;
    private List<Method> methods;

    public Class(String[] code) {
        super(null);

        this.code = code;
    }

    public void run(Console console) throws InvalidCodeException {
        methods = new ArrayList<>();
        functionManager = new FunctionManager(console);

        Method currentMethod = null;

        for(String line : code) {
            line = trimComments(line);

            if(line.startsWith("method ")) {
                String[] args = line.split(" ");
                String[] mArgs = args[1].split(":");
                String methodName = mArgs[0];

                if(mArgs.length == 1) {
                    throw new InvalidCodeException("Did not specify return type for method " + methodName + ".");
                }

                Variable.VariableType returnType = Variable.VariableType.match(mArgs[1]);

                String[] params = Arrays.copyOfRange(args, 2, args.length);

                currentMethod = new Method(this, methodName, returnType, params);
            } else if(currentMethod != null && line.equals("end " + currentMethod.getName())) {
                methods.add(currentMethod);

                currentMethod = null;
            } else if(line.startsWith("declare")) {
                functionManager.parse(this, line);
            } else {
                if(currentMethod != null && !line.equals("") && !line.equals(" ")) {
                    currentMethod.addLine(line);
                }
            }
        }

        console.clear();

        Method main = getMethod("main");
        main.run();
        main.invoke(new String[0]);

        console.write("--Terminated.");
    }

    private String trimComments(String str) {
        StringBuilder fin = new StringBuilder();

        for(String word : str.split(" ")) {
            if(word.startsWith("//")) {
                return fin.toString().trim();
            } else {
                fin.append(word).append(" ");
            }
        }

        return fin.toString().trim();
    }

    public Method getMethod(String name) throws InvalidCodeException {
        for(Method m : methods) {
            if(m.getName().equals(name)) {
                return m;
            }
        }

        throw new InvalidCodeException("Method " + name + " does not exist.");
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException {
    }

    @Override
    public String toString() {
        return "Class methods=" + Arrays.toString(methods.toArray()) + " code=" + Arrays.toString(code);
    }

}
