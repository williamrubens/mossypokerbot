package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.IncomeRate;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 18/05/13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class NegativeIncomeRateFolderTest {
    @Test
    public void testFoldHoleCards() throws Exception
    {

        ImmutableMap mockedMap = mock(ImmutableMap.class) ;

        UnmodifiableIterator<HoleCards> mockedHoleCardIterator = mock(UnmodifiableIterator.class);
        ImmutableList mockedHoleCardList = mock(ImmutableList.class );

        when(mockedHoleCardList.iterator()).thenReturn(mockedHoleCardIterator);
        when(mockedHoleCardIterator.hasNext()).thenReturn(true, true, false);
        when(mockedHoleCardIterator.next()).thenReturn(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS));

        when(mockedMap.get(anyObject())).thenReturn(new IncomeRate(+1, 0, 0)).thenReturn(new IncomeRate(-1, 0, 0));

        NegativeIncomeRateFolder folder = new NegativeIncomeRateFolder();

        ImmutableList foldedCards = folder.foldHoleCards(mockedHoleCardList, mockedMap);

        verify(mockedHoleCardIterator, times(3)).hasNext();
        verify(mockedMap, times(2)).get(anyObject());


        assertEquals(foldedCards.size(), 1);
        assertEquals(((HoleCards)foldedCards.get(0)).card1() , Card.ACE_CLUBS);
        assertEquals(((HoleCards)foldedCards.get(0)).card2() , Card.ACE_DIAMONDS);



    }
}
