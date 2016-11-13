package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class If extends ConditionalBlock
{
    private final List<ElseIf> elseIfs;
    private Else elze;

    public If(Block superBlock, String aVal, String bVal, CompareOperation compareOp)
    {
        super(superBlock, aVal, bVal, compareOp);

        this.elseIfs = new ArrayList<>();
    }

    @Override
    public void runAfterParse() throws InvalidCodeException
    {
        boolean opSuccess = false;

        if (this.compareOp == CompareOperation.EQUALS)
        {
            if (FlowLang.implode(new String[]{this.aVal}, this).equals(FlowLang.implode(new String[]{this.bVal}, this)))
            {
                this.doBlocks();
                opSuccess = true;
            }
        }
        else if (this.compareOp == CompareOperation.NOTEQUALS)
        {
            if (!FlowLang.implode(new String[]{this.aVal}, this).equals(FlowLang.implode(new String[]{this.bVal}, this)))
            {
                this.doBlocks();
                opSuccess = true;
            }
        }
        else if (this.compareOp == CompareOperation.GREATERTHAN)
        {
            int a, b;

            try
            {
                a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
            }
            catch (Exception e)
            {
                throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers");
            }

            if (a > b)
            {
                this.doBlocks();
                opSuccess = true;
            }
        }
        else if (this.compareOp == CompareOperation.LESSTHAN)
        {
            int a, b;

            try
            {
                a = Integer.parseInt(FlowLang.implode(new String[]{this.aVal}, this));
                b = Integer.parseInt(FlowLang.implode(new String[]{this.bVal}, this));
            }
            catch (Exception e)
            {
                throw new InvalidCodeException("Attempted to use " + this.compareOp.name().toLowerCase() + " on non-integers");
            }

            if (a < b)
            {
                this.doBlocks();
                opSuccess = true;
            }
        }

        if (!opSuccess)
        {
            boolean elseIfRan = false;

            for (ElseIf elseIf : this.elseIfs)
            {
                elseIf.run();
                if (elseIf.runElseIf())
                {
                    elseIfRan = true;
                    break;
                }
            }

            if (!elseIfRan && this.elze != null)
            {
                this.elze.run();
                this.elze.doBlocks();
            }
        }
    }

    public void addElseIf(ElseIf elseIf)
    {
        this.elseIfs.add(elseIf);
    }

    public void setElse(Else elze)
    {
        this.elze = elze;
    }
}
