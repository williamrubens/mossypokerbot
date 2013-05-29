package com.mossy.holdem.modules;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.holdem.Card;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.implementations.PreFlopIncomeRateStore;
import com.mossy.holdem.implementations.PreFlopIncomeRateSimulator;
import com.mossy.holdem.interfaces.IDeckFactory;
import org.apache.log4j.BasicConfigurator;
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
            BasicConfigurator.configure();

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
