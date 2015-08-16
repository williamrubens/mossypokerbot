/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableSortedSet;

import java.util.Iterator;
import java.util.TreeSet;

import com.google.inject.Inject;
import com.mossy.pokerbot.Rank;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.interfaces.IHand;
import com.mossy.pokerbot.interfaces.IHandEvaluator;
import com.mossy.pokerbot.interfaces.IHandScoreFactory;

/**
 *
 * @author d80050
 */
public class FiveCardHandEvaluator implements IHandEvaluator
{
    final IHandScoreFactory scoreFactory;

    @Inject
    public
    FiveCardHandEvaluator(IHandScoreFactory sf)
    {
        scoreFactory = sf;
    }

    @Override
    public int evaluateHand(IHand hand)
    {
        if(hand.cardCount() != 5)
        {
            throw new RuntimeException ("FiveCardHandEvaluator can only evaluate hands with FIVE cards");
        }
        
        ImmutableSortedSet<Card> cards = hand.cardsSorted();
        
        boolean straight = false;
        boolean flush = false;
        Iterator<Card> iCard = cards.iterator();
        // cards are in increasing order, ie card1 has lowest rank, card5, highest
        Card card1 = iCard.next();
        Card card2 = iCard.next();
        Card card3 = iCard.next();
        Card card4 = iCard.next();
        Card card5 = iCard.next();
        
        int numRanks = 0;
        Rank pair1 = null;
        Rank pair2 = null;
        Rank trips = null;
        TreeSet<Rank> kickers = new TreeSet<Rank>();
        
        if(card1.rank() != card2.rank())
        {
            numRanks++;
            kickers.add(card1.rank());
            kickers.add(card2.rank());
        }
        else
        {
            pair1 = card1.rank();
        }
        
        if(card2.rank() != card3.rank())
        {
            numRanks++;
            kickers.add(card3.rank());
        }
        else
        {
            // in case card2 was added of a kicker
            kickers.remove(card2.rank());
            // check if card3 == card1, because they we have trips
            if(card3.rank() == card1.rank())
            {
                trips = card3.rank();
                pair1 = null;
            }
            else
            {
                pair1 = card2.rank();
            }
        }
        if(card3.rank() != card4.rank())
        {
            numRanks++;
            kickers.add(card4.rank());
        }
        else
        {
            kickers.remove(card3.rank());
            // depending on configurations, could have trips, four of a kind, two pair or pair
            if(trips != null)
            {
                return scoreFactory.buildFourOfAKindScore(card4.rank(), card5.rank());
            }
            else if(pair1 != null)
            {
                if(pair1 == card4.rank())
                {
                    trips = card4.rank();
                    pair1 = null;
                }
                else
                {
                    pair2 = card4.rank();
                }
            }
            else
            {
                pair1 = card4.rank();
            }
        }
        if(card4.rank() != card5.rank())
        {
            numRanks++;
            kickers.add(card5.rank());
        }
        else
        {
            kickers.remove(card4.rank());
             // depending on configurations, could have trips, four of a kind, two pair or pair
            if(trips != null && trips == card5.rank())
            {
                return scoreFactory.buildFourOfAKindScore(card5.rank(), card1.rank());
            }
            else if(pair2 != null)
            {
                if(pair2 == card5.rank())
                {
                    trips = card5.rank();
                    pair2 = null;
                }
                else
                {
                    pair2 = card4.rank();
                }
            }
            else if(pair1 != null)
            {
                if(pair1 == card5.rank())
                {
                    trips = card5.rank();
                    pair1 = null;
                }
                else
                {
                    pair2 = card4.rank();
                }
            }
            else
            {
                pair1 = card4.rank();
            }
        }
        
        // check for straight
        if((card1.rank() == card5.rank().addToRank(-4) || (card1.rank() == Rank.TWO && card4.rank() == Rank.FIVE && card5.rank() == Rank.ACE))
                && card1.rank().compareTo(Rank.JACK) < 0 && pair1 == null && trips == null)
        {
            straight = true;
        }
        
        if(card1.suit() == card2.suit()  &&  card2.suit() == card3.suit() &&  card3.suit()== card4.suit() &&  card4.suit() == card5.suit())
        {
            flush = true;
        }
        
        if(straight && flush)
        {
            // check if it's a ace low straight

            if(card1.rank() == Rank.TWO && card5.rank() == Rank.ACE)
            {
                return scoreFactory.buildStraightFlushScore(card4.rank());
            }

            return scoreFactory.buildStraightFlushScore(card5.rank());
        }

        
        if(trips != null && pair1 != null)
        {
            return scoreFactory.buildFullHouseScore(trips, pair1);
        }
        
        if(flush)
        {
            return scoreFactory.buildFlushScore(hand);
        }
        
        if(straight)
        {
            // check if it's a ace low straight

            if(card1.rank() == Rank.TWO && card5.rank() == Rank.ACE)
            {
                return scoreFactory.buildStraightScore(card4.rank());
            }

            return scoreFactory.buildStraightScore(card5.rank());
        }
        
        if(trips != null)
        {
            return scoreFactory.buildThreeOfAKindScore(trips, kickers.last(), kickers.first());
        }
        
        if(pair1 != null)
        {
            if(pair2 != null)
            {
                return scoreFactory.buildTwoPairScore(pair1, pair2, kickers.first());
            }
            else
            {
                Iterator<Rank> iKickers = kickers.descendingIterator();
                return scoreFactory.buildPairScore(pair1, iKickers.next(),iKickers.next(), iKickers.next());
            }
        }
        return scoreFactory.buildHighCardScore(hand);
        
   
                    
//      
//        if(card1.rank() == card2.rank() && card3.rank() == card5.rank())
//        {   
//            return IHandScore.FULL_HOUSE + card3.rank().number();
//        } 
//           
//        if(card1.rank() == card3.rank() && card4.rank() == card5.rank())
//        {
//            return IHandScore.FULL_HOUSE + card3.rank().number();
//        }
//        
//        if(flush)
//        {
//            return IHandScore.FLUSH;
//        }
//        
//        if(straight)
//        {
//            return IHandScore.STRAIGHT;
//        }
//        
        
    }

}
