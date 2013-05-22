/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem;

import java.util.Random;

/**
 *
 * @author d80050
 */
public enum Rank //implements Comparable<Rank>
{
    // the ordering below is important, that way Rank.compareTo() 
    // will return the correct answer
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);
    
    


    private int rank;
    private Rank(int number)
    {
        rank = number;
    }
    
    public int number()
    {
        return rank;
    }
 
        
    public Rank addToRank(int delta)
    {
        int newRank = ((this.rank + delta) - 2) % 13 + 2;
        if(newRank < 0)
        {
            newRank += 13;
        }
        try
        {
             return numberToRank(newRank);
        }
        catch(Exception e)
        {
            // todo log this
            return ACE; 
        }
             
       
    }
    
    static public Rank getRandomRank() 
    {
        return getRandomRank(ACE);
    }
    
    static public Rank fromChar(char c)
    {
        switch(Character.toUpperCase(c))
        {
            case '1': return ACE;
            case '2': return TWO;
            case '3': return THREE;
            case '4': return FOUR;
            case '5': return FIVE;
            case '6': return SIX;
            case '7': return SEVEN;
            case '8': return EIGHT;
            case '9': return NINE;            
            case 'T': return TEN;
            case 'J': return JACK;
            case 'Q': return QUEEN;
            case 'K': return KING;
            case 'A': return ACE;    
        }
        throw new IllegalArgumentException(String.format("Cannot convert char %c to card", c));
    }
    @Override
    public String toString() 
    {
        switch(this)
        {
            case TWO:   return "2";
            case THREE: return "3";
            case FOUR:  return "4";
            case FIVE:  return "5";
            case SIX:   return "6";
            case SEVEN: return "7";
            case EIGHT: return "8";
            case NINE:  return "9";
            case TEN:   return "T";
            case JACK:  return "J";
            case QUEEN: return "Q";
            case KING:  return "K";
            case ACE:   return "A";
        }
        return "Called toString on very dodgy rank";
        
    }
    
    static final Random rand = new Random();
    
    static public Rank getRandomRank(Rank maxRank) 
    {
        try
        {
            return Rank.numberToRank(rand.nextInt(maxRank.number()) + 1);
        }
        catch (Exception ex)
        {
             // todo log this
            
             return TWO;
        }
       
    }
    
    static private Rank numberToRank(int n) throws Exception
    {
        switch(n)
        {
            case 1: return ACE;
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            case 5: return FIVE;
            case 6: return SIX;
            case 7: return SEVEN;
            case 8: return EIGHT;
            case 9: return NINE;
            case 10: return TEN;
            case 11: return JACK;
            case 12: return QUEEN;
            case 13: return KING;
            case 14: return ACE;                
        }
        throw new Exception("Dodgy rank number");
    }
    
    
    
}
