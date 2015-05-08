package com.mossy.holdem.implementations.state;

import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.state.IPlayerInfo;


/**
 * Created by williamrubens on 09/08/2014.
 */
public class PlayerInfo implements IPlayerInfo
{
    private final ChipStack bank;
    private final ChipStack pot;
    private final boolean isOut;
    private final boolean hasChecked;

    public PlayerInfo(ChipStack bank, ChipStack pot, boolean isOut, boolean hasChecked)
    {
        this.bank = bank;
        this.pot = pot;
        this.isOut = isOut;
        this.hasChecked = hasChecked;
    }

    @Override
    public ChipStack bank()
    {
        return bank;
    }

    @Override
    public ChipStack pot()
    {
        return pot;
    }

    @Override
    public boolean isOut()
    {
        return isOut;
    }

    @Override
    public boolean hasChecked()
    {
        return false;
    }
}
