package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.implementations.fastevaluator.FastHandEvaluator;
import com.mossy.holdem.implementations.fastevaluator.HandBitsAdaptor;
import com.mossy.holdem.interfaces.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * User: William
 * Date: 15/09/2013
 * Time: 23:39
 */
public class HandStrengthCalculatorTest
{

    @Test
    public void testHandStrengthCalculator () throws Exception
    {
        IHandFactory handFactory = new HandFactory();
        IHandScoreFactory scoreFactory = new HandScoreFactory();
        IHandEvaluator handEvaluator = new FastHandEvaluator(scoreFactory, new HandBitsAdaptor(handFactory));
        IHandStrengthCalculator calculator = new HandStrengthCalculator(handEvaluator, handFactory, new StandardDeckFactory());

        HoleCards myHoleCards = HoleCards.from(Card.ACE_DIAMONDS, Card.QUEEN_CLUBS);
        ImmutableList<Card> boardCards = ImmutableList.of(Card.JACK_HEARTS, Card.FOUR_CLUBS, Card.THREE_HEARTS);

        double handStrength = calculator.calculateHandStrength(myHoleCards, boardCards);

        assertEquals(handStrength, 0.585, 0.001);




    }
}
