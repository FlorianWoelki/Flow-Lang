package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Variable {

    public enum VariableType {
        VOID( null ),
        BOOLEAN( Boolean.class ),
        INTEGER( Integer.class ),
        DECIMAL( Double.class ),
        STRING( null );

        private final java.lang.Class<?> clazz;

        VariableType(java.lang.Class<?> clazz) {
            this.clazz = clazz;
        }

        public void validateValue(Object value, Block block) throws InvalidCodeException {
            try {
                if ( clazz != null ) {
                    String sValue = FlowLang.implode( new String[] { String.valueOf( value ) }, block );
                    clazz.getDeclaredMethod( "valueOf", String.class ).invoke( null, sValue );
                }
            } catch ( Exception e ) {
                throw new InvalidCodeException( "Invalid value for variable type " + this );
            }
        }

        public static VariableType match(String str) throws InvalidCodeException {
            for ( VariableType t : values() ) {
                if ( t.name().toLowerCase().equals( str.toLowerCase() ) ) {
                    return t;
                }
            }

            throw new InvalidCodeException( "Variable type " + str + " doesn't exist." );
        }
    }

    private final VariableType type;
    private final String name;
    private String value;

    public Variable(VariableType type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = String.valueOf( value );
    }

    public VariableType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = String.valueOf( value );
    }

    @Override
    public String toString() {
        return "Variable name=" + getName() + " type=" + getType();
    }

}
