package com.mossy.holdem.implementations.state;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.ProbabilityTriple;
import com.mossy.holdem.interfaces.player.IPlayerModel;
import com.mossy.holdem.interfaces.player.IPlayerState;
import com.mossy.holdem.interfaces.player.IPlayerStatistics;
import com.mossy.holdem.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.holdem.interfaces.state.IGameState;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

/**
 * Created by willrubens on 14/06/15.
 */
public class ActionProbabilityCalculatorTest {



    @DataProvider(name = "actionTypes")
    public Object[][] createData() {
        return new Object[][] {
                { ImmutableList.of(Action.ActionType.CHECK, Action.ActionType.BET, Action.ActionType.FOLD), Float.valueOf(1.0f / 3.0f) },
                { ImmutableList.of(Action.ActionType.CALL, Action.ActionType.RAISE_TO, Action.ActionType.FOLD), Float.valueOf(1.0f / 3.0f) },
                { ImmutableList.of(Action.ActionType.CALL, Action.ActionType.FOLD), Float.valueOf(1.0f / 3.0f) },
                { ImmutableList.of(Action.ActionType.WIN), Float.valueOf(1.0f) },
                };
    }

    private ImmutableMap<Action.ActionType, Float> builldExpected(ImmutableList<Action.ActionType> actionTypes, Float expected) {

        if(actionTypes.size() == 2 ) {
            return ImmutableMap.of(actionTypes.get(0), expected + expected, actionTypes.get(1), expected );
        }

        return FluentIterable.from(actionTypes).toMap(input -> Float.valueOf(expected));
    }

    @Test(dataProvider = "actionTypes")
    public void actionProbabilityCalculator_test_test(ImmutableList<Action.ActionType> actionTypes, Float expected) {
        IPlayerStatisticsHolder statsHolder = mock(IPlayerStatisticsHolder.class);
        IPlayerModel playerModel = mock(IPlayerModel.class);
        IGameState gameState = mock(IGameState.class);
        IPlayerModel player = mock(IPlayerModel.class);
        IPlayerState playerState = mock(IPlayerState.class);
        IPlayerStatistics playerStats = mock(IPlayerStatistics.class);

        stub(gameState.nextPlayer()).toReturn(playerState);
        stub(playerState.id()).toReturn(0);
        stub(statsHolder.getStats(0)).toReturn(playerStats);
        stub(playerModel.calculateActionProbabilties(playerStats, gameState )).toReturn(new ProbabilityTriple(expected, expected, expected));


        ActionProbabilityCalculator probCalc = new ActionProbabilityCalculator(statsHolder, playerModel);

        ImmutableMap<Action.ActionType, Float> actual = probCalc.calculateProbability(gameState, actionTypes);

        Assert.assertEqualsNoOrder(actual.entrySet().toArray(), builldExpected(actionTypes, expected).entrySet().toArray());

    }
}
