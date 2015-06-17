package com.mossy.holdem.gametree;

import com.google.common.collect.FluentIterable;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.player.IPlayerState;

/**
 * Created by willrubens on 13/06/15.
 */
public class ExpectedValueCalculator {

    public ChipStack calculateExpectedValue(IPlayerState currentPlayerState, ITreeNode<IHoldemTreeData> root) {
        return recursiveDepthFirstExpectedValueCalculator(currentPlayerState, root);
    }

    private ChipStack recursiveDepthFirstExpectedValueCalculator(IPlayerState currentPlayerState, ITreeNode<IHoldemTreeData> parentNode) {

        if(parentNode.children().isEmpty()) {
            //IPlayerState futurePlayerState = parentNode.data().state().playerStates().stream().filter(player -> player.id() == currentPlayerState.id()).findFirst().get();
            IPlayerState futurePlayerState = FluentIterable.from(parentNode.data().state().playerStates()).filter(player -> player.id() == currentPlayerState.id()).first().get();
            return futurePlayerState.bank().subtract(currentPlayerState.bank());
        }

        ChipStack runningExpectedValue = ChipStack.NO_CHIPS;
        for(ITreeNode<IHoldemTreeData> childNode : parentNode.children()) {
            ChipStack childExpecedValue = recursiveDepthFirstExpectedValueCalculator(currentPlayerState, childNode);

            runningExpectedValue = runningExpectedValue.add(childExpecedValue.multiply(childNode.data().probability()));
        }
        return runningExpectedValue;
    }
}
