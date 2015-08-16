package com.mossy.pokerbot.implementations.player;

import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.interfaces.player.IPlayerState;


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

    @Override
    public String toString()
    {
        return String.format("id %d, bank %s, in pot %s, isout %b, hasChecked %b", id(), bank(), pot(), isOut(), hasChecked());
    }

}


