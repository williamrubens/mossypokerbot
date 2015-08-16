/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.implementations;

import com.google.inject.Inject;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.annotations.Annotations.FiveCardEvaluator;
import com.mossy.pokerbot.interfaces.IHand;
import com.mossy.pokerbot.interfaces.IHandEvaluator;

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
