package com.mossy.holdem.gametree;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.ChipStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by willrubens on 13/06/15.
 */
public class MutableTreeNode<T> implements  IMutableTreeNode<T> {

    T data;
    List<ITreeNode<T>> children = new LinkedList<>();

    public MutableTreeNode(T t)
    {
        data = t;
    }

    @Override
    public T data()
    {
        return data;
    }

    @Override
    public ImmutableList<ITreeNode<T>> children()
    {
        return ImmutableList.copyOf(children);
    }

    @Override
    public void addChild(ITreeNode newChild) {
        children.add(newChild);
    }

    @Override
    public String toString()  {
        return data.toString();
    }

    // debug feature

    private ChipStack ev = ChipStack.NO_CHIPS;
    public void setEv(ChipStack newEv) { ev = newEv;}
    public ChipStack ev() { return ev;}


}
