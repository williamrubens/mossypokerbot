package com.mossy.holdem.gametree;

import com.mossy.holdem.interfaces.state.IGameState;

/**
 * Created by willrubens on 16/06/15.
 */
public interface IHoldemTreeBuilder {
    ITreeNode<IHoldemTreeData> buildTree(IGameState initialState);
}
