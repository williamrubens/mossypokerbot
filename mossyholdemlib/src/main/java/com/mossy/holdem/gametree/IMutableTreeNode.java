package com.mossy.holdem.gametree;

/**
 * Created by willrubens on 13/06/15.
 */
public interface IMutableTreeNode<T> extends ITreeNode <T> {

    void addChild(ITreeNode<T> newChild);
}
