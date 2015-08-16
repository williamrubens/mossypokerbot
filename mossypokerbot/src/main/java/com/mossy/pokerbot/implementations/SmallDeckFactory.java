package com.mossy.pokerbot.implementations;

import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.interfaces.IDeck;
import com.mossy.pokerbot.interfaces.IDeckFactory;

import java.util.ArrayList;

/**
 * Created by willrubens on 09/06/15.
 */
public class SmallDeckFactory implements IDeckFactory {

    @Override
    public IDeck build() {
        ArrayList<Card> cards = new ArrayList<Card>();

        cards.add(Card.ACE_CLUBS);

        cards.add(Card.ACE_HEARTS);

        cards.add(Card.ACE_SPADES);

        cards.add(Card.ACE_DIAMONDS);

        return new Deck(cards);
    }
}
