package com.mossy.holdem.interfaces.player;

import com.mossy.holdem.ChipStack;
import com.mossy.holdem.HoleCards;

/**
 * Created by willrubens on 04/07/15.
 */
public interface IMutablePlayerState extends  IPlayerState{

    HoleCards holeCards();
    void copy(IPlayerState other);
    void setHoleCards(HoleCards newHoleCards);
    void setId(int newId);
    void setBank(ChipStack newBank);
    void setPot(ChipStack newPot);

}
