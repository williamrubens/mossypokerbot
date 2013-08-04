package com.mossy.holdem.implementations.FastEvaluator;

import com.mossy.holdem.implementations.FiveCardHandEvaluator;
import com.mossy.holdem.implementations.HandEvaluator;
import com.mossy.holdem.implementations.HandFactory;
import com.mossy.holdem.implementations.HandScoreFactory;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 16/06/2013
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class FastHandEvaluatorTest {

    @Test
    public void testEvaluateHand() throws Exception
    {

        String []  hands = new String [] {
                //"7sAcKcQcJcTc", // straight flush
                //"4dKhQhJhTh9h", // straigh flush lower
                //"KcKsKd3hKh9d", // four of a kind
                //"QcQsQdQh8d", // four of a kind
                //"KcKsKdQhQdTh", // full house
                //"QcQhQdKsKd4d", // full house
                //"Ac9c5c8c6c7s5c", // flush
                //"Kc9c8c6c5c", // flush
                //"3d4h5s6s7c9d", // straight
                //"JcQdKh2c3d4h5s6s", // straight
                "2c2d2h5h3h", // trips
                "2c2h2d4h3h5c", // trips
                "AcAhKcKh8d", // two pair
                "KcKhQcThJs", // two pair
                "8c6d5h4s3s", // high card
                "8c6d5h4s2s"}; // high card

        int lastScore = Integer.MAX_VALUE;

        HandFactory handFactory = new HandFactory();
        HandScoreFactory scoreFactory = new HandScoreFactory();
        HandBitsAdaptor handBitsAdaptor = new HandBitsAdaptor(handFactory);
        IHandEvaluator handEvaluator = new FastHandEvaluator(scoreFactory, handBitsAdaptor);

        for(String handString : hands)
        {
            IHand hand = handFactory.build(handString);
            int handScore = handEvaluator.evaluateHand(hand);
            boolean isSmaller = handScore < lastScore;
            assertEquals(isSmaller, true);
        }

    }
}
