package com.mossy.holdem;

import java.math.BigDecimal;

/**
 * Created by williamrubens on 10/08/2014.
 */
public class ChipStack implements Comparable<ChipStack>
{
    private BigDecimal amount;

    public ChipStack(BigDecimal amount)
    {
        this.amount = amount;
    }
    public ChipStack(int amount)
    {
        this.amount = new BigDecimal(amount);
    }

    public ChipStack()
    {
        this(BigDecimal.ZERO);
    }

    public ChipStack add(ChipStack rhs)
    {
        return new ChipStack(amount.add(rhs.amount));
    }

    public ChipStack subtract(ChipStack rhs)
    {
        return new ChipStack(amount.subtract(rhs.amount));
    }

    public final static ChipStack NO_CHIPS = new ChipStack();
    public final static ChipStack ONE_CHIP = new ChipStack(1);
    public final static ChipStack TWO_CHIPS = new ChipStack(2);

    @Override
    public int compareTo(ChipStack rhs)
    {
        return amount.compareTo(rhs.amount);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        return equals((ChipStack)o);

    }

    public boolean equals(ChipStack rhs)
    {
        ChipStack chipStack = rhs;

        if (!amount.equals(rhs.amount))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return amount.hashCode();
    }

    @Override
    public String toString()
    {
        return "ChipStack{" + amount.toString() +
                '}';
    }
}
