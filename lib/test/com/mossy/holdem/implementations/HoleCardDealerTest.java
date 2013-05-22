package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.interfaces.IDeck;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 17/05/13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class HoleCardDealerTest {


    @Test
    public void testDeal() throws Exception
    {

        HoleCardDealer dealer = new HoleCardDealer(5);

        IDeck mockedDeck = mock(IDeck.class);
        when(mockedDeck.dealTopCard()).thenReturn(Card.ACE_CLUBS);

        ImmutableList<HoleCards> holeCards = dealer.deal(mockedDeck);

        verify(mockedDeck, times(5*2)).dealTopCard();

        for(HoleCards hc : holeCards)
        {
            assertEquals(hc.card1(), Card.ACE_CLUBS);
            assertEquals(hc.card2(), Card.ACE_CLUBS);
        }
    }
}
