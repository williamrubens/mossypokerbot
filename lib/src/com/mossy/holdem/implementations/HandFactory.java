/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableSortedSet;

import java.util.*;

import com.mossy.holdem.*;

import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.IHand;
import com.mossy.holdem.interfaces.IHandFactory;

/**
 *
 * @author d80050
 */
public class HandFactory implements IHandFactory
{
    
    private final Random rand = new Random();

    public IHand generateRandom(HandType handType, StandardDeckFactory deckFactory)
    {
        switch(handType)
        {
            case STRAIGHT_FLUSH:  return generateRandomStraightFlush();
            case FOUR_OF_A_KING:  return generateRandomFourOfAKind(deckFactory);
            case FULL_HOUSE:      return generateRandomFullHouse(deckFactory);
            case FLUSH:           return generateRandomFlush(deckFactory);
            case STRAIGHT:        return generateRandomStraight();
            case THREE_OF_A_KIND: return generateRandomThreeOfAKind(deckFactory);
            case TWO_PAIR:        return generateRandomTwoPair(deckFactory);
            case PAIR:            return generateRandomPair(deckFactory);
            case HIGH_CARD:       return generateRandomHighCard(deckFactory);
        }
        return null;
    }
    
    private ArrayList<Card> pickNRandomDifferentCards(int nCards)
    {
        if(nCards > 5)
        {
            throw new IllegalArgumentException("Don't yet know how to generate more than 5 random cards")   ;
        }

        TreeSet<Rank> ranks = new TreeSet<Rank>();

        ArrayList<Card> cards = new ArrayList<>();

        while(ranks.size() != nCards)
        {
            Rank randomRank =  Rank.getRandomRank();

            if(ranks.contains(randomRank))
            {
                continue;
            }
            ranks.add(randomRank);

            if(ranks.size() == 5)
            {
                Iterator<Rank> iRank = ranks.iterator();
                boolean straight = true;
                Rank previousRank = iRank.next();
                while(iRank.hasNext())
                {
                    Rank nextRank = iRank.next();
                    //annoying edge case of 2-3-4-5-A

                    if(previousRank.addToRank(1) != nextRank)
                    {
                        if(!(previousRank == Rank.FIVE && nextRank == Rank.ACE))
                        {
                            straight = false;
                            break;
                        }

                    }
                    previousRank = nextRank;
                }
                // make sure we don't accidentally crate a straight
                if(straight)
                {
                    ranks.remove(randomRank);
                    continue;
                }
            }
        }

        TreeSet<Suit> suits = new TreeSet<Suit>();

        for(Rank randomRank : ranks)
        {
            Suit randomSuit = Suit.getRandomSuit();
            // make sure that there are more than one suit
            while(suits.size() == 1 && suits.contains(randomSuit))
            {
                randomSuit = Suit.getRandomSuit();
            }
            suits.add(randomSuit);

            Card card = Card.from(randomRank, randomSuit);
            cards.add(card);
        }

        return cards;
    }

    private IHand generateRandomStraightFlush() 
    {
        
        Rank randomRank = Rank.getRandomRank(Rank.TEN);
        Suit randomSuit = Suit.getRandomSuit();
        
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        
        builder.add(Card.from(randomRank, randomSuit));
        builder.add(Card.from(randomRank.addToRank(1), randomSuit));
        builder.add(Card.from(randomRank.addToRank(2), randomSuit));
        builder.add(Card.from(randomRank.addToRank(3), randomSuit));
        builder.add(Card.from(randomRank.addToRank(4), randomSuit));
        
        return new Hand(builder.build());
    }
    
    private IHand generateRandomFourOfAKind(StandardDeckFactory deckFactory) 
    {
        
        Hand randomHand = new Hand();
        
        Rank randomRank = Rank.getRandomRank();
        
        IDeck deck = deckFactory.build();
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        
        builder.add(deck.pickRandom(randomRank));
        builder.add(deck.pickRandom(randomRank));
        builder.add(deck.pickRandom(randomRank));
        builder.add(deck.pickRandom(randomRank));
        builder.add(deck.dealTopCard());
              
        return new Hand(builder.build());
        
    }
    
    private IHand generateRandomFullHouse(StandardDeckFactory deckFactory) 
    {
        
        Hand randomHand = new Hand();
        
        Rank randomTrips = Rank.getRandomRank();
        Rank randomPair = null;
        while((randomPair = Rank.getRandomRank()) == randomTrips) {}
        
        IDeck deck = deckFactory.build();
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        
        builder.add(deck.pickRandom(randomTrips));
        builder.add(deck.pickRandom(randomTrips));
        builder.add(deck.pickRandom(randomTrips));
        builder.add(deck.pickRandom(randomPair));
        builder.add(deck.pickRandom(randomPair));
              
        return new Hand(builder.build());
        
    }
    
    private IHand generateRandomFlush(StandardDeckFactory deckFactory) 
    {
        ArrayList<Card> cards = pickNRandomDifferentCards(5);

        Suit randomSuit = Suit.getRandomSuit();
        
        IDeck deck = deckFactory.build();
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());

        builder.add(Card.from(cards.get(0).rank(), randomSuit));
        builder.add(Card.from(cards.get(1).rank(), randomSuit));
        builder.add(Card.from(cards.get(2).rank(), randomSuit));
        builder.add(Card.from(cards.get(3).rank(), randomSuit));
        builder.add(Card.from(cards.get(4).rank(), randomSuit));

        return new Hand(builder.build());
        
    }
    
    private IHand generateRandomStraight() 
    {
        
        Rank randomRank = Rank.getRandomRank(Rank.TEN);
        
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        
        builder.add(Card.from(randomRank, Suit.getRandomSuit()));
        builder.add(Card.from(randomRank.addToRank(1), Suit.getRandomSuit()));
        builder.add(Card.from(randomRank.addToRank(2), Suit.getRandomSuit()));
        builder.add(Card.from(randomRank.addToRank(3), Suit.SPADES));
        builder.add(Card.from(randomRank.addToRank(4), Suit.HEARTS));
        
        return new Hand(builder.build());
        
    }
    
    private IHand generateRandomThreeOfAKind(StandardDeckFactory deckFactory) 
    {
        ArrayList<Card> cards = pickNRandomDifferentCards(3);
        
        IDeck deck = deckFactory.build();
        deck.shuffle();
        
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        
        builder.add(deck.pickRandom(cards.get(0).rank()));
        builder.add(deck.pickRandom(cards.get(0).rank()));
        builder.add(deck.pickRandom(cards.get(0).rank()));
        builder.add(cards.get(1));
        builder.add(cards.get(2));
              
        return new Hand(builder.build());
        
    }
    
    private IHand generateRandomTwoPair(StandardDeckFactory deckFactory) 
    {
        
        Hand randomHand = new Hand();
        
        ArrayList<Card> cards = pickNRandomDifferentCards(3);
        
        IDeck deck = deckFactory.build();
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        
        builder.add(deck.pickRandom(cards.get(0).rank()));
        builder.add(deck.pickRandom(cards.get(0).rank()));
        builder.add(deck.pickRandom(cards.get(1).rank()));
        builder.add(deck.pickRandom(cards.get(1).rank()));
        builder.add(cards.get(2));
              
        return new Hand(builder.build());
        
    }
    
    private IHand generateRandomPair(StandardDeckFactory deckFactory) 
    {        
        Hand randomHand = new Hand();
        
        ArrayList<Card> cards = pickNRandomDifferentCards(4);
        
        IDeck deck = deckFactory.build();
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        
        builder.add(deck.pickRandom(cards.get(0).rank()));
        builder.add(deck.pickRandom(cards.get(0).rank()));
        builder.add(cards.get(1));
        builder.add(cards.get(2));
        builder.add(cards.get(3));
              
        return new Hand(builder.build());
        
    }
   
    private IHand generateRandomHighCard(StandardDeckFactory deckFactory) 
    {  
                
        ArrayList<Card> cards = pickNRandomDifferentCards(5);
        
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        builder.add(cards.get(0));
        builder.add(cards.get(1));
        builder.add(cards.get(2));
        builder.add(cards.get(3));
        builder.add(cards.get(4));
              
        return new Hand(builder.build());
        
    }

    @Override
    public IHand build(String handString)  throws Exception
    {
        if(handString.length() % 2 != 0)
        {
            throw new Exception("Hand string has odd number of characters");
        }
        
        java.util.ArrayList<String> cardStrings = new java.util.ArrayList<String>();
        
        
        String remainingString = handString;
        
        int strIndex = handString.length() - 2;
        
        while(remainingString.length() > 0)
        {            
            cardStrings.add(remainingString.substring(strIndex));
            remainingString = remainingString.substring(0, strIndex);
            strIndex -= 2;                        
        }
        
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
                
        for(String cardString : cardStrings)
        {
            builder.add(Card.fromString(cardString));
        }
        
        return new Hand(builder.build());
    }

    @Override
    public IHand build(HoleCards holeCards, List<Card> boardCards) throws Exception
    {
        ImmutableSortedSet.Builder<Card> builder = ImmutableSortedSet.orderedBy(new Card.HandCardComparer());
        builder.add(holeCards.card1());
        builder.add(holeCards.card2());
                
        for(Card boardCard : boardCards)
        {
            builder.add(boardCard);
        }
        
        return new Hand(builder.build());
    }

   
    
}
