package com.mossy.pokerbot.interfaces.player;

import com.mossy.pokerbot.ChipStack;

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

