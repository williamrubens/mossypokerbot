package com.mossy.holdem.implementations.player;

import com.google.inject.Singleton;
import com.mossy.holdem.ChipStack;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.interfaces.player.IMutablePlayerState;
import com.mossy.holdem.interfaces.player.IPlayerState;

/**
 * Created by willrubens on 04/07/15.
 */
@Singleton
public class MutablePlayerState implements IMutablePlayerState
{
    private ChipStack bank;
    private ChipStack pot;
    private boolean isOut;
    private boolean hasChecked;
    private int id;
    private HoleCards holeCards;

//    public MutablePlayerState(int id, ChipStack bank, ChipStack pot, boolean isOut, boolean hasChecked)
//    {
//        this.bank = bank;
//        this.pot = pot;
//        this.isOut = isOut;
//        this.hasChecked = hasChecked;
//        this.id = id;
//    }
//
//    public MutablePlayerState(int id, ChipStack bank, ChipStack pot)
//    {
//        this(id, bank, pot, false, false);
//    }

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

    @Override
    public HoleCards holeCards() {
        return holeCards;
    }

    @Override
    public void copy(IPlayerState other) {
        this.setBank(other.bank());
        this.setId(other.id());
        this.setPot(other.pot());
        this.isOut = other.isOut();
        this.hasChecked = other.hasChecked();
    }

    @Override
    public void setHoleCards(HoleCards newHoleCards) {
        holeCards = newHoleCards;
    }

    @Override
    public void setId(int newId) {
        id = newId;
    }

    @Override
    public void setBank(ChipStack newBank) {
        bank = newBank;
    }

    @Override
    public void setPot(ChipStack newPot) {
        pot = newPot;
    }
}