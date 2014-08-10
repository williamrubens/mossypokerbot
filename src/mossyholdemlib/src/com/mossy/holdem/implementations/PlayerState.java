package com.mossy.holdem.implementations;

import com.mossy.holdem.ChipStack;
import com.mossy.holdem.interfaces.IPlayerState;


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
    public IPlayerState play(ChipStack chips) throws Exception
    {
        if(bank.compareTo(chips) < 0)
        {
            throw new Exception(String.format("Cannot play amount %s as only have %s in bank", chips.toString(), bank.toString()));
        }
        return new PlayerState(bank.subtract(chips), pot.add(chips), isOut);
    }

    @Override
    public boolean isOut()
    {
        return isOut;
    }
}
