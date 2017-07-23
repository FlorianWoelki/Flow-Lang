package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class While extends ConditionalBlock {

    public While(Block superBlock, String aVal, String bVal, CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        if(compareOp == CompareOperation.EQUALS) {
            int a, b;

            try {
                a = Integer.parseInt(FlowLang.implode(aVal, this));
                b = Integer.parseInt(FlowLang.implode(bVal, this));
            } catch(Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
            }

            while(a == b) {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
                }

                doBlocks();
            }
        } else if(compareOp == CompareOperation.NOTEQUALS) {
            int a, b;

            try {
                a = Integer.parseInt(FlowLang.implode(aVal, this));
                b = Integer.parseInt(FlowLang.implode(bVal, this));
            } catch(Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
            }

            while(a != b) {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
                }

                doBlocks();
            }
        } else if(compareOp == CompareOperation.GREATERTHAN) {
            int a, b;

            try {
                a = Integer.parseInt(FlowLang.implode(aVal, this));
                b = Integer.parseInt(FlowLang.implode(bVal, this));
            } catch(Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
            }

            while(a > b) {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
                }

                doBlocks();
            }
        } else if(compareOp == CompareOperation.LESSTHAN) {
            int a, b;

            try {
                a = Integer.parseInt(FlowLang.implode(aVal, this));
                b = Integer.parseInt(FlowLang.implode(bVal, this));
            } catch(Exception e) {
                throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
            }

            while(a < b) {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
                }

                doBlocks();
            }
        }
    }

    @Override
    public String toString() {
        return "While aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }

}
