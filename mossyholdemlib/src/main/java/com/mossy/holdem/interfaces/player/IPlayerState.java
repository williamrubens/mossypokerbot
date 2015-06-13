package com.mossy.holdem.interfaces.player;

import com.mossy.holdem.ChipStack;

/**
 * Created by williamrubens on 09/08/2014.
 */
public interface IPlayerState
{
    int id();
    ChipStack bank();
    ChipStack pot();
    boolean   isOut();
    boolean   hasChecked();


}

