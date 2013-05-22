/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.mossy.holdem.interfaces.IHand;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author d80050
 */
public class HandEvaluatorTest {
    
    public HandEvaluatorTest() {
    }

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
            "KcKhQcQhJs", // two pair
            "8c6d5h4s3s", // high card
            "8c6d5h4s2s"}; // high card
        
        int lastScore = Integer.MAX_VALUE;
        
        HandFactory handFactory = new HandFactory();    
        HandScoreFactory scoreFactory = new HandScoreFactory();
        FiveCardHandEvaluator fiveCardhandEvaluator = new FiveCardHandEvaluator(scoreFactory);
        HandEvaluator handEvaluator = new HandEvaluator(fiveCardhandEvaluator);
        
        for(String handString : hands)
        {
            IHand hand = handFactory.build(handString);
            int handScore = handEvaluator.evaluateHand(hand);
            boolean isSmaller = handScore < lastScore;
            assertEquals(isSmaller, true);                    
        }
    }
}