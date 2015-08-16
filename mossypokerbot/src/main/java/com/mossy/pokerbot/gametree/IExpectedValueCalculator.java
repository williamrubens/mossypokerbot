package com.mossy.pokerbot.gametree;

import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.interfaces.player.IPlayerState;

/**
 * Created by willrubens on 18/06/15.
 */
public interface IExpectedValueCalculator {
    ChipStack calculateExpectedValue(IPlayerState currentPlayerState, ITreeNode<IHoldemTreeData> root) ;
}
