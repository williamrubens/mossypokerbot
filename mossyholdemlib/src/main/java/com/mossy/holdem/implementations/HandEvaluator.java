/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.google.inject.Inject;
import com.mossy.holdem.Card;
import com.mossy.holdem.annotations.Annotations.FiveCardEvaluator;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandEvaluator;

/**
 *
 * @author d80050
 */
public class HandEvaluator implements IHandEvaluator
{
    IHandEvaluator fiveCardEvaluator;

    @Inject
    HandEvaluator(@FiveCardEvaluator IHandEvaluator fce)
    {
        fiveCardEvaluator = fce;
    }
            

    @Override
    public int evaluateHand(IHand hand)
    {       
        
        if(hand.cardCount() == 5)
        {
            return fiveCardEvaluator.evaluateHand(hand);
        }
                
        int highestScore = -1;
        
        for(Card cardToRemove : hand.cardsSorted())
        {
            int currentScore = evaluateHand(hand.removeCard(cardToRemove));
            highestScore = currentScore  > highestScore ? currentScore : highestScore;
        }
        
        return highestScore;
    }
    
}
