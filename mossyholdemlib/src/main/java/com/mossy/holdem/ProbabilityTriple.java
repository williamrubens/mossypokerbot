package com.mossy.holdem;

/**
 * Created by willrubens on 13/06/15.
 */
public class ProbabilityTriple {

    final float fold;
    final float call;
    final float raise;


    public ProbabilityTriple(float fold, float call, float raise) {
        this.fold = fold;
        this.call = call;
        this.raise = raise;

    }

    public float fold() { return fold; }
    public float call() { return call; }
    public float raise() { return raise; }
}
