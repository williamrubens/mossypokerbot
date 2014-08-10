package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.GameStage;
import com.mossy.holdem.interfaces.IGameState;
import com.mossy.holdem.interfaces.IPlayerState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sun.security.x509.IPAddressName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by williamrubens on 09/08/2014.
 */
@RunWith(Parameterized.class)
public class FLPreFlopStateTest
{


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                {2, 0, 1, Action.Factory.callAction() },
                {2, 1, 0, Action.Factory.callAction()},
                {3, 0, 0, Action.Factory.callAction()},
                {3, 1, 1, Action.Factory.callAction()},
                {3, 2, 2, Action.Factory.callAction()},
                {2, 0, 2, Action.Factory.callAction()},

                };
        return Arrays.asList(data);
    }


    ArrayList<IPlayerState> players = new ArrayList<IPlayerState>();
    int numPlayers;
    int dealerPosition;
    int nextToPlay;
    ChipStack smallBlind = ChipStack.ONE_CHIP;
    ChipStack bigBlind = ChipStack.TWO_CHIPS;
    Action action;


    //given
    //when
    //then.
    public FLPreFlopStateTest(int numPlayers, int dealerPosition, Action action)
    {
        for(int p = 0; p < numPlayers; p++)
        {
            IPlayerState playerState = mock(IPlayerState.class);
            stub(playerState.pot()).toReturn(ChipStack.NO_CHIPS);
            players.add(playerState);
        }
        this.dealerPosition = dealerPosition;
        this.numPlayers = numPlayers;
        this.nextToPlay = nextToPlay = (dealerPosition + 1 ) % numPlayers;
        this.action = action;
    }

    @Test
    public void NewPreFlopState_CheckAction_AdjustsPlayerPot() throws Exception
    {
        //given

        // post small blind
        int smallBlindPos = (dealerPosition + 1) % numPlayers;
        int bigBlindPos = (dealerPosition + 2) % numPlayers;

        ChipStack amountToCall;

        if(nextToPlay == smallBlindPos)
        {
            amountToCall = bigBlind.subtract(smallBlind);
        }
        else
        {
            amountToCall = bigBlind;
        }

        IPlayerState nextPlayer = players.get(nextToPlay);

        stub(players.get(smallBlindPos).pot()).toReturn(smallBlind);
        stub(players.get(bigBlindPos).pot()).toReturn(bigBlind);
        stub(nextPlayer.play(amountToCall)).toReturn(mock(IPlayerState.class));

        IGameState flPreflop = new FLPreFlopState(smallBlind, bigBlind, ImmutableList.copyOf(players), dealerPosition, nextToPlay );

        // when
        IGameState nextGameState = flPreflop.nextState(action);

        // then
        verify(nextPlayer, times(1)).play(amountToCall);
    }

}
