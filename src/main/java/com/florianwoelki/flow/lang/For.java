package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 15.11.16.
 */
public class For extends Block
{
    private final String lower, upper;

    public For(Block superBlock, String lower, String upper)
    {
        super(superBlock);

        this.lower = lower;
        this.upper = upper;
    }

    @Override
    protected void runAfterParse() throws InvalidCodeException
    {
        double a, b;

        try
        {
            a = Double.valueOf(FlowLang.implode(new String[]{this.lower}, this));
            b = Double.valueOf(FlowLang.implode(new String[]{this.upper}, this));
        }
        catch (Exception e)
        {
            throw new InvalidCodeException("Attempted to use for loop with non-number bounds.");
        }

        double larger = Math.max(a, b), smaller = Math.min(a, b);

        for (double i = smaller; i < larger; i++)
        {
            try
            {
                this.getSuperBlock().getVariable(this.lower).setValue(i);
            }
            catch (Exception ignored)
            {
            }

            this.doBlocks();
        }
    }
}
