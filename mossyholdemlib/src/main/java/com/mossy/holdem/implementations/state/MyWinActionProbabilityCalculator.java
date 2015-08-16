package com.mossy.holdem.implementations.state;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.Action;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.interfaces.state.IActionProbabilityCalculator;
import com.mossy.holdem.interfaces.state.IGameState;
import static com.google.common.base.Preconditions.*;

/**
 * Created by willrubens on 30/06/15.
 */
public class MyWinActionProbabilityCalculator implements IActionProbabilityCalculator {

    MyWinActionProbabilityCalculator(HoleCards myHoleCards)

    @Override
    public ImmutableMap<Action.ActionType, Float> calculateProbability(IGameState state, ImmutableList<Action.ActionType> actions) {

        checkNotNull(state);
        checkNotNull(actions);
        checkElementIndex(0, actions.size());

        if(actions.get(0) != Action.ActionType.WIN) {
            throw new RuntimeException("MyWinActionProbabilityCalculator only works with WIN Actions, not" + actions.get(0).toString());
        }


    }
}
