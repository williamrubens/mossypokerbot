package com.mossy.holdem.implementations.state;

import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.state.IPlayerState;


/**
 * Created by williamrubens on 09/08/2014.
 */
public class PlayerState implements IPlayerState
{
    private final ChipStack bank;
    private final ChipStack pot;
    private final boolean isOut;

    public PlayerState(ChipStack bank, ChipStack pot, boolean isOut)
    {
        this.bank = bank;
        this.pot = pot;
        this.isOut = isOut;
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
}
