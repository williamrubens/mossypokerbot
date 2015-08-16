package com.mossy.holdem;

/**
 * Created by willrubens on 13/06/15.
 */
public class ProbabilityTriple {

    final double fold;
    final double call;
    final double raise;


    public ProbabilityTriple(double fold, double call, double raise) {
        this.fold = fold;
        this.call = call;
        this.raise = raise;

    }

    public double fold() { return fold; }
    public double call() { return call; }
    public double raise() { return raise; }

    public double get(Action.ActionType type) {
        switch (type) {
            case CHECK: return fold + call;
            case BET: return raise;
            case RAISE_TO: return raise;
            case FOLD: return fold;
            case CALL: return call;
        }
        throw new RuntimeException("Illegal action type for ProbabilityTriple");
    }
}
