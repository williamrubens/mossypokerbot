package com.mossy.pokerbot;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

/**
 * Created by williamrubens on 10/08/2014.
 */
public class ChipStack implements Comparable<ChipStack> {
    private BigDecimal amount;

    public ChipStack(BigDecimal amount) {
        this.amount = amount;
    }

    public ChipStack(int amount) {
        this.amount = new BigDecimal(amount);
    }

    public ChipStack() {
        this(BigDecimal.ZERO);
    }

    public ChipStack add(ChipStack rhs) {
        return new ChipStack(amount.add(rhs.amount));
    }

    public ChipStack subtract(ChipStack rhs) {
        return new ChipStack(amount.subtract(rhs.amount));
    }

    public ChipStack divide(int divisor) {
        return new ChipStack(amount.divide(new BigDecimal(divisor)));
    }
    public ChipStack multiply(double scalar) {
        return new ChipStack(amount.multiply(new BigDecimal(scalar)));
    }

    public final static ChipStack NO_CHIPS = new ChipStack();
    public final static ChipStack ONE_CHIP = new ChipStack(1);
    public final static ChipStack TWO_CHIPS = new ChipStack(2);

    public final static ChipStack of(int i) {
        return new ChipStack(i);
    }

    public final static ChipStack of(double d) {
        return new ChipStack(BigDecimal.valueOf(d));
    }

    public double toDouble() {
        return amount.doubleValue();
    }

    @Override
    public int compareTo(ChipStack rhs) {
        return amount.compareTo(rhs.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return equals((ChipStack) o);

    }

    public boolean equals(ChipStack rhs) {
        ChipStack chipStack = rhs;

        if (!amount.equals(rhs.amount)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public String toString() {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " chips ";
    }

    public static final BinaryOperator<ChipStack> adder = new BinaryOperator<ChipStack>() {
        @Override
        public ChipStack apply(ChipStack lhs, ChipStack rhs) {
            return lhs.add(rhs);
        }
    };

}
