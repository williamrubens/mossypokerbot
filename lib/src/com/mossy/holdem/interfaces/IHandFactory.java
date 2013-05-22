/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.interfaces;

import java.util.List;

import com.mossy.holdem.HoleCards;
import com.mossy.holdem.Card;

/**
 *
 * @author d80050
 */
public interface IHandFactory 
{
    //IHand generateRandom(HandType handType)  throws Exception;
    IHand build(String handString) throws Exception;
    IHand build(HoleCards holeCards, List<Card> boardCards) throws Exception;
}
