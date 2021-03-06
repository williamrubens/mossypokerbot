/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.interfaces;

import java.util.List;

import com.mossy.pokerbot.HoleCards;
import com.mossy.pokerbot.Card;

/**
 *
 * @author d80050
 */
public interface IHandFactory
{
    //IHand generateRandom(HandType handType)  throws Exception;
    IHand build(String handString);
    IHand build(HoleCards holeCards, List<Card> boardCards);

    IHand build(List<Card> cards);
}
