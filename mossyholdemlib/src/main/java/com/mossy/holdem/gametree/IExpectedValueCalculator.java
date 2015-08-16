package com.mossy.holdem.gametree;

import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.player.IPlayerState;

/**
 * Created by willrubens on 18/06/15.
 */
public interface IExpectedValueCalculator {
    ChipStack calculateExpectedValue(IPlayerState currentPlayerState, ITreeNode<IHoldemTreeData> root) ;
}