/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.pokerbot;

import java.util.Random;

/**
 *
 * @author d80050
 */
public enum Suit 
{
    SPADES,
    HEARTS,
    DIAMONDS,
    CLUBS ;
    
    private static Suit numberToSuit(int n)
    {
        switch(n)
        {
            case 0: return SPADES;
            case 1: return HEARTS;
            case 2: return DIAMONDS;
            case 3: return CLUBS;
            default: throw new IllegalArgumentException("Suits numbers go from 0-3");
        }
    }
    
    public static Suit fromChar(char c)
    {
        switch(Character.toUpperCase(c))
        {
            case 'S': return SPADES;
            case 'H': return HEARTS;
            case 'D': return DIAMONDS;
            case 'C': return CLUBS;
            default: throw new IllegalArgumentException(String.format("Illegal character %c for suit",c));
        }
    }
    
    public char toChar() 
    {
        switch(this)
        {
            case SPADES:   return 's';
            case HEARTS:   return 'h';
            case DIAMONDS: return 'd';
            case CLUBS:    return 'c';
            default: return 'X';
        }
    }
    
    static final Random rand = new Random();
    
    static public Suit getRandomSuit()
    {
        try
        {
            return numberToSuit(rand.nextInt(4));
        }
        catch (Exception e)
        {
            // todo log this
            
            return SPADES;
        }
    }
}
