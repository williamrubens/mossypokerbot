/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot.implementations;

import com.mossy.pokerbot.interfaces.IHand;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author d80050
 */
public class HandTest {
    
    public HandTest() {
    }
/*
    @Test
    public void testCards() {
        System.out.println("cards");
        Hand instance = new Hand();
        ImmutableSortedSet expResult = null;
        ImmutableSortedSet result = instance.cards();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCardCount() {
        System.out.println("cardCount");
        Hand instance = new Hand();
        int expResult = 0;
        int result = instance.cardCount();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testHighestCard() {
        System.out.println("highestCard");
        Hand instance = new Hand();
        Card expResult = null;
        Card result = instance.highestCard();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAddCard() {
        System.out.println("addCard");
        Card card = null;
        Hand instance = new Hand();
        IHand expResult = null;
        IHand result = instance.addCard(card);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemoveCard() {
        System.out.println("removeCard");
        Card card = null;
        Hand instance = new Hand();
        IHand expResult = null;
        IHand result = instance.removeCard(card);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
*/
    @Test
    public void testToString() {
        try {
            System.out.println("toString");
            
            
            String fromString = "KcKdKsTdTh";
            
            HandFactory hf = new HandFactory();
            
            IHand myHand = hf.build(fromString);
            
            String toString = myHand.toString();
            assertEquals(fromString, toString);
            
        } catch (Exception ex) {
            Logger.getLogger(HandTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("hand.toString() test failed");
        }
    }
}