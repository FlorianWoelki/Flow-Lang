package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 11.11.16.
 */
public class ElseIf extends ConditionalBlock {

    public ElseIf(Block superBlock, String aVal, String bVal, CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
    }

    public boolean runElseIf() throws InvalidCodeException {
        boolean opSuccess = false;

        if(compareOp == CompareOperation.EQUALS) {
            if(FlowLang.implode(new String[]{aVal}, this).equals(FlowLang.implode(new String[]{bVal}, this))) {
                doBlocks();
                opSuccess = true;
            }
        } else if(compareOp == CompareOperation.NOTEQUALS) {
            if(!FlowLang.implode(new String[]{aVal}, this).equals(FlowLang.implode(new String[]{bVal}, this))) {
                doBlocks();
                opSuccess = true;
            }
        } else if(compareOp == CompareOperation.GREATERTHAN) {
            int a, b;

            try {
                a = Integer.parseInt(FlowLang.implode(new String[]{aVal}, this));
                b = Integer.parseInt(FlowLang.implode(new String[]{bVal}, this));
            } catch(Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
            }

            if(a > b) {
                doBlocks();
                opSuccess = true;
            }
        } else if(compareOp == CompareOperation.LESSTHAN) {
            int a, b;

            try {
                a = Integer.parseInt(FlowLang.implode(new String[]{aVal}, this));
                b = Integer.parseInt(FlowLang.implode(new String[]{bVal}, this));
            } catch(Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
            }

            if(a < b) {
                doBlocks();
                opSuccess = true;
            }
        }

        return opSuccess;
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
    }

    @Override
    public String toString() {
        return "ElseIf aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }

}
