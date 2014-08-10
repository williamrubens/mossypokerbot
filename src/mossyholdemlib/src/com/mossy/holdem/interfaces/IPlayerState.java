package com.mossy.holdem.interfaces;

import com.mossy.holdem.ChipStack;

import java.math.BigDecimal;

/**
 * Created by williamrubens on 09/08/2014.
 */
public interface IPlayerState
{
    ChipStack bank();
    ChipStack pot();
    IPlayerState play(ChipStack chips) throws Exception;
    boolean   isOut();


}

