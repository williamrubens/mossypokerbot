/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.interfaces;

import com.google.common.collect.ImmutableSortedSet;
import com.mossy.holdem.PreFlopHandType;

/**
 *
 * @author d80050
 */
public interface IPreFlopHandTypeAdaptor
{

    ImmutableSortedSet<PreFlopHandType> adaptDeck(IDeck deck);
    // one to many
    //HoleCards adaptPreFlopHandType(PreFlopHandType handtype);
    // many to one => NOT DETERMINISTIC
    //PreFlopHandType adaptHoleCards(HoleCards holeCards);
}
