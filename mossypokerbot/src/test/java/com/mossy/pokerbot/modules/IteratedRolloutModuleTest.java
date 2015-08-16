package com.mossy.pokerbot.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.pokerbot.Card;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;
import com.mossy.pokerbot.implementations.PreFlopIncomeRateSimulator;
import com.mossy.pokerbot.interfaces.IDeckFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
public class IteratedRolloutModuleTest
{

    @Test
    public void IteratedRolloutTest() throws Exception
    {

        try
        {
            //BasicConfigurator.configure();

            int numPlayers = 10;
            int boardCards = 5;
            PreFlopHandType handType1 =  PreFlopHandType.fromHoleCards(Card.ACE_CLUBS, Card.ACE_DIAMONDS);
            PreFlopHandType handType2 =  PreFlopHandType.fromHoleCards(Card.ACE_CLUBS, Card.KING_CLUBS);

            Injector injector = Guice.createInjector(new IteratedRolloutModule(numPlayers, boardCards));

            PreFlopIncomeRateSimulator perFlopSimulator = injector.getInstance(PreFlopIncomeRateSimulator.class);
            IDeckFactory deckFactory = injector.getInstance(IDeckFactory.class);

            IncomeRate incomeRate1  =
                    perFlopSimulator.simulateIncomeRate(deckFactory.build(), null, handType1, 0.01);

            IncomeRate incomeRate2  =
                    perFlopSimulator.simulateIncomeRate(deckFactory.build(), null, handType2, 0.01);


            boolean firstBigger = incomeRate1.incomeRate() > incomeRate2.incomeRate();

            assertEquals(firstBigger, true);

        }
        catch (Exception ex) {
            //Logger.getLogger(PreFlopLookupCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);

            fail(ex.getMessage());
        }



    }
}
