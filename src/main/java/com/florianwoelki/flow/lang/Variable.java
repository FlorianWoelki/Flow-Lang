package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Variable {

    private final VariableType type;
    private final String name;
    private final ArrayList<Object> values;
    private boolean isArray;

    public Variable(VariableType type, String name, boolean isArray, Object... values) {
        this.type = type;
        this.name = name;
        this.isArray = isArray;
        this.values = new ArrayList<>(Arrays.asList(values));

        if(isArray) {
            Object[] splittedArray = this.values.get(0).toString().split(",");

            this.values.addAll(Arrays.asList(splittedArray));
            this.values.remove(0);
        }
    }

    public VariableType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() throws InvalidCodeException {
        if(isArray) {
            throw new InvalidCodeException("Attempted to access value of array.");
        }

        return values.get(0);
    }

    public void setValue(Object value) throws InvalidCodeException {
        if(isArray) {
            throw new InvalidCodeException("Attempted to set value of array.");
        }

        values.set(0, getType().formatValue(value));
    }

    public Object[] getValues() throws InvalidCodeException {
        if(!isArray) {
            throw new InvalidCodeException("Attempted to access values of non-array.");
        }

        return values.toArray();
    }

    public void setValue(Object value, int index) throws InvalidCodeException {
        if(!isArray) {
            throw new InvalidCodeException("Attempted to set value at position of non-array.");
        }

        values.add(index, getType().formatValue(value));
    }

    public boolean isArray() {
        return isArray;
    }

    @Override
    public String toString() {
        return "Variable name=" + getName() + " type=" + getType() + " isArray=" + isArray + " values=" + Arrays.toString(values.toArray());
    }

    public enum VariableType {
        VOID(null), BOOLEAN(Boolean.class), INTEGER(Integer.class), DECIMAL(Double.class), STRING(null);

        private final java.lang.Class<?> clazz;

        VariableType(java.lang.Class<?> clazz) {
            this.clazz = clazz;
        }

        public static VariableType match(String str) throws InvalidCodeException {
            for(VariableType t : values()) {
                if(t.name().toLowerCase().equals(str.toLowerCase())) {
                    return t;
                }
            }

            throw new InvalidCodeException("Variable type " + str + " doesn't exist.");
        }

        public void validateValue(Object value, Block block) throws InvalidCodeException {
            try {
                if(clazz != null) {
                    String sValue = FlowLang.implode(String.valueOf(value), block);
                    clazz.getDeclaredMethod("valueOf", String.class).invoke(null, sValue);
                }
            } catch(Exception e) {
                throw new InvalidCodeException("Invalid value for variable type " + this);
            }
        }

        public Object formatValue(Object value) throws InvalidCodeException {
            try {
                if(clazz != null) {
                    return clazz.getDeclaredMethod("valueOf", String.class).invoke(null, value);
                } else {
                    return null;
                }
            } catch(Exception e) {
                throw new InvalidCodeException("Formatted invalid value " + value + " for variable type " + name().toLowerCase());
            }
        }
    }

}
