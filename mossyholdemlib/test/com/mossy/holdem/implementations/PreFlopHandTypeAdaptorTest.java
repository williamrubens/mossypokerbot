/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableSortedSet;
import com.mossy.holdem.interfaces.IDeck;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author d80050
 */
public class PreFlopHandTypeAdaptorTest {
    
    public PreFlopHandTypeAdaptorTest() {
    }


    @Test
    public void testAdaptDeck() 
    {
        System.out.println("adaptDeck");
        
        
        PreFlopHandTypeAdaptor adaptor = new PreFlopHandTypeAdaptor();
        StandardDeckFactory deckFactory = new StandardDeckFactory();
        
        IDeck deck = deckFactory.build();
        
        ImmutableSortedSet result = adaptor.adaptDeck(deck);
        assertEquals(169, result.size());
    }
}