/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

package org.rt.utils;

import org.rt.utils.exceptions.InvalidPrintOperationException;
import org.rt.utils.exceptions.InvalidStartOperationException;
import org.rt.utils.exceptions.InvalidStopOperationException;

public class CTimer 
{
    private long lStartTime;
    private long lStopTime;
    private long ex_lStartTime;
    private long ex_lStopTime;
    
    public CTimer()
    {
        reset();
    }

    //  this method is invoked, when data structure in the class
    //  have to be set to default values. Internally this method
    //  is invoked in the class, from the constructor and from
    //  those methods, which throws Exception
    public void reset()
    {
        this.lStartTime = -1;
        this.lStopTime = -1;
        updateExTimes();
    }
    
    public void updateExTimes()
    {
        this.ex_lStartTime = this.lStartTime;
        this.ex_lStopTime = this.lStopTime;
    }

    public long getEx_lStartTime() {
        return ex_lStartTime;
    }

    public void setEx_lStartTime(long ex_lStartTime) {
        this.ex_lStartTime = ex_lStartTime;
    }

    public long getEx_lStopTime() {
        return ex_lStopTime;
    }

    public void setEx_lStopTime(long ex_lStopTime) {
        this.ex_lStopTime = ex_lStopTime;
    }
    
    public void printDetailsOfLatestOperations(String strFmt) throws InvalidPrintOperationException
    {
        if (this.ex_lStartTime == -1 || this.ex_lStopTime == -1)
        {
            reset();
            throw new InvalidPrintOperationException("Timing details can be printed only after they have been captured atleast once");
        }
        
        System.out.println("\t\tStart-time : " + new java.sql.Timestamp(this.ex_lStartTime));
        System.out.println("\t\tStop-time : " + new java.sql.Timestamp(this.ex_lStopTime));
        System.out.println("\n\t\tTime-taken " + GlobalUtils.getFormattedTimeTakenFromMilliSeconds((this.ex_lStopTime - this.ex_lStartTime), true));
    }
    
    public void start() throws InvalidStartOperationException
    {
        if (this.lStartTime != -1)
        {
            reset();
            throw new InvalidStartOperationException("start() is invoked twice");
        }
        this.lStartTime = System.currentTimeMillis();
    }
    
    public long stop() throws InvalidStopOperationException
    {
        if (this.lStartTime == -1)
        {
            reset();
            throw new InvalidStopOperationException("stop() invoked prior to invoking start()");
        }
        this.lStopTime = System.currentTimeMillis();
        long lTimeTaken = this.lStopTime - this.lStartTime;
        updateExTimes();
        this.lStartTime = this.lStopTime = -1;
        
        return lTimeTaken;
    }
}