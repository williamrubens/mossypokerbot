package com.mossy.pokerbot.gametree;

import com.mossy.pokerbot.interfaces.state.IGameState;

/**
 * Created by willrubens on 16/06/15.
 */
public interface IHoldemTreeBuilder {
    ITreeNode<IHoldemTreeData> buildTree(IGameState initialState);
}
