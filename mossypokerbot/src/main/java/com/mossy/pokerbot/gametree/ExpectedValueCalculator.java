package com.mossy.pokerbot.gametree;

import com.google.common.collect.FluentIterable;
import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.interfaces.player.IPlayerState;

/**
 * Created by willrubens on 13/06/15.
 */
public class ExpectedValueCalculator implements IExpectedValueCalculator {

    @Override
    public ChipStack calculateExpectedValue(IPlayerState currentPlayerState, ITreeNode<IHoldemTreeData> root) {
        return recursiveCalcEv(currentPlayerState, root);
    }

    private ChipStack recursiveCalcEv(IPlayerState currentPlayerState, ITreeNode<IHoldemTreeData> parentNode) {

        if(parentNode.children().isEmpty()) {
            //IPlayerState futurePlayerState = parentNode.data().state().playerStates().stream().filter(player -> player.id() == currentPlayerState.id()).findFirst().get();
            IPlayerState futurePlayerState = FluentIterable.from(parentNode.data().state().playerStates()).filter(player -> player.id() == currentPlayerState.id()).first().get();
            ChipStack ev =  futurePlayerState.bank().subtract(currentPlayerState.bank());
            setNodeEv(parentNode, ev);
            return ev;
        }

        ChipStack runningExpectedValue = ChipStack.NO_CHIPS;
        for(ITreeNode<IHoldemTreeData> childNode : parentNode.children()) {
            ChipStack childExpecedValue = recursiveCalcEv(currentPlayerState, childNode).multiply(childNode.data().probability());

            runningExpectedValue = runningExpectedValue.add(childExpecedValue);
        }
        setNodeEv(parentNode, runningExpectedValue);

        return runningExpectedValue;
    }

    private void setNodeEv(ITreeNode<IHoldemTreeData> node, ChipStack ev) {
        if(node instanceof MutableTreeNode) {
            // this is used in debugging...
            MutableTreeNode<IHoldemTreeData> mutableState = (MutableTreeNode<IHoldemTreeData>)node;
            mutableState.setEv(ev);
        }


    }


}
