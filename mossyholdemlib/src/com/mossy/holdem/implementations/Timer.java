/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mossy.holdem.implementations;

/**
 *
 * @author d80050
 */
public class Timer 
{
    long totalTime = 0;
    long timerStart = 0;
    int numTimings = 0;
    boolean running = false;
    String name = null;

    Timer() {}

    Timer(String n) { name = n;}
    
    
    public void startTimer() throws Exception 
    {
        if(running)
        {
            String ex = name == null ? "Timer already running" : String.format("Timer %s already running", name)       ;
            throw new Exception(ex);
        }
        numTimings++;
        running = true;
        timerStart = System.currentTimeMillis();
    }
    
    public void stopTimer() throws Exception 
    {
        if(!running)
        {
            String ex = name == null ? "Timer not running" : String.format("Timer %s not running", name);
            throw new Exception(ex);
        }
        totalTime +=  System.currentTimeMillis() - timerStart;
        running = false;        
    }
    
    public double totalTime() { return totalTime; }
    
    public double avgTime()
    {
        return (double) totalTime / (double) numTimings;
    }
    
    
}
