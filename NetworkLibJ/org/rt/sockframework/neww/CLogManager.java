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
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.rt.utils.CLogger;
import org.rt.utils.FileUtils;
import org.rt.utils.exceptions.DirectoryFoundInsteadOfFileException;

/**
 *
 * @author Krishna
 */
public class CLogManager extends ServerComponent
{
    //  Log file must contain information in
    //  given below format :-
    //  Log_Type : <file-name>
    //  eg. access_logs : c:\\access.txt
    private Properties dictLogProperties;
    private HashMap dictLoggers;
    private String strFileName;

    {
        dictLogProperties = new Properties();
        dictLoggers = new HashMap();
        strFileName = new String();
    }

    public CLogManager() {
        super(false);
    }
    
    public CLogManager(String strFileName) throws FileNotFoundException, 
                                        DirectoryFoundInsteadOfFileException, IOException
    {
        super(true);
        
        this.strFileName = strFileName;
        if (FileUtils.doesFileExists(strFileName) == false)
        {
            throw new FileNotFoundException("File does not exists : " + strFileName);
        }
        
       dictLogProperties.load(new FileInputStream(this.strFileName));
       
       Enumeration<Object> enumProperties = dictLogProperties.keys();
       
       while (enumProperties.hasMoreElements())
       {
           String strLogType = (String)enumProperties.nextElement();
           String strTempFileName = dictLogProperties.getProperty(strLogType);
           
           CLogger objLogger = new CLogger(strTempFileName);
           
           dictLoggers.put(strLogType, objLogger);
       }
    }

    public void logInfo(String strLogType, String strMsg) throws Exception
    {
        ((CLogger)this.dictLoggers.get(strLogType)).logInfo(strMsg);
    }
    
    public void logDebug(String strLogType, String strMsg) throws Exception
    {
        ((CLogger)this.dictLoggers.get(strLogType)).logDebug(strMsg);
    }
    
    public void logWarn(String strLogType, String strMsg) throws Exception
    {
        ((CLogger)this.dictLoggers.get(strLogType)).logWarn(strMsg);
    }
    
    public void logError(String strLogType, String strMsg) throws Exception
    {
        ((CLogger)this.dictLoggers.get(strLogType)).logError(strMsg);
    }
    
    public Properties getDictLogProperties() {
        return dictLogProperties;
    }

    public String getStrFileName() {
        return strFileName;
    }
}
