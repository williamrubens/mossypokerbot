/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem;

/**
 *
 * @author d80050
 */
final public class PreFlopHandType implements Comparable<PreFlopHandType>
{
    private Rank rank1;
    private Rank rank2;
    private boolean suited;
    private String name;

    static public PreFlopHandType fromHoleCards(HoleCards holeCards)
    {
        return new PreFlopHandType(holeCards.card1().rank(), holeCards.card2().rank(), holeCards.card1().suit() == holeCards.card2().suit());
    }

    static public PreFlopHandType fromHoleCards(Card card1, Card card2)
    {
        return new PreFlopHandType(card1.rank(), card2.rank(), card1.suit() == card2.suit());
    }
    
    private PreFlopHandType(Rank r1, Rank r2, boolean s)
    {
        if(r1.compareTo(r2) > 0)
        {
            rank1 = r1;
            rank2 = r2;
        }
        else
        {
            rank1 = r2;
            rank2 = r1;
        }


        suited = s;
        computeName();
    }

    private void computeName()
    {
        name = String.format("%s%s", rank1, rank2);
        if(rank1 != rank2)
        {
            name += suited ? "s" : "o";
        }

    }

    public Rank rank1() {return rank1; }
    public Rank rank2() {return rank2; }
    public boolean suited() {return suited; }
    
    @Override
    public String toString() { return name; }

    public static PreFlopHandType fromString(String source)  throws Exception
    {
        if(source.length() != 2 && source.length() != 3)
        {
            throw new Exception(String.format("Don't know how to convert string %s to PreflopHandType", source));
        }
        Rank rank1 = Rank.fromChar(source.charAt(0));
        Rank rank2 = Rank.fromChar(source.charAt(1));
        boolean suited = false;
        if(source.length() == 3 && source.charAt(2) == 's')
        {
            suited = true;
        }
        return new PreFlopHandType(rank1, rank2, suited);
    }


    @Override
    public int hashCode() {
        int result = rank1.hashCode();
        result = 31 * result + rank2.hashCode();
        result = 31 * result + (suited ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) 
    {
        if(this == o)
        {
            return true;
        }
        
        if(!(o instanceof PreFlopHandType))
        {
            return false;
        }
        
        PreFlopHandType rhs = (PreFlopHandType)o;
        
        return this.rank1 == rhs.rank1 && this.rank2 == rhs.rank2 && this.suited == rhs.suited;
    }

    @Override
    public int compareTo(PreFlopHandType t) 
    {
        // sort alphabetically, should be the easisest
        return name.compareTo(t.name);
    }

    public HoleCards toHoleCardsNonDeterministic()
    {
        Suit randomSuit1 = Suit.getRandomSuit();
        Suit randomSuit2 = Suit.getRandomSuit();

        while(randomSuit2 == randomSuit1) { randomSuit2 = Suit.getRandomSuit(); }

        Card card1 = Card.from(rank1(), randomSuit1);
        Card card2 = Card.from(rank2(), suited() ? randomSuit1 : randomSuit2);

        return  HoleCards.from(card1, card2);

    }





    
}
