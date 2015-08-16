package com.mossy.pokerbot.interfaces;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 23/05/13
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public interface IIteratedRolloutSimulator
{
    void iterateSimulation( double tolerance) throws Exception ;
}
