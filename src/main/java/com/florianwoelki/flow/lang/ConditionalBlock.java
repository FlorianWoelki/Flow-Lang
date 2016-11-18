package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public abstract class ConditionalBlock extends Block {

    enum ConditionalBlockType {
        IF,
        ELSE,
        ELSEIF,
        WHILE,
        FOR;
    }

    enum CompareOperation {
        EQUALS( "==" ),
        NOTEQUALS( "!=" ),
        GREATERTHAN( ">" ),
        LESSTHAN( "<" );

        private final String op;

        CompareOperation(String op) {
            this.op = op;
        }

        public String getOp() {
            return op;
        }

        public static CompareOperation match(String str) throws InvalidCodeException {
            for ( CompareOperation op : values() ) {
                if ( op.getOp().equals( str ) ) {
                    return op;
                }
            }

            throw new InvalidCodeException( "Comparison operation " + str + " doesn't exist." );
        }
    }

    final String aVal;
    final String bVal;
    final CompareOperation compareOp;

    ConditionalBlock(Block superBlock, String aVal, String bVal, CompareOperation compareOp) {
        super( superBlock );

        this.aVal = aVal;
        this.bVal = bVal;
        this.compareOp = compareOp;
    }

    public abstract void runAfterParse() throws InvalidCodeException;

    @Override
    public String toString() {
        return "ConditionalBlock type=" + getClass().getSimpleName();
    }

}
