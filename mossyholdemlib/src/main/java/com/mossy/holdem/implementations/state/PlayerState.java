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
    private final boolean hasChecked;
    private final int id;

    public PlayerState(int id, ChipStack bank, ChipStack pot, boolean isOut, boolean hasChecked)
    {
        this.bank = bank;
        this.pot = pot;
        this.isOut = isOut;
        this.hasChecked = hasChecked;
        this.id = id;
    }

    public PlayerState(int id, ChipStack bank, ChipStack pot)
    {
        this(id, bank, pot, false, false);
    }

    @Override
    public int id() { return id; }

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
        return hasChecked;
    }
}
