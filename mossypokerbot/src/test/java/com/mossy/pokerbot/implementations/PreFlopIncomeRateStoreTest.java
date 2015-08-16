package com.mossy.pokerbot.implementations;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopIncomeRateStoreTest {
                                                  /*
    PreFlopIncomeRateStore incomeRateBuilder;

    @Before
    public void setUp() throws Exception
    {
        incomeRateBuilder = new PreFlopIncomeRateStore(new SummaryStatisticsFactory() );


    }

    @Test
    public void testAddWinnings() throws Exception
    {
        PreFlopHandType handType = PreFlopHandType.fromHoleCards(Card.ACE_CLUBS, Card.ACE_DIAMONDS);
        SummaryStatistics stats = new SummaryStatistics();

        Random rand = new Random();

        for(int i = 0; i < 10000; ++i)
        {
            double randomNumber = (rand.nextDouble() * 2.0 - 1.0 )* 5.0;
            incomeRateBuilder.addWinnings(handType, randomNumber);
            stats.addValue(randomNumber);
        }

        IncomeRate rate = incomeRateBuilder.getIncomeRate(handType);

        ImmutableMap<PreFlopHandType, IncomeRate> handTypeToIncomeRate = incomeRateBuilder.getMap();

        assertEquals(stats.getMean(), rate.incomeRate(), 0.01);
        assertEquals(stats.getStandardDeviation(), rate.standardDeviation(), 0.01);
        assertEquals(stats.getMean(), handTypeToIncomeRate.get(handType).incomeRate(), 0.01);
        assertEquals(stats.getStandardDeviation(), handTypeToIncomeRate.get(handType).standardDeviation(), 0.01);
    }

                                                 */
}
