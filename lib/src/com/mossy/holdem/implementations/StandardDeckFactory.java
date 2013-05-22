/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.IDeckFactory;
import java.util.ArrayList;

/**
 *
 * @author d80050
 */
public class StandardDeckFactory implements IDeckFactory
{
    public IDeck build()
    {
        ArrayList<Card> cards = new ArrayList<Card>();
        
        
        cards.add(Card.ACE_CLUBS);
        cards.add(Card.KING_CLUBS);
        cards.add(Card.QUEEN_CLUBS);
        cards.add(Card.JACK_CLUBS);
        cards.add(Card.TEN_CLUBS);
        cards.add(Card.NINE_CLUBS);
        cards.add(Card.EIGHT_CLUBS);
        cards.add(Card.SEVEN_CLUBS);
        cards.add(Card.SIX_CLUBS);
        cards.add(Card.FIVE_CLUBS);
        cards.add(Card.FOUR_CLUBS);
        cards.add(Card.THREE_CLUBS);
        cards.add(Card.TWO_CLUBS);
        
        cards.add(Card.ACE_HEARTS);
        cards.add(Card.KING_HEARTS);
        cards.add(Card.QUEEN_HEARTS);
        cards.add(Card.JACK_HEARTS);
        cards.add(Card.TEN_HEARTS);
        cards.add(Card.NINE_HEARTS);
        cards.add(Card.EIGHT_HEARTS);
        cards.add(Card.SEVEN_HEARTS);
        cards.add(Card.SIX_HEARTS);
        cards.add(Card.FIVE_HEARTS);
        cards.add(Card.FOUR_HEARTS);
        cards.add(Card.THREE_HEARTS);
        cards.add(Card.TWO_HEARTS);
        
        cards.add(Card.ACE_SPADES);
        cards.add(Card.KING_SPADES);
        cards.add(Card.QUEEN_SPADES);
        cards.add(Card.JACK_SPADES);
        cards.add(Card.TEN_SPADES);
        cards.add(Card.NINE_SPADES);
        cards.add(Card.EIGHT_SPADES);
        cards.add(Card.SEVEN_SPADES);
        cards.add(Card.SIX_SPADES);
        cards.add(Card.FIVE_SPADES);
        cards.add(Card.FOUR_SPADES);
        cards.add(Card.THREE_SPADES);
        cards.add(Card.TWO_SPADES);
        
        cards.add(Card.ACE_DIAMONDS);
        cards.add(Card.KING_DIAMONDS);
        cards.add(Card.QUEEN_DIAMONDS);
        cards.add(Card.JACK_DIAMONDS);
        cards.add(Card.TEN_DIAMONDS);
        cards.add(Card.NINE_DIAMONDS);
        cards.add(Card.EIGHT_DIAMONDS);
        cards.add(Card.SEVEN_DIAMONDS);
        cards.add(Card.SIX_DIAMONDS);
        cards.add(Card.FIVE_DIAMONDS);
        cards.add(Card.FOUR_DIAMONDS);
        cards.add(Card.THREE_DIAMONDS);
        cards.add(Card.TWO_DIAMONDS);
        
        return new Deck(cards);
    }
}
