/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem;


import java.util.Comparator;

/**
 *
 * @author d80050
 */

// Explicitly DOES NOT extend IComparable<Card> because depending on situations
// you may or may not want to take suit into account for comparison
// (eg when comparing cards for evaluation, you don't want to consider suit
// but when adding them to a collection, you do want to)

final public class Card{

    private Rank rank;
    private Suit suit;
    
    
    final static public Card ACE_CLUBS = new Card(Rank.ACE, Suit.CLUBS);
    final static public Card KING_CLUBS = new Card(Rank.KING, Suit.CLUBS);
    final static public Card QUEEN_CLUBS = new Card(Rank.QUEEN, Suit.CLUBS);
    final static public Card JACK_CLUBS = new Card(Rank.JACK, Suit.CLUBS);
    final static public Card TEN_CLUBS = new Card(Rank.TEN, Suit.CLUBS);
    final static public Card NINE_CLUBS = new Card(Rank.NINE, Suit.CLUBS);
    final static public Card EIGHT_CLUBS = new Card(Rank.EIGHT, Suit.CLUBS);
    final static public Card SEVEN_CLUBS = new Card(Rank.SEVEN, Suit.CLUBS);
    final static public Card SIX_CLUBS = new Card(Rank.SIX, Suit.CLUBS);
    final static public Card FIVE_CLUBS = new Card(Rank.FIVE, Suit.CLUBS);
    final static public Card FOUR_CLUBS = new Card(Rank.FOUR, Suit.CLUBS);
    final static public Card THREE_CLUBS = new Card(Rank.THREE, Suit.CLUBS);
    final static public Card TWO_CLUBS = new Card(Rank.TWO, Suit.CLUBS);
    
    final static public Card ACE_HEARTS = new Card(Rank.ACE, Suit.HEARTS);
    final static public Card KING_HEARTS = new Card(Rank.KING, Suit.HEARTS);
    final static public Card QUEEN_HEARTS = new Card(Rank.QUEEN, Suit.HEARTS);
    final static public Card JACK_HEARTS = new Card(Rank.JACK, Suit.HEARTS);
    final static public Card TEN_HEARTS = new Card(Rank.TEN, Suit.HEARTS);
    final static public Card NINE_HEARTS = new Card(Rank.NINE, Suit.HEARTS);
    final static public Card EIGHT_HEARTS = new Card(Rank.EIGHT, Suit.HEARTS);
    final static public Card SEVEN_HEARTS = new Card(Rank.SEVEN, Suit.HEARTS);
    final static public Card SIX_HEARTS = new Card(Rank.SIX, Suit.HEARTS);
    final static public Card FIVE_HEARTS = new Card(Rank.FIVE, Suit.HEARTS);
    final static public Card FOUR_HEARTS = new Card(Rank.FOUR, Suit.HEARTS);
    final static public Card THREE_HEARTS = new Card(Rank.THREE, Suit.HEARTS);
    final static public Card TWO_HEARTS = new Card(Rank.TWO, Suit.HEARTS);
    
    final static public Card ACE_SPADES = new Card(Rank.ACE, Suit.SPADES);
    final static public Card KING_SPADES = new Card(Rank.KING, Suit.SPADES);
    final static public Card QUEEN_SPADES = new Card(Rank.QUEEN, Suit.SPADES);
    final static public Card JACK_SPADES = new Card(Rank.JACK, Suit.SPADES);
    final static public Card TEN_SPADES = new Card(Rank.TEN, Suit.SPADES);
    final static public Card NINE_SPADES = new Card(Rank.NINE, Suit.SPADES);
    final static public Card EIGHT_SPADES = new Card(Rank.EIGHT, Suit.SPADES);
    final static public Card SEVEN_SPADES = new Card(Rank.SEVEN, Suit.SPADES);
    final static public Card SIX_SPADES = new Card(Rank.SIX, Suit.SPADES);
    final static public Card FIVE_SPADES = new Card(Rank.FIVE, Suit.SPADES);
    final static public Card FOUR_SPADES = new Card(Rank.FOUR, Suit.SPADES);
    final static public Card THREE_SPADES = new Card(Rank.THREE, Suit.SPADES);
    final static public Card TWO_SPADES = new Card(Rank.TWO, Suit.SPADES);
            
    final static public Card ACE_DIAMONDS = new Card(Rank.ACE, Suit.DIAMONDS);
    final static public Card KING_DIAMONDS = new Card(Rank.KING, Suit.DIAMONDS);
    final static public Card QUEEN_DIAMONDS = new Card(Rank.QUEEN, Suit.DIAMONDS);
    final static public Card JACK_DIAMONDS = new Card(Rank.JACK, Suit.DIAMONDS);
    final static public Card TEN_DIAMONDS = new Card(Rank.TEN, Suit.DIAMONDS);
    final static public Card NINE_DIAMONDS = new Card(Rank.NINE, Suit.DIAMONDS);
    final static public Card EIGHT_DIAMONDS = new Card(Rank.EIGHT, Suit.DIAMONDS);
    final static public Card SEVEN_DIAMONDS = new Card(Rank.SEVEN, Suit.DIAMONDS);
    final static public Card SIX_DIAMONDS = new Card(Rank.SIX, Suit.DIAMONDS);
    final static public Card FIVE_DIAMONDS = new Card(Rank.FIVE, Suit.DIAMONDS);
    final static public Card FOUR_DIAMONDS = new Card(Rank.FOUR, Suit.DIAMONDS);
    final static public Card THREE_DIAMONDS = new Card(Rank.THREE, Suit.DIAMONDS);
    final static public Card TWO_DIAMONDS = new Card(Rank.TWO, Suit.DIAMONDS);


    public static Card from(Rank r, Suit s)
    {
        return new Card(r,s);
    }

    private Card(Rank r, Suit s)
    {
        rank = r;
        suit =s;
    }

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        
        if(!(o instanceof Card))
        {
            return false;
        }
        
        Card rhs = (Card)o;
        
        return this.rank == rhs.rank && this.suit == rhs.suit;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 67 * hash + (this.rank != null ? this.rank.hashCode() : 0);
        hash = 67 * hash + (this.suit != null ? this.suit.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString()
    {
        //return String.format("%s of %s", rank, suit);
        return String.format("%s%c", rank, suit.toChar());
    }
    
    static public Card fromString(String cardString)
    {
        if(cardString.length() != 2)
        {
            throw new IllegalArgumentException("Card.fromString only works with strings of length 2");
        }
        Rank rank = Rank.fromChar(cardString.charAt(0));
        Suit suit = Suit.fromChar(cardString.charAt(1));
        
        return new Card(rank, suit);
    }



    public static class SuitThenRankComparer implements Comparator<Card>
    {
        @Override
        public int compare(Card lhs, Card rhs)
        {
            int suitDiff = lhs.suit().compareTo(rhs.suit());

            if(suitDiff != 0)
            {
                return suitDiff;
            }
            return lhs.rank().compareTo(rhs.rank());
        }
    }

    /**
     *
     * @author d80050
     */
    public static class RankThenSuitComparer implements Comparator<Card>
    {
        @Override
        public int compare(Card lhs, Card rhs)
        {
            int rankDiff = lhs.rank().compareTo(rhs.rank());

            if(rankDiff != 0)
            {
                return rankDiff;
            }
            return lhs.suit().compareTo(rhs.suit());
        }
    }
}
