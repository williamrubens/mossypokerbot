package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mossy.holdem.Card;
import com.mossy.holdem.HoleCards;
import com.mossy.holdem.IncomeRate;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;

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

        ImmutableMap mockedMap = Mockito.mock(ImmutableMap.class) ;

        UnmodifiableIterator<HoleCards> mockedHoleCardIterator = Mockito.mock(UnmodifiableIterator.class);
        ImmutableList mockedHoleCardList = Mockito.mock(ImmutableList.class);

        Mockito.when(mockedHoleCardList.iterator()).thenReturn(mockedHoleCardIterator);
        Mockito.when(mockedHoleCardIterator.hasNext()).thenReturn(true, true, false);
        Mockito.when(mockedHoleCardIterator.next()).thenReturn(HoleCards.from(Card.ACE_CLUBS, Card.ACE_DIAMONDS));

        Mockito.when(mockedMap.get(Matchers.anyObject())).thenReturn(new IncomeRate(+1, 0, 0)).thenReturn(new IncomeRate(-1, 0, 0));

        NegativeIncomeRateFolder folder = new NegativeIncomeRateFolder();

        ImmutableList foldedCards = folder.foldHoleCards(mockedHoleCardList, mockedMap);

        Mockito.verify(mockedHoleCardIterator, Mockito.times(3)).hasNext();
        Mockito.verify(mockedMap, Mockito.times(2)).get(Matchers.anyObject());


        assertEquals(foldedCards.size(), 1);
        assertEquals(((HoleCards)foldedCards.get(0)).card1() , Card.ACE_CLUBS);
        assertEquals(((HoleCards)foldedCards.get(0)).card2() , Card.ACE_DIAMONDS);



    }
}
