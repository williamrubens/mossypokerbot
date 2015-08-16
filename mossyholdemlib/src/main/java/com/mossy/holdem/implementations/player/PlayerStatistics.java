package com.mossy.holdem.implementations.player;

import com.mossy.holdem.Action;
import com.mossy.holdem.Street;
import com.mossy.holdem.interfaces.player.IPlayerStatistics;
import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 13/06/15.
 */
class PlayerStatistics implements IPlayerStatistics{

    final int id;

    // global stats
    final int nPreFlopVoluntaryActions ;
    final int nPreFlopCalls ;
    final int nPreFlopRaises ;

    // per round stats
    final boolean hasRaisedPreFlopThisRound ;
    final int preFlopRaisesThisRound ;

    // per street stats
    final int nAggresiveActions ;


    final Street currentStreet;

    public PlayerStatistics(int id) {
        this(id, 0, 0, 0, false, 0, 0, Street.PRE_FLOP);
    }

    public PlayerStatistics(int id,
                            int nPreFlopVoluntaryActions,
                            int nPreFlopCalls,
                            int nPreFlopRaises,
                            boolean hasRaisedPreFlopThisRound,
                            int preFlopRaisesThisRound,
                            int nAggressiveActions,
                            Street currentStreet) {
        this.id = id;
        this.nPreFlopVoluntaryActions = nPreFlopVoluntaryActions;
        this.nPreFlopCalls = nPreFlopCalls;
        this.nPreFlopRaises = nPreFlopRaises;

        // per round stats
        this.hasRaisedPreFlopThisRound = hasRaisedPreFlopThisRound;
        this.preFlopRaisesThisRound = preFlopRaisesThisRound;

        // per street stats
        this.nAggresiveActions = nAggressiveActions;

        this.currentStreet = currentStreet;

    }


    @Override
    public int id() {
        return id;
    }

    @Override
    public double vpip() {
        return (double)(nPreFlopRaises + nPreFlopCalls) / (double) nPreFlopVoluntaryActions;
    }

    @Override
    public double pfr() {
        return (double)nPreFlopRaises / (double) nPreFlopVoluntaryActions;
    }

    @Override
    public boolean hasRaisedPreFlopThisRound() {
        return hasRaisedPreFlopThisRound;
    }

    @Override
    public int preFlopRaisesThisRound() {
        return preFlopRaisesThisRound;
    }

    @Override
    public int aggressionCountThisStreet() {
        return nAggresiveActions;
    }

    public PlayerStatistics update(IGameState gameState, Action action) {

        Street newStreet = gameState.street();
        if (newStreet == Street.PRE_FLOP) {
            boolean newHasRaisedPreFlopThisRound = hasRaisedPreFlopThisRound();
            int newPreFlopRaisesThisRound = preFlopRaisesThisRound();

            if (currentStreet.compareTo(newStreet) > 0) {
                // means the we're back to playing a new round
                newHasRaisedPreFlopThisRound = false;
                newPreFlopRaisesThisRound = 0;
            }

            switch (action.type()) {
                case RAISE_TO:
                    return new PlayerStatistics(id, nPreFlopVoluntaryActions + 1, nPreFlopCalls, nPreFlopCalls + 1, true, preFlopRaisesThisRound + 1, nAggresiveActions + 1, newStreet);
                case CALL:
                    return new PlayerStatistics(id, nPreFlopVoluntaryActions + 1, nPreFlopCalls + 1, nPreFlopCalls, newHasRaisedPreFlopThisRound, newPreFlopRaisesThisRound, nAggresiveActions, newStreet);
                case FOLD:
                case CHECK:
                    return new PlayerStatistics(id, nPreFlopVoluntaryActions + 1, nPreFlopCalls, nPreFlopCalls, newHasRaisedPreFlopThisRound, newPreFlopRaisesThisRound, nAggresiveActions, newStreet);
            }
        }

        int newAggressiveActionsCount = nAggresiveActions;
        if(currentStreet != gameState.street()) {
            newAggressiveActionsCount = 0;
        }
        if(action.isAggressive()) {
            newAggressiveActionsCount++;
        }


        return new PlayerStatistics(id, nPreFlopVoluntaryActions + 1, nPreFlopCalls, nPreFlopCalls,
                hasRaisedPreFlopThisRound, preFlopRaisesThisRound, newAggressiveActionsCount, gameState.street());

    }


}
