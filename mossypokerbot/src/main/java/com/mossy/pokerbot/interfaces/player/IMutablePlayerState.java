package com.mossy.pokerbot.interfaces.player;

import com.mossy.pokerbot.ChipStack;
import com.mossy.pokerbot.HoleCards;

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
