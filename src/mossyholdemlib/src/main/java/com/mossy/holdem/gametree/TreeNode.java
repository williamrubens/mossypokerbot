package com.mossy.holdem.gametree;

import com.google.common.collect.ImmutableList;

/**
 * Created by williamrubens on 09/08/2014.
 */
public class TreeNode<T> implements ITreeNode<T>
{
    T data;
    ImmutableList<ITreeNode<T>> children;

    public TreeNode(T t, ImmutableList<ITreeNode<T>> children)
    {
        data = t;
        this.children = children;
    }

    @Override
    public T data()
    {
        return data;
    }

    @Override
    public ImmutableList<ITreeNode<T>> children()
    {
        return children;
    }
}
