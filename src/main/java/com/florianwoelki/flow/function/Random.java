package com.florianwoelki.flow.function;

import com.florianwoelki.flow.exception.InvalidCodeException;
import com.florianwoelki.flow.gui.Console;
import com.florianwoelki.flow.lang.Block;
import com.florianwoelki.flow.lang.Variable;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class Random extends Function {

    private java.util.Random random;

    public Random() {
        super( "random" );
    }

    /*
    Usage: random([ceiling]) <var>
     */
    @Override
    public void run(Console console, Block block, String[] args, Variable receiver) throws InvalidCodeException {
        if ( random == null ) {
            random = new java.util.Random();
        }

        if ( receiver != null ) {
            if ( receiver.getType() != Variable.VariableType.INTEGER && receiver.getType() != Variable.VariableType.DECIMAL ) {
                throw new InvalidCodeException( "Attempted to assign random number to non-integer." );
            }

            int ceil = -1;

            if ( !args[ 0 ].equals( "" ) ) {
                try {
                    ceil = Integer.parseInt( args[ 0 ] );
                } catch ( Exception e ) {
                    throw new InvalidCodeException( "Invalid ceiling." );
                }

                if ( ceil == -1 ) {
                    receiver.setValue( random.nextInt() );
                } else {
                    receiver.setValue( random.nextInt( ceil ) );
                }
            }
        }
    }

}
