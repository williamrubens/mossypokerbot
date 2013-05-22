/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.interfaces;

import java.io.Writer;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 *
 * @author d80050
 */
public interface IPreFlopLookupCalculator {


    void generatePreFlopLookup(SummaryStatistics stats, Writer writer, double tolerence, IDeck deck, IPreFlopHandTypeAdaptor adaptor, int numPlayers, IHandFactory handFactory, IHandEvaluator handEvaluator) ;
    
}
