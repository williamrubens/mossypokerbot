package com.mossy.holdem.implementations.state;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.Street;
import com.mossy.holdem.implementations.player.PlayerState;
import com.mossy.holdem.interfaces.player.IPlayerState;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertEqualsNoOrder;

import static org.mockito.Mockito.mock;

/**
 * Created by williamrubens on 30/08/2014.
 */
public class FixedLimitStateTest
{

    @DataProvider(name = "PlayerData")
    public Object[][] createPlayerData() {
        return new Object[][] {
                // two players pre-flop
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false)), Street.PRE_FLOP, 0, 1},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.ONE_CHIP, false, false)), Street.PRE_FLOP,  0, 0},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS,false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.ONE_CHIP, false, false)), Street.PRE_FLOP,  0, 1},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS, false, false)), Street.PRE_FLOP,  0, 0},
//                // two players post-flop
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false)), Street.FLOP, 0, 1},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, true)), Street.FLOP, 0, 0},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS, false, false)), Street.FLOP,  0, 0},
                // three players pre-flop
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(2, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false)), Street.PRE_FLOP, 0, 1},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.ONE_CHIP, false, false),
                                  new PlayerState(2, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false)), Street.PRE_FLOP, 0, 2},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.NO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.ONE_CHIP, false, false),
                                  new PlayerState(2, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS, false, false)), Street.PRE_FLOP, 2, 0},

                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS, false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS, false, false),
                                  new PlayerState(2, ChipStack.NO_CHIPS, ChipStack.TWO_CHIPS, false, false)), Street.PRE_FLOP, 2, 1},
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.of(16), false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.of(12), true, false),
                                  new PlayerState(2, ChipStack.NO_CHIPS, ChipStack.of(12), false, false)), Street.FLOP, 0, 2},

                // lots of players
                {ImmutableList.of(new PlayerState(0, ChipStack.NO_CHIPS, ChipStack.of(4), false, false),
                                  new PlayerState(1, ChipStack.NO_CHIPS, ChipStack.of(0), false, false),
                                  new PlayerState(2, ChipStack.NO_CHIPS, ChipStack.of(0), false, false),
                                  new PlayerState(3, ChipStack.NO_CHIPS, ChipStack.of(0), false, false),
                                  new PlayerState(4, ChipStack.NO_CHIPS, ChipStack.of(0), false, true),
                                  new PlayerState(5, ChipStack.NO_CHIPS, ChipStack.of(0), false, true),
                                  new PlayerState(6, ChipStack.NO_CHIPS, ChipStack.of(4), false, false)), Street.FLOP, 3, 1}
                };
    }

    @Test(dataProvider = "PlayerData")
    public void AFixedLimteState_findNextPlayer_getsCorrectPlayer(ImmutableList<IPlayerState> players, Street street, int dealerPos, int nextPlayerId) {

        ChipStack lowerLimit = ChipStack.of(2);
        ChipStack higherLimit = ChipStack.of(4);

        FixedLimitState state = new FixedLimitState(lowerLimit, higherLimit, players, ImmutableMap.of(), street, dealerPos, 0, 3, Action.Factory.dealHoleCards());

        IPlayerState nextPlayer = state.nextPlayer();

        assertEquals(nextPlayer.id(),  nextPlayerId);


    }


    @Test(dataProvider = "PlayerData")
    public void AFixedLimteState_iterateFromDealer_startsWithPlayerAfterDealer(ImmutableList<IPlayerState> players, Street street, int dealerPos, int nextPlayerId) {

        ChipStack lowerLimit = ChipStack.of(2);
        ChipStack higherLimit = ChipStack.of(4);

        FixedLimitState state = new FixedLimitState(lowerLimit, higherLimit, players, ImmutableMap.of(), street, dealerPos, 0, 3, Action.Factory.dealHoleCards());

        UnmodifiableIterator<IPlayerState> nextPlayerIter = state.fromDealerIterator();
        int nexPlayerSeat = state.playerSeatAfter(dealerPos);

        assertEquals(nextPlayerIter.next().id(),  players.get(nexPlayerSeat).id());


    }

    @Test(dataProvider = "PlayerData")
    public void AFixedLimteState_iterateFromDealer_finishesWithDealer(ImmutableList<IPlayerState> players, Street street, int dealerPos, int nextPlayerId) {

        ChipStack lowerLimit = ChipStack.of(2);
        ChipStack higherLimit = ChipStack.of(4);

        FixedLimitState state = new FixedLimitState(lowerLimit, higherLimit, players, ImmutableMap.of(), street, dealerPos, 0, 3, Action.Factory.dealHoleCards());

        UnmodifiableIterator<IPlayerState> nextPlayerIter = state.fromDealerIterator();

        IPlayerState nextPlayer = null;

        while(nextPlayerIter.hasNext()){
            nextPlayer = nextPlayerIter.next();
        }

        assertEquals(nextPlayer.id(),  players.get(dealerPos).id());


    }
}