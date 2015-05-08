import com.mossy.holdem.Rank;
import com.mossy.holdem.Suit;

/**
 * User: William
 * Date: 14/09/2013
 * Time: 11:19
 */
public class MeerkatToMossyAdaptor
{
    com.mossy.holdem.Card adaptCard(com.biotools.meerkat.Card meerkatCard)  throws  Exception
    {
        com.mossy.holdem.Suit mossySuit;
        com.mossy.holdem.Rank mossyRank;
        switch (meerkatCard.getRank())
        {
            case com.biotools.meerkat.Card.ACE:   mossyRank = Rank.ACE; break;
            case com.biotools.meerkat.Card.KING:  mossyRank = Rank.KING; break;
            case com.biotools.meerkat.Card.QUEEN: mossyRank = Rank.QUEEN; break;
            case com.biotools.meerkat.Card.JACK:  mossyRank = Rank.JACK; break;
            case com.biotools.meerkat.Card.TEN:   mossyRank = Rank.TEN; break;
            case com.biotools.meerkat.Card.NINE:  mossyRank = Rank.NINE; break;
            case com.biotools.meerkat.Card.EIGHT: mossyRank = Rank.EIGHT; break;
            case com.biotools.meerkat.Card.SEVEN: mossyRank = Rank.SEVEN; break;
            case com.biotools.meerkat.Card.SIX:   mossyRank = Rank.SIX; break;
            case com.biotools.meerkat.Card.FIVE:  mossyRank = Rank.FIVE; break;
            case com.biotools.meerkat.Card.FOUR:  mossyRank = Rank.FOUR; break;
            case com.biotools.meerkat.Card.THREE: mossyRank = Rank.THREE; break;
            case com.biotools.meerkat.Card.TWO:   mossyRank = Rank.TWO; break;
            default: throw new Exception(String.format("Failed to find rank of meerkat card %s", meerkatCard));
        }
        switch (meerkatCard.getSuit())
        {
            case com.biotools.meerkat.Card.SPADES:   mossySuit = Suit.SPADES; break;
            case com.biotools.meerkat.Card.CLUBS:    mossySuit = Suit.CLUBS; break;
            case com.biotools.meerkat.Card.DIAMONDS: mossySuit = Suit.DIAMONDS; break;
            case com.biotools.meerkat.Card.HEARTS:   mossySuit = Suit.HEARTS; break;
            default: throw new Exception(String.format("Failed to find suit of meerkat card %s", meerkatCard));
        }

        return com.mossy.holdem.Card.from(mossyRank, mossySuit);

    }
}
