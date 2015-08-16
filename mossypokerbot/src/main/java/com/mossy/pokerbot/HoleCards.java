package com.mossy.pokerbot;


/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 15/05/13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
final public class HoleCards
{
    private Card card1;
    private Card card2;

    public static HoleCards from(Card card1, Card card2)
    {
        return new HoleCards(card1, card2);
    }

    private HoleCards(Card _card1, Card _card2)
    {
        card1 = _card1;
        card2 = _card2;
    }

    public Card card1() { return card1; }
    public Card card2() { return card2; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HoleCards)) return false;

        HoleCards holeCards = (HoleCards) o;

        if (!card1.equals(holeCards.card1)) return false;
        if (!card2.equals(holeCards.card2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = card1.hashCode();
        result = 31 * result + card2.hashCode();
        return result;
    }
}

