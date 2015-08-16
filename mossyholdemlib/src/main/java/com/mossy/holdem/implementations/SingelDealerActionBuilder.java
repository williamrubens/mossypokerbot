package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.Card;
import com.mossy.holdem.interfaces.IDealerActionBuilder;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 28/06/15.
 */
public class SingelDealerActionBuilder implements IDealerActionBuilder {
    @Override
    public ImmutableList<Action> dealAllFlops(IGameState gameState) {
        //return ImmutableList.of(Action.Factory.dealFlopAction(Card.ACE_CLUBS, Card.ACE_DIAMONDS, Card.ACE_HEARTS));
        // hack to signify that these actions have occured during game tree building instead of genuine actions from
        // a dealer
        return ImmutableList.of(Action.Factory.dealFlopAction());
    }

    @Override
    public ImmutableList<Action> dealAllTurns(IGameState gameState) {
        //return ImmutableList.of(Action.Factory.dealTurnAction(Card.ACE_SPADES));
        // hack see above
        return ImmutableList.of(Action.Factory.dealTurnAction());
    }

    @Override
    public ImmutableList<Action> dealAllRivers(IGameState gameState) {
        //return ImmutableList.of(Action.Factory.dealTurnAction(Card.ACE_SPADES));
        // hack see above
        return ImmutableList.of(Action.Factory.dealRiverAction());
    }
}
