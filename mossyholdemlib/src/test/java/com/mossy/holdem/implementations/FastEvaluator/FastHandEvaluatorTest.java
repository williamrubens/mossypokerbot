package com.mossy.holdem.implementations.FastEvaluator;

import com.mossy.holdem.HandType;
import com.mossy.holdem.implementations.*;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
                "7sAcKcQcJcTc", // straight flush
                "4dKhQhJhTh9h", // straigh flush lower
                "KcKsKd3hKh9d", // four of a kind
                "QcQsQdQh8d", // four of a kind
                "KcKsKdQhQdTh", // full house
                "QcQhQdKsKd4d", // full house
                "Ac9c5c8c6c7s5c", // flush
                "Kc9c8c6c5c", // flush
                "3d4h5s6s7c9d", // straight
                "JcQdKh2c3d4h5s6s", // straight
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

    @Test
    public void testEvaluateRandomHand() throws Exception
    {


        HandFactory handFactory = new HandFactory();
        HandScoreFactory scoreFactory = new HandScoreFactory();
        FiveCardHandEvaluator handEvaluator = new FiveCardHandEvaluator(scoreFactory);
        StandardDeckFactory deckFactory = new StandardDeckFactory();
        FastHandEvaluator fastEvaluator = new FastHandEvaluator(scoreFactory, new HandBitsAdaptor(handFactory));

        for(HandType handType : HandType.values())
        {
            int iterations = 0;
            while(iterations++ < 10000)
            {
                IHand randomHand = handFactory.generateRandom(handType, deckFactory);

                if(!compareEvaluators(randomHand, handEvaluator, fastEvaluator))
                {
                    fail(String.format("Fast score not equals hand score"));
                }
            }
        }
    }

    private boolean compareEvaluators(IHand hand, IHandEvaluator evaluator1, IHandEvaluator evaluator2) throws Exception
    {
        int handScore = evaluator1.evaluateHand(hand);
        int fastScore = evaluator2.evaluateHand(hand);

        if(handScore == fastScore)
        {
            return true;
        }
        return false;
    }
}
