/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.neww;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.rt.extended.ExtendedArrayList;
import org.rt.utils.FileUtils;

/**
 *
 * @author Krishna
 */
public class CServerCounterManager extends ServerComponent
{
    //  file containing names of counters associated with server.
    //  file must contain information in following format :-
    //  <Counter_name> : <Window_Size_For_Counter_Maintainence>
    private String strCounterFileLocation;
    private Map<String, ArrayList<Object>> dictCounters;
    private Map<String, ArrayList<java.sql.Timestamp>> dictTimeStampOfCounters;
    private Map<String, Object> dictCountersLock;

    {
        dictCounters = new HashMap<>();
        dictTimeStampOfCounters = new HashMap<>();
        dictCountersLock = new HashMap<>();
    }

    public CServerCounterManager() {
        super(false);
    }

    public CServerCounterManager(String strCounterFileLocation) throws Exception
    {
        super(true);
        
        this.strCounterFileLocation = strCounterFileLocation;
        if (FileUtils.doesFileExists(strCounterFileLocation) == false)
        {
            throw new FileNotFoundException("Counter File Location does not exists " + strCounterFileLocation);
        }
        Properties objTempProperties = new Properties();
        
        objTempProperties.load(new FileInputStream(strCounterFileLocation));
        
        Enumeration<Object> objTempPropertyNames = objTempProperties.keys();
        
        while (objTempPropertyNames.hasMoreElements())
        {
            String strPropertyName = (String)objTempPropertyNames.nextElement();
            int iMaxSize = Integer.parseInt((String)objTempProperties.getProperty(strPropertyName));
            this.dictCounters.put(strPropertyName, new ExtendedArrayList<>(iMaxSize));
            this.dictTimeStampOfCounters.put(strPropertyName, new ExtendedArrayList<java.sql.Timestamp>(iMaxSize));
            this.dictCountersLock.put(strPropertyName, new Object());
        }
    }

    public String getStrCounterFileLocation() {
        return strCounterFileLocation;
    }

    public Map<String, ArrayList<Object>> getDictCounters() {
        return dictCounters;
    }

    public Map<String, Object> getDictCountersLock() {
        return dictCountersLock;
    }

    public Map<String, ArrayList<Timestamp>> getDictTimeStampOfCounters() {
        return dictTimeStampOfCounters;
    }

    //  don't set counters value externally. Instead, Use this method.
    //  Use this method to increment the integer type counters.
    public void incrementCounter(String strCounterName) throws Exception
    {
        if (
                this.dictCounters.containsKey(strCounterName) == false ||
                this.dictCountersLock.containsKey(strCounterName) == false ||
                this.dictTimeStampOfCounters.containsKey(strCounterName)
            )
        {
            throw new Exception("Invalid counter passed " + strCounterName);
        }
        
        synchronized(this.dictCountersLock.get(strCounterName))
        {
            ArrayList<Object> lsCounter = this.dictCounters.get(strCounterName);
            int iCurrentValue = (Integer)lsCounter.get(lsCounter.size() - 1);
            ((ExtendedArrayList)lsCounter).add(new Integer(iCurrentValue + 1), true);
            ((ExtendedArrayList)this.dictTimeStampOfCounters.get(strCounterName)).add(new java.sql.Timestamp(System.currentTimeMillis()), true);
        }
    }
    
    //  don't set counters value externally. Instead, Use this method.
    //  Use this method to decrement the integer type counters.
    public void decrementCounter(String strCounterName) throws Exception
    {
        if (
                this.dictCounters.containsKey(strCounterName) == false ||
                this.dictCountersLock.containsKey(strCounterName) == false
            )
        {
            throw new Exception("Invalid counter passed " + strCounterName);
        }
        
        synchronized(this.dictCountersLock.get(strCounterName))
        {
            ArrayList<Object> lsCounter = this.dictCounters.get(strCounterName);
            int iCurrentValue = (Integer)lsCounter.get(lsCounter.size() - 1);
            ((ExtendedArrayList)lsCounter).add(new Integer(iCurrentValue - 1), true);
            ((ExtendedArrayList)this.dictTimeStampOfCounters.get(strCounterName)).add(new java.sql.Timestamp(System.currentTimeMillis()), true);
        }
    }
    
    //  don't set counters value externally. Instead, Use this method.
    //  Use this method to increment the integer type counters.
    public void setCounter(String strCounterName, Object objNewVal) throws Exception
    {
        if (
                this.dictCounters.containsKey(strCounterName) == false ||
                this.dictCountersLock.containsKey(strCounterName) == false
            )
        {
            throw new Exception("Invalid counter passed " + strCounterName);
        }
        
        synchronized(this.dictCountersLock.get(strCounterName))
        {
            ArrayList<Object> lsCounter = this.dictCounters.get(strCounterName);
            ((ExtendedArrayList)lsCounter).add(objNewVal, true);
            ((ExtendedArrayList)this.dictTimeStampOfCounters.get(strCounterName)).add(new java.sql.Timestamp(System.currentTimeMillis()), true);
        }
    }
}
