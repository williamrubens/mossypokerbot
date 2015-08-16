package com.mossy.pokerbot.gametree;

import com.google.common.collect.ImmutableList;

/**
 * Created by williamrubens on 09/08/2014.
 */
public interface ITreeNode<T>
{
    T data();
    ImmutableList<ITreeNode<T>> children();

}
