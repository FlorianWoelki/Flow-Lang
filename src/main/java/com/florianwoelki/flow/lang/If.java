package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class If extends ConditionalBlock {

    private final List<ElseIf> elseIfs;
    private Else elze;

    public If(Block superBlock, String aVal, String bVal, CompareOperation compareOp) {
        super( superBlock, aVal, bVal, compareOp );

        this.elseIfs = new ArrayList<>();
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        boolean opSuccess = false;

        if ( compareOp == CompareOperation.EQUALS ) {
            if ( FlowLang.implode( new String[] { aVal }, this ).equals( FlowLang.implode( new String[] { bVal }, this ) ) ) {
                doBlocks();
                opSuccess = true;
            }
        } else if ( compareOp == CompareOperation.NOTEQUALS ) {
            if ( !FlowLang.implode( new String[] { aVal }, this ).equals( FlowLang.implode( new String[] { bVal }, this ) ) ) {
                doBlocks();
                opSuccess = true;
            }
        } else if ( compareOp == CompareOperation.GREATERTHAN ) {
            int a, b;

            try {
                a = Integer.parseInt( FlowLang.implode( new String[] { aVal }, this ) );
                b = Integer.parseInt( FlowLang.implode( new String[] { bVal }, this ) );
            } catch ( Exception e ) {
                throw new InvalidCodeException( "Attempted to use " + compareOp.name().toLowerCase() + " on non-integers" );
            }

            if ( a > b ) {
                doBlocks();
                opSuccess = true;
            }
        } else if ( compareOp == CompareOperation.LESSTHAN ) {
            int a, b;

            try {
                a = Integer.parseInt( FlowLang.implode( new String[] { aVal }, this ) );
                b = Integer.parseInt( FlowLang.implode( new String[] { bVal }, this ) );
            } catch ( Exception e ) {
                throw new InvalidCodeException( "Attempted to use " + compareOp.name().toLowerCase() + " on non-integers" );
            }

            if ( a < b ) {
                doBlocks();
                opSuccess = true;
            }
        }

        if ( !opSuccess ) {
            boolean elseIfRan = false;

            for ( ElseIf elseIf : elseIfs ) {
                elseIf.run();
                if ( elseIf.runElseIf() ) {
                    elseIfRan = true;
                    break;
                }
            }

            if ( !elseIfRan && elze != null ) {
                elze.run();
                elze.doBlocks();
            }
        }
    }

    public void addElseIf(ElseIf elseIf) {
        elseIfs.add( elseIf );
    }

    public void setElse(Else elze) {
        elze = elze;
    }

    @Override
    public String toString() {
        return "If aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }

}
