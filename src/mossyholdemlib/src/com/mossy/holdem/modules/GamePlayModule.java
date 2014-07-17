package com.mossy.holdem.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mossy.holdem.implementations.KnowledgeBasedHoldemPlayer;
import com.mossy.holdem.implementations.PreFlopIncomeRateLookup;
import com.mossy.holdem.implementations.PreFlopIncomeRateLookupFactory;
import com.mossy.holdem.interfaces.IKnowledgeBasedHoldemPlayer;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateVendor;

/**
 * Created by williamrubens on 11/05/2014.
 */
public class GamePlayModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IKnowledgeBasedHoldemPlayer.class).to(KnowledgeBasedHoldemPlayer.class);

    }

    @Provides
    IPreFlopIncomeRateVendor providePreFlopIncomeRateVendor() throws Exception
    {
        PreFlopIncomeRateLookupFactory factory = new PreFlopIncomeRateLookupFactory();

        return factory.Build("prefloplookup_iterated.csv");
    }
}
