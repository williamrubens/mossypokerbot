package com.mossy.holdem.implementations.state;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.mossy.holdem.*;
import com.mossy.holdem.interfaces.*;
import com.mossy.holdem.interfaces.player.IMutablePlayerState;
import com.mossy.holdem.interfaces.player.IPlayerModel;
import com.mossy.holdem.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;
import com.mossy.holdem.interfaces.state.IGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static com.google.common.base.Preconditions.*;

/**
 * Created by willrubens on 30/06/15.
 */
public class MyWinActionProbabilityCalculator implements IActionProbabilityCalculator {

    final IHandStrengthCalculator handStrengthCalculator;
    final IDeckFactory deckFactory;
    final IMutablePlayerState mutableState;
    final IHandFactory handFactory;
    final IPlayerModel playerModel;

    final ConcurrentHashMap<IHand, Double> myWinProbCache = new ConcurrentHashMap<>();
    final static private Logger log = LogManager.getLogger(MyWinActionProbabilityCalculator.class);

    @Inject
    public MyWinActionProbabilityCalculator(IMutablePlayerState mutableState,
                                            IHandStrengthCalculator handStrengthCalculator,
                                            IDeckFactory deckFactory,
                                            IHandFactory handFactory,
                                            IPlayerModel playerModel) {
        this.mutableState = mutableState;
        this.handStrengthCalculator = handStrengthCalculator;
        this.deckFactory = deckFactory;
        this.handFactory = handFactory;
        this.playerModel = playerModel;

    }

    @Override
    public ImmutableMap<Action, Double> calculateProbability(IGameState state, ImmutableList<Action> actions) {
        checkNotNull(state);
        checkNotNull(actions);

        if (actions.size() == 0) {
            return ImmutableMap.of();
        }

        if (state.street() != Street.SHOWDOWN) {
            boolean playerActions = FluentIterable.from(actions).allMatch(action -> action.isPlayerAction());
            if(playerActions) {
                return calculatePlayProbability(state, actions);
            }
            // for now just calculate the probability spread evenly for actions that aren;t about winning
            double numberOfActions = (double)actions.size();
            return FluentIterable.from(actions).toMap(action -> Double.valueOf(1.0f / numberOfActions));
        }

        checkState(actions.get(0).type() == Action.ActionType.WIN);

        return calculateMyWinProbability(state, actions);
    }


    private ImmutableMap<Action, Double> calculatePlayProbability(IGameState state, ImmutableList<Action> actions) {

        int nextPlayerId = state.playerStates().get(state.nextPlayerSeat()).id();
        // could move statsholder into playermodel?
        ProbabilityTriple probabilityTriple = playerModel.calculateActionProbabilties(state);


        return FluentIterable.from(actions).toMap(action -> probabilityTriple.get(action.type()));

    }

    private ImmutableMap<Action, Double> calculateMyWinProbability(IGameState state, ImmutableList<Action> actions) {
        // for now assuming that all community cards in state have been dealt from a genuine dealer
        // instead of added during gametree construction

        IDeck deck = deckFactory.build();

        deck.dealCard(mutableState.holeCards().card1());
        deck.dealCard(mutableState.holeCards().card2());
        for (Card communitCard : state.communityCards()) {
            deck.dealCard(communitCard);
        }

        IHand myHand = handFactory.build(mutableState.holeCards(), state.communityCards());

        double effectiveHandStrength = 0.0;

        if (myWinProbCache.containsKey(myHand)) {
            effectiveHandStrength = myWinProbCache.get(myHand).doubleValue();
        } else {
            ImmutableList<Card> undealtCards = deck.undealtCards().asList();

            if (state.communityCards().size() == 3) {
                effectiveHandStrength = calculateExpectedHandStrengthFromFlop(state, undealtCards);
            } else if (state.communityCards().size() == 4) {
                effectiveHandStrength = calculateExpectedHandStrengthFromTurn(state, undealtCards);
            } else if (state.communityCards().size() == 5) {
                effectiveHandStrength = handStrengthCalculator.calculateHandStrength(mutableState.holeCards(), state.communityCards());
            } else {
                throw new RuntimeException(String.format("Cannot calculate expected hand strength from %s", state));
            }


            myWinProbCache.put(myHand, Double.valueOf(effectiveHandStrength));

            log.info(String.format("Hand: %s, EHS %f", myHand, effectiveHandStrength));
        }

        final double ehs = effectiveHandStrength;
        final double other = (1.0d - ehs) / (actions.size() - 1);

        return FluentIterable.from(actions).toMap(action -> Double.valueOf((double) (action.playerId() == mutableState.id() ? ehs : other)));
    }

    private double calculateExpectedHandStrengthFromFlop(IGameState state, ImmutableList<Card> undealtCards) {

        double effectiveHandStrength = 0;
        for (int turnCardIdx = 0; turnCardIdx < undealtCards.size(); ++turnCardIdx) {
            for (int riverCardIdx = turnCardIdx + 1; riverCardIdx < undealtCards.size(); ++riverCardIdx) {

                ImmutableList.Builder<Card> builder = ImmutableList.builder();

                ImmutableList<Card> communityCards =
                        builder.addAll(state.communityCards()).add(undealtCards.get(turnCardIdx)).add(undealtCards.get(riverCardIdx)).build();

                effectiveHandStrength += handStrengthCalculator.calculateHandStrength(mutableState.holeCards(), communityCards);
            }
        }
        // 52 - 2 - 3 = 47
        // 47 choose 2 = 47 ! / 45 ! / 2 ! = 47 * 46 / 2 = 1081
        effectiveHandStrength /= 1081d;

        return effectiveHandStrength;
    }


    private double calculateExpectedHandStrengthFromTurn(IGameState state, ImmutableList<Card> undealtCards) {

        double effectiveHandStrength = 0;
        for (int riverCardIdx = 0; riverCardIdx < undealtCards.size(); ++riverCardIdx) {

            ImmutableList.Builder<Card> builder = ImmutableList.builder();

            ImmutableList<Card> communityCards =
                    builder.addAll(state.communityCards()).add(undealtCards.get(riverCardIdx)).build();

            effectiveHandStrength += handStrengthCalculator.calculateHandStrength(mutableState.holeCards(), communityCards);
        }

        // 52 - 2 - 3 = 47
        // 47 choose 1 = 47 ! / 46 !  = 47
        effectiveHandStrength /= 47d;

        return effectiveHandStrength;
    }
}
