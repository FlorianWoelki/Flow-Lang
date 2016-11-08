package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Variable
{
    public enum VariableType
    {
        BOOLEAN(Boolean.class),
        INTEGER(Integer.class),
        STRING(String.class);

        private final java.lang.Class<?> clazz;

        VariableType(java.lang.Class<?> clazz)
        {
            this.clazz = clazz;
        }

        public void validateValue(Object value)
        {
            try
            {
                this.clazz.getDeclaredMethod("valueOf", String.class).invoke(null, value);
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                e.printStackTrace();
            }
        }

        public static VariableType match(String str) throws InvalidCodeException
        {
            for (VariableType t : values())
            {
                if (t.name().toLowerCase().equals(str))
                {
                    return t;
                }
            }

            throw new InvalidCodeException("Variable type " + str + " doesn't exist.");
        }
    }

    private final VariableType type;
    private final String name;
    private String value;

    public Variable(VariableType type, String name, Object value)
    {
        this.type = type;
        this.name = name;
        this.value = String.valueOf(value);
    }

    public VariableType getType()
    {
        return this.type;
    }

    public String getName()
    {
        return this.name;
    }

    public String getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
