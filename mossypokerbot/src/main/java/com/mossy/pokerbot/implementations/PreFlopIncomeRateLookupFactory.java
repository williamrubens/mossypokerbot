package com.mossy.pokerbot.implementations;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import com.mossy.pokerbot.IncomeRate;
import com.mossy.pokerbot.PreFlopHandType;
import com.mossy.pokerbot.interfaces.IPreFlopIncomeRateVendor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 09/09/2013
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopIncomeRateLookupFactory
{
    final static private Logger log = LogManager.getLogger(PreFlopIncomeRateLookupFactory.class);

    public IPreFlopIncomeRateVendor Build(String iteratedRolloutFilename) throws Exception
    {

        InputStream input = getClass().getResourceAsStream(iteratedRolloutFilename);

        CSVReader reader = new CSVReader(new InputStreamReader(input));
        String [] nextLine;

        ImmutableMap.Builder<PreFlopHandType, IncomeRate> builder = ImmutableMap.builder();

        while ((nextLine = reader.readNext()) != null)
        {
            try
            {
                // hack!
                String numPlayers = nextLine[4].trim();
                if(!numPlayers.equals("9"))
                {
                    continue;
                }

                PreFlopHandType preFlopHand = PreFlopHandType.fromString(nextLine[0]);
                IncomeRate ir = new IncomeRate(Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[2]), Double.parseDouble(nextLine[3]));

                builder.put(preFlopHand, ir);

            }
            catch (Exception ex)
            {
                log.error(ex.getMessage());
            }


        }

        return new PreFlopIncomeRateLookup(builder.build());
    }

}
