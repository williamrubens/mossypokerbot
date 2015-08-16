package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.IDeck;
import com.mossy.holdem.interfaces.IDeckFactory;
import com.mossy.holdem.interfaces.IDealerActionBuilder;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 18/05/15.
 */
public class DealerActionBuilder implements IDealerActionBuilder {

    final IDeckFactory deckFactory;

    @Inject
    public DealerActionBuilder(IDeckFactory deckFactory) {
        this.deckFactory = deckFactory;
    }

    @Override
    public ImmutableList<Action> dealAllFlops(IGameState gameState) {
        IDeck deck = deckFactory.build();
        // remove those cards that are already dealt
        gameState.communityCards().forEach(deck::dealCard);

        ImmutableList.Builder<Action> builder = ImmutableList.builder();

        ImmutableList<Card> remainingCards = deck.undealtCards().asList();

        for(int flopCard1 = 0; flopCard1 < remainingCards.size(); ++flopCard1) {
            for (int flopCard2 = flopCard1 + 1; flopCard2 < remainingCards.size(); ++flopCard2) {
                for (int flopCard3 = flopCard2 + 1; flopCard3 < remainingCards.size(); ++flopCard3) {
                    builder.add(Action.Factory.dealFlopAction(remainingCards.get(flopCard1), remainingCards.get(flopCard2), remainingCards.get(flopCard3)));
                }
            }
        }

        return builder.build();
    }

    @Override
    public ImmutableList<Action> dealAllTurns(IGameState gameState) {
        IDeck deck = deckFactory.build();
        // remove those cards that are already dealt
        gameState.communityCards().forEach(deck::dealCard);

        ImmutableList.Builder<Action> builder = ImmutableList.builder();

        ImmutableList<Card> remainingCards = deck.undealtCards().asList();

        for(int flopCard1 = 0; flopCard1 < remainingCards.size(); ++flopCard1) {
            builder.add(Action.Factory.dealTurnAction(remainingCards.get(flopCard1)));
        }

        return builder.build();
    }

    @Override
    public ImmutableList<Action> dealAllRivers(IGameState gameState) {
        IDeck deck = deckFactory.build();
        // remove those cards that are already dealt
        gameState.communityCards().forEach(deck::dealCard);

        ImmutableList.Builder<Action> builder = ImmutableList.builder();

        ImmutableList<Card> remainingCards = deck.undealtCards().asList();

        for(int flopCard1 = 0; flopCard1 < remainingCards.size(); ++flopCard1) {
            builder.add(Action.Factory.dealRiverAction(remainingCards.get(flopCard1)));
        }

        return builder.build();
    }

}
