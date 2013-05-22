/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.mossy.holdem.Rank;
import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandScoreFactory;
import java.util.Iterator;

/**
 *
 * @author d80050
 */
public class HandScoreFactory implements IHandScoreFactory {
    /*
    static final int CARD_SCORE_DELTA  = 1 << 4; //  = 16 needs to be higher than .rank().number()
    static final int HAND_SCORE_DELTA  = CARD_SCORE_DELTA  << 20; // == CARD_SCORE_DELTA ^ 6
    
    static final int STRAIGHT_FLUSH    = HAND_SCORE_DELTA * 9;
    static final int FOUR_OF_A_KIND    = HAND_SCORE_DELTA * 8;
    static final int FULL_HOUSE        = HAND_SCORE_DELTA * 7; 
    static final int FLUSH             = HAND_SCORE_DELTA * 6;
    static final int STRAIGHT          = HAND_SCORE_DELTA * 5;
    static final int THREE_OF_A_KIND   = HAND_SCORE_DELTA * 4;
    static final int TWO_PAIR          = HAND_SCORE_DELTA * 3;
    static final int PAIR              = HAND_SCORE_DELTA * 2;
    static final int HIGH_CARD         = HAND_SCORE_DELTA * 1;
    */
    
    static final int HAND_TYPE_SHIFT    = 24;
    static final int FIRST_CARD_SHIFT   = 16;
    static final int SECOND_CARD_SHIFT  = 12;
    static final int THIRD_CARD_SHIFT   = 8;
    static final int FOURTH_CARD_SHIFT  = 4;
    static final int FIFTH_CARD_SHIFT   = 0;
    
    
    
    
    static final int STRAIGHT_FLUSH    = (9 << HAND_TYPE_SHIFT);
    static final int FOUR_OF_A_KIND    = (8 << HAND_TYPE_SHIFT);
    static final int FULL_HOUSE        = (7 << HAND_TYPE_SHIFT);
    static final int FLUSH             = (6 << HAND_TYPE_SHIFT);
    static final int STRAIGHT          = (5 << HAND_TYPE_SHIFT);
    static final int THREE_OF_A_KIND   = (4 << HAND_TYPE_SHIFT);
    static final int TWO_PAIR          = (3 << HAND_TYPE_SHIFT);
    static final int PAIR              = (2 << HAND_TYPE_SHIFT);
    static final int HIGH_CARD         = (1 << HAND_TYPE_SHIFT);
    
    static final int HAND_TYPE_MASK = 0xFF000000;
    
    
    @Override
    public int buildStraightFlushScore(Rank highestRank)
    {
        return STRAIGHT_FLUSH + (highestRank.number() << FIRST_CARD_SHIFT);
    }
    @Override
    public int buildFourOfAKindScore(Rank fourOfAKindRank)
    {
        return FOUR_OF_A_KIND + (fourOfAKindRank.number() << FIRST_CARD_SHIFT);
    }
    @Override
    public int buildFullHouseScore(Rank trips, Rank pairs)
    {
        return FULL_HOUSE + (trips.number()  << FIRST_CARD_SHIFT) + (pairs.number() << SECOND_CARD_SHIFT);
    }
    @Override
    public int buildFlushScore(IHand allCards)
    {
        return FLUSH + buildKickerScore(allCards);
    }
    @Override
    public int buildStraightScore(Rank highestRank)
    {
         return STRAIGHT + (highestRank.number() << FIRST_CARD_SHIFT);
    }
    @Override
    public int buildThreeOfAKindScore(Rank trips, Rank highKicker, Rank lowKicker)
    {
        return THREE_OF_A_KIND + (trips.number() << FIRST_CARD_SHIFT) + (highKicker.number() << SECOND_CARD_SHIFT) +
                        (lowKicker.number() << THIRD_CARD_SHIFT) ;
    }
    @Override
    public int buildTwoPairScore(Rank highPair, Rank lowPair, Rank kicker)
    {
        return TWO_PAIR + (highPair.number() << FIRST_CARD_SHIFT) + (lowPair.number() << SECOND_CARD_SHIFT) 
                        + (kicker.number() << THIRD_CARD_SHIFT) ;
    }
    @Override
    public int buildPairScore(Rank pair, Rank highKicker, Rank medKicker, Rank lowKicker)
    {
         return PAIR + (pair.number() << FIRST_CARD_SHIFT) + (highKicker.number() << SECOND_CARD_SHIFT) 
                        + (medKicker.number() << THIRD_CARD_SHIFT)+ (lowKicker.number() << FOURTH_CARD_SHIFT) ;
    }
    @Override
    public int buildHighCardScore(IHand kickers)
    {
        return HIGH_CARD + buildKickerScore(kickers);
    }
    
    
    private int buildKickerScore(IHand kickers)
    {
        
        if(kickers.cardCount() != 5)
        {
            throw new IllegalArgumentException("Must have 5 card hand.");
        }
        
        int kickerAdjustment = 0;
        
        Iterator<Card> iKicker = kickers.cards().iterator();
        
        kickerAdjustment += iKicker.next().rank().number() << FIFTH_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << FOURTH_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << THIRD_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << SECOND_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << FIRST_CARD_SHIFT;
      
        return kickerAdjustment;
    }
    
}
