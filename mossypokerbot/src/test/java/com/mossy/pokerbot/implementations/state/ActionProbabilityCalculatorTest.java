package com.mossy.pokerbot.implementations.state;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.Action;
import com.mossy.pokerbot.ProbabilityTriple;
import com.mossy.pokerbot.interfaces.player.IPlayerModel;
import com.mossy.pokerbot.interfaces.player.IPlayerState;
import com.mossy.pokerbot.interfaces.player.IPlayerStatistics;
import com.mossy.pokerbot.interfaces.player.IPlayerStatisticsHolder;
import com.mossy.pokerbot.interfaces.state.IGameState;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

/**
 * Created by willrubens on 14/06/15.
 */
public class ActionProbabilityCalculatorTest {



    @DataProvider(name = "actionTypes")
    public Object[][] createData() {
        return new Object[][] {
                { ImmutableList.of(Action.ActionType.CHECK, Action.ActionType.BET, Action.ActionType.FOLD), Double.valueOf(1.0f / 3.0f) },
                { ImmutableList.of(Action.ActionType.CALL, Action.ActionType.RAISE_TO, Action.ActionType.FOLD), Double.valueOf(1.0f / 3.0f) },
                { ImmutableList.of(Action.ActionType.CALL, Action.ActionType.FOLD), Double.valueOf(1.0f / 3.0f) },
                { ImmutableList.of(Action.ActionType.WIN), Double.valueOf(1.0f) },
                };
    }

    private ImmutableMap<Action.ActionType, Double> builldExpected(ImmutableList<Action.ActionType> actionTypes, Double expected) {

        if(actionTypes.size() == 2 ) {
            return ImmutableMap.of(actionTypes.get(0), expected + expected, actionTypes.get(1), expected );
        }

        return FluentIterable.from(actionTypes).toMap(input -> Double.valueOf(expected));
    }

    @Test(dataProvider = "actionTypes")
    public void actionProbabilityCalculator_test_test(ImmutableList<Action.ActionType> actionTypes, Double expected) {
        IPlayerStatisticsHolder statsHolder = mock(IPlayerStatisticsHolder.class);
        IPlayerModel playerModel = mock(IPlayerModel.class);
        IGameState gameState = mock(IGameState.class);
        IPlayerModel player = mock(IPlayerModel.class);
        IPlayerState playerState = mock(IPlayerState.class);
        IPlayerStatistics playerStats = mock(IPlayerStatistics.class);

        stub(gameState.nextPlayer()).toReturn(playerState);
        stub(playerState.id()).toReturn(0);
        stub(statsHolder.get(0)).toReturn(playerStats);
        stub(playerModel.calculateActionProbabilties( gameState )).toReturn(new ProbabilityTriple(expected, expected, expected));


//        ActionProbabilityCalculator probCalc = new ActionProbabilityCalculator(statsHolder, playerModel);
//
//        ImmutableMap<Action.ActionType, Double> actual = probCalc.calculateProbability(gameState, actionTypes);
//
//        Assert.assertEqualsNoOrder(actual.entrySet().toArray(), builldExpected(actionTypes, expected).entrySet().toArray());

    }
}
