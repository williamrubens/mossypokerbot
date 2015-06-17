package com.mossy.holdem.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossy.holdem.interfaces.IHoldemPlayer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by williamrubens on 17/07/2014.
 */
public class FixedLimitPlayModuleTest
{

    @Test
    public void getInstance_returns_whenRun() throws Exception
    {

        try
        {
            Injector injector = Guice.createInjector(new FixedLimitPlayModule());

            IHoldemPlayer var = injector.getInstance(IHoldemPlayer.class);

            assertEquals(true, true);

        }
        catch (Exception ex) {
            //Logger.getLogger(PreFlopLookupCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);

            fail(ex.getMessage());
        }




    }
}

