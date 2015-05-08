package com.mossy.holdem.interfaces.state;

import com.mossy.holdem.ChipStack;

/**
 * Created by williamrubens on 09/08/2014.
 */
public interface IPlayerInfo
{
    ChipStack bank();
    ChipStack pot();
    boolean   isOut();
    boolean   hasChecked();


}

