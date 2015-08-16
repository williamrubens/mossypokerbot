/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.mossy.pokerbot.PreFlopHandType;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.interfaces.IDeck;
import com.mossy.pokerbot.interfaces.IPreFlopHandTypeAdaptor;

/**
 *
 * @author d80050
 */
public class PreFlopHandTypeAdaptor implements IPreFlopHandTypeAdaptor
{                     /*
    @Override
    public PreFlopHandType adaptHoleCardsToPreFlopHandType(Card h1, Card h2)
    {
        int multiplerOffset = Rank.ACE.number();
        // there are 169 preflop hands, 13 pairs, 13 choose 2 = 78 suited and 13 choose 2 = 78 unsuited
        if(h1.rank() == h2.rank())
        {
            return new PreFlopHandType(h1.rank().number(), String.format("%s%s",h1.rank(), h1.rank()));
        }
        
        // make sure h1 is the highest card
        if(h2.rank().compareTo(h1.rank()) > 0)
        {
            Card temp = h1;
            h1 = h2;
            h2 = temp;
        }
        
        if(h1.suit() == h2.suit())
        {
            int id =  multiplerOffset * multiplerOffset + h1.rank().number() * multiplerOffset + h2.rank().number() ;
            return new PreFlopHandType(id, String.format("%s%ss", h1.rank(), h2.rank()));
        }
        
        int id = 3 * multiplerOffset * multiplerOffset + h1.rank().number() * multiplerOffset + h2.rank().number() ;
        return new PreFlopHandType(id, String.format("%s%so", h1.rank(), h2.rank()));
        
    }

    @Override
    public ArrayList<Card> adaptPreFlopHandTypeToHoleCards(PreFlopHandType handType)
    {
        String handTypeString = handType.toString();
        Rank rank1 = Rank.fromChar(handTypeString.charAt(0));
        Rank rank2 = Rank.fromChar(handTypeString.charAt(1));

    }

    @Override
    public HoleCards adaptPreFlopHandType(PreFlopHandType handtype)
    {
        Suit randomSuit1 = Suit.getRandomSuit();
        Suit randomSuit2 = Suit.getRandomSuit();

        while(randomSuit2 == randomSuit1) { randomSuit2 = Suit.getRandomSuit(); }

        Card card1 = new Card(handtype.rank1(), randomSuit1);
        Card card2 = new Card(handtype.rank2(), handtype.suited() ? randomSuit1 : randomSuit2);

        return  new HoleCards(card1, card2);
    }
                                 */
    @Override
    public ImmutableSortedSet<PreFlopHandType> adaptDeck(IDeck deck)
    {
        ImmutableSortedSet.Builder<PreFlopHandType> builder = ImmutableSortedSet.naturalOrder();
        ImmutableList<Card> cards = ImmutableList.copyOf(deck.allCards());
        for(int idx = 0; idx < cards.size(); ++idx)
        { 
            for(int jdx = idx + 1; jdx < cards.size(); ++jdx)
            {
                builder.add(PreFlopHandType.fromHoleCards(cards.get(idx), cards.get(jdx)));
            }
        }
        return builder.build();
    }



    
}
