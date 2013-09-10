package com.mossy.holdem.implementations;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import com.mossy.holdem.IncomeRate;
import com.mossy.holdem.PreFlopHandType;
import com.mossy.holdem.interfaces.IPreFlopIncomeRateVendor;
import org.apache.log4j.Logger;

import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 09/09/2013
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public class PreFlopIncomeRateLookupFactory
{
    final static private Logger log = Logger.getLogger(PreFlopIncomeRateLookupFactory.class);

    public IPreFlopIncomeRateVendor Build(String iteratedRolloutFilename) throws Exception
    {
        CSVReader reader = new CSVReader(new FileReader(iteratedRolloutFilename));
        String [] nextLine;

        ImmutableMap.Builder<PreFlopHandType, IncomeRate> builder = ImmutableMap.builder();

        while ((nextLine = reader.readNext()) != null)
        {
            try
            {
                // hack!
                if(nextLine[4] != "9")
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
