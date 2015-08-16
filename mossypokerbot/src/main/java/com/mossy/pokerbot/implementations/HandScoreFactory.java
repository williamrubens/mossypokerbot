/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.pokerbot.Rank;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.interfaces.IHand;
import com.mossy.pokerbot.interfaces.IHandScoreFactory;
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

    public static final int HAND_TYPE_SHIFT    = 24;
    public static final int FIRST_CARD_SHIFT   = 16;
    public static final int SECOND_CARD_SHIFT  = 12;
    public static final int THIRD_CARD_SHIFT   = 8;
    public static final int FOURTH_CARD_SHIFT  = 4;
    public static final int FIFTH_CARD_SHIFT   = 0;




    public static final int STRAIGHT_FLUSH    = (9 << HAND_TYPE_SHIFT);
    public static final int FOUR_OF_A_KIND    = (8 << HAND_TYPE_SHIFT);
    public static final int FULL_HOUSE        = (7 << HAND_TYPE_SHIFT);
    public static final int FLUSH             = (6 << HAND_TYPE_SHIFT);
    public static final int STRAIGHT          = (5 << HAND_TYPE_SHIFT);
    public static final int THREE_OF_A_KIND   = (4 << HAND_TYPE_SHIFT);
    public static final int TWO_PAIR          = (3 << HAND_TYPE_SHIFT);
    public static final int PAIR              = (2 << HAND_TYPE_SHIFT);
    public static final int HIGH_CARD         = (1 << HAND_TYPE_SHIFT);

    public static final int HAND_TYPE_MASK = 0xFF000000;
    
    
    @Override
    public int buildStraightFlushScore(Rank highestRank)
    {
        return STRAIGHT_FLUSH + (highestRank.number() << FIRST_CARD_SHIFT);
    }
    @Override
    public int buildFourOfAKindScore(Rank fourOfAKindRank, Rank kicker)
    {
        return FOUR_OF_A_KIND + (fourOfAKindRank.number() << FIRST_CARD_SHIFT) + (kicker.number() << SECOND_CARD_SHIFT);
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
    public int buildFlushScore(ImmutableSortedSet<Rank> allRanks)
    {
        return FLUSH + buildKickerScore(allRanks);
    }
    @Override
    public int buildStraightScore(Rank highestRank)
    {
         return STRAIGHT + (highestRank.number() << FIRST_CARD_SHIFT);
    }
    @Override
    public int buildThreeOfAKindScore(Rank trips, Rank kicker1, Rank kicker2)
    {
        Rank highKicker = kicker1.compareTo(kicker2) > 0  ? kicker1 : kicker2;
        Rank lowKicker = highKicker.equals(kicker1) ? kicker2 : kicker1;

        return THREE_OF_A_KIND + (trips.number() << FIRST_CARD_SHIFT) + (highKicker.number() << SECOND_CARD_SHIFT) +
                        (lowKicker.number() << THIRD_CARD_SHIFT) ;
    }
    @Override
    public int buildTwoPairScore(Rank pair1, Rank pair2, Rank kicker)
    {
        Rank highPair = pair1.compareTo(pair2) > 0  ? pair1 : pair2;
        Rank lowPair = highPair.equals(pair1) ? pair2 : pair1;

        return TWO_PAIR + (highPair.number() << FIRST_CARD_SHIFT) + (lowPair.number() << SECOND_CARD_SHIFT) 
                        + (kicker.number() << THIRD_CARD_SHIFT) ;
    }
    @Override
    public int buildPairScore(Rank pair, Rank kicker1, Rank kicker2, Rank kicker3)
    {
        ImmutableSortedSet<Rank> kickers = ImmutableSortedSet.of(kicker1, kicker2, kicker3);
        return buildPairScore(pair, kickers);
    }

    @Override
    public int buildPairScore(Rank pair, ImmutableSortedSet<Rank> kickers)
    {
        UnmodifiableIterator<Rank> iRank = kickers.iterator();

        Rank lowKicker = iRank.next();
        Rank medKicker = iRank.next();
        Rank highKicker = iRank.next();

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

        //if(kickers.cardCount() != 5)
        //{
        //    throw new IllegalArgumentException("Must have 5 card hand.");
        //}

        int kickerAdjustment = 0;

        Iterator<Card> iKicker = kickers.cardsSorted().asList().reverse().iterator();

        kickerAdjustment += iKicker.next().rank().number() << FIRST_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << SECOND_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << THIRD_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << FOURTH_CARD_SHIFT;
        kickerAdjustment += iKicker.next().rank().number() << FIFTH_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().rank().number() << FIFTH_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().rank().number() << FOURTH_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().rank().number() << THIRD_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().rank().number() << SECOND_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().rank().number() << FIRST_CARD_SHIFT;

        return kickerAdjustment;
    }

    private int buildKickerScore(ImmutableSortedSet<Rank> kickers)
    {

        Iterator<Rank> iKicker = kickers.asList().reverse().iterator();

        int kickerAdjustment = 0;

        kickerAdjustment += iKicker.next().number() << FIRST_CARD_SHIFT;
        kickerAdjustment += iKicker.next().number() << SECOND_CARD_SHIFT;
        kickerAdjustment += iKicker.next().number() << THIRD_CARD_SHIFT;
        kickerAdjustment += iKicker.next().number() << FOURTH_CARD_SHIFT;
        kickerAdjustment += iKicker.next().number() << FIFTH_CARD_SHIFT;

//        if(kickers.size() != 5)
//        {
//            throw new IllegalArgumentException("Must have 5 card hand.");
//        }
//
//        int kickerAdjustment = 0;
//
//        // lowest to highets iterator
//        Iterator<Rank> iKicker = kickers.iterator();
//
//        kickerAdjustment += iKicker.next().number() << FIFTH_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().number() << FOURTH_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().number() << THIRD_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().number() << SECOND_CARD_SHIFT;
//        kickerAdjustment += iKicker.next().number() << FIRST_CARD_SHIFT;

        return kickerAdjustment;
    }
    
}
