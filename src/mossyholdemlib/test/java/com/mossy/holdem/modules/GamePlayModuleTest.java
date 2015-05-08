package com.mossy.holdem.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.holdem.Card;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.implementations.PreFlopIncomeRateSimulator;
import com.mossy.holdem.interfaces.IDeckFactory;
import com.mossy.holdem.interfaces.IKnowledgeBasedHoldemPlayer;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by williamrubens on 17/07/2014.
 */
public class GamePlayModuleTest
{

    @Test
    public void getInstance_returns_whenRun() throws Exception
    {

        try
        {
            Injector injector = Guice.createInjector(new GamePlayModule());

            IKnowledgeBasedHoldemPlayer var = injector.getInstance(IKnowledgeBasedHoldemPlayer.class);

            assertEquals(true, true);

        }
        catch (Exception ex) {
            //Logger.getLogger(PreFlopLookupCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);

            fail(ex.getMessage());
        }




    }
}

