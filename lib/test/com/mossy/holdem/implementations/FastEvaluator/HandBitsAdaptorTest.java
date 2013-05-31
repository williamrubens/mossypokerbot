package com.mossy.holdem.implementations.FastEvaluator;

import com.mossy.holdem.Suit;
import com.mossy.holdem.implementations.HandFactory;
import com.mossy.holdem.interfaces.IHand;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 31/05/13
 * Time: 12:30
 * To change this template use File | Settings | File Templates.
 */
public class HandBitsAdaptorTest {
    @Test
    public void testAdaptHand() throws Exception
    {

        HandBitsAdaptor adaptor = new HandBitsAdaptor(mock(HandFactory.class));

        HandFactory handFactory = new HandFactory();

        IHand hand = handFactory.build("Ac");

        CardBits cardBits = adaptor.adaptHand(hand);

        assertEquals(cardBits.clubs, (1 << 12));
        assertEquals(cardBits.diamonds, 0);
        assertEquals(cardBits.hearts, 0);
        assertEquals(cardBits.spades, 0);

        hand = handFactory.build("AcAd");

        cardBits = adaptor.adaptHand(hand);

        assertEquals(cardBits.clubs, (1 << 12));
        assertEquals(cardBits.diamonds,  (1 << 12));
        assertEquals(cardBits.hearts, 0);
        assertEquals(cardBits.spades, 0);
        long bits = (( ((long)1 << 12) << HandBitsAdaptor.CLUBS_BIT_SHIFT) | ( ((long)1 << 12) << HandBitsAdaptor.DIAMONDS_BIT_SHIFT));
        assertEquals(cardBits.cards,bits );

        hand = handFactory.build("AcKcAd");

        cardBits = adaptor.adaptHand(hand);

        assertEquals(cardBits.clubs, ( (1 << 12) |  (1 << 11)));
        assertEquals(cardBits.diamonds,  (1 << 12));
        assertEquals(cardBits.hearts, 0);
        assertEquals(cardBits.spades, 0);
         bits = ((( ((long)1 << 12) |  ((long)1 << 11)) << HandBitsAdaptor.CLUBS_BIT_SHIFT) | ( ((long)1 << 12) << HandBitsAdaptor.DIAMONDS_BIT_SHIFT))   ;
        assertEquals(cardBits.cards,bits );


    }

    @Test
    public void testAdaptSuitBits() throws Exception
    {
        HandFactory handFactory = new HandFactory();
        HandBitsAdaptor adaptor = new HandBitsAdaptor(handFactory);

        int  aceKing = ((1 << 12) |  (1 << 11));

        String string = adaptor.adaptSuitBits(aceKing, Suit.DIAMONDS).toString();

        assertEquals(string, "AdKd");






    }
}
