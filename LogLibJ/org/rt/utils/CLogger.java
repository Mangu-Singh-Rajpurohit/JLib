/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils;

import java.io.File;
import java.util.ArrayList;

public class CLogger implements java.io.Serializable
{
    public static final int DEFAULT_BUFSIZE = 100;
    public static final byte FILE_MODE		= 1;		//	default mode
    public static final byte DB_MODE		= 2;		//	specifies logs have to be written to db.

    private static final byte [] arrayByteValidModes = {FILE_MODE, DB_MODE};
    private byte byteLoggerMode;
    private byte byteLoggingLevel;
    private int iBufLength;
    private int iNoOfMessageLogged;

    private java.io.PrintWriter objPw;
    private java.io.File objFile;
    private java.util.ArrayList<CLogEvent> lsLogObjects;
    private java.sql.Connection objCon;

    public CLogger()
    {
    }
    
    public CLogger(String strFileName) 
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(strFileName, null, null, DEFAULT_BUFSIZE, FILE_MODE);
    }

    public CLogger(String strFileName, int iBufLength)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(strFileName, null, null, iBufLength, FILE_MODE);
    }

    public CLogger(String strFileName, int iBufLength, byte byteMode)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(strFileName, null, null, iBufLength, byteMode);
    }

    public CLogger(java.io.PrintWriter pw)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(null, pw, null, DEFAULT_BUFSIZE, FILE_MODE);
    }

    public CLogger(java.io.PrintWriter pw, int iBufLength)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(null, pw, null, iBufLength, FILE_MODE);
    }

    public CLogger(java.io.PrintWriter pw, int iBufLength, byte byteMode)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(null, pw, null, iBufLength, byteMode);
    }

    public CLogger(java.io.File objFile)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(null, null, objFile, DEFAULT_BUFSIZE, FILE_MODE);
    }

    public CLogger(java.io.File objFile, int iBufLength)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(null, null, objFile, iBufLength, FILE_MODE);
    }

    public CLogger(java.io.File objFile, int iBufLength, byte byteMode)
            throws java.io.FileNotFoundException, IllegalArgumentException
    {
		init(null, null, objFile, iBufLength, byteMode);
    }

    public CLogger(java.sql.Connection objCon, int iBufLength)
    {
		//	TODO :- remove modes from above constructors
    }
	
    public java.io.PrintWriter getOut()
    {
		return this.objPw;
    }

    public byte getMode()
    {
		return this.byteLoggerMode;
    }

    public void setOut(java.io.PrintWriter objPw) throws IllegalArgumentException
    {
		if (objPw == null)
		{
				throw new IllegalArgumentException("PrintWriter object can not be null");
		}

		this.objPw = objPw;
    }

    public int getBufLength()
    {
		return this.iBufLength;
    }

    public void setBufLength(int iBufLength) throws IllegalArgumentException
    {
		if (iBufLength < 0)
				throw new IllegalArgumentException("Buffer Size must be non-negative");

		this.iBufLength = iBufLength;
    }

    public int getNoOfMessagesLogged()
    {
		return this.iNoOfMessageLogged;
    }

    public byte getByteLoggingLevel() 
	{
        return byteLoggingLevel;
    }

    public void setByteLoggingLevel(byte byteLoggingLevel) 
	{
        this.byteLoggingLevel = byteLoggingLevel;
    }

    public byte getByteLoggerMode() 
	{
        return byteLoggerMode;
    }

    public void setByteLoggerMode(byte byteLoggerMode) 
	{
        this.byteLoggerMode = byteLoggerMode;
    }
    
    public void reinitialize(String strFileName) throws Exception
    {
        this.init(strFileName, null, null, this.getBufLength(), this.byteLoggerMode);
    }
    
    public void reinitialize(String strFileName, byte byteLoggingLevel) throws Exception
    {
        this.init(strFileName, null, null, this.getBufLength(), this.byteLoggerMode, byteLoggingLevel);
    }
    
    public void reinitialize(String strFileName, byte byteLoggingLevel, byte byteLoggingMode) throws Exception
    {
        this.init(strFileName, null, null, this.getBufLength(), byteLoggingMode, byteLoggingLevel);
    }
    
    public void reinitialize(String strFileName, byte byteLoggingLevel, byte byteLoggingMode, int iBufSize) throws Exception
    {
        this.init(strFileName, null, null, iBufSize, byteLoggingMode, byteLoggingLevel);
    }
    
    private void init
    (
		String strFileName, 
		java.io.PrintWriter objPw, 
		java.io.File objFile, 
		int iBufLength, 
		byte byteMode,
		byte byteLoggingLevel
    ) throws java.io.FileNotFoundException, IllegalArgumentException
    {
		//  flushing the old logs, present
		//  in buffer. It's necessary because
		//  this method is invoked from
		//  reinitialize() method.
		if (this.lsLogObjects != null)
		{
			if (this.objPw != null)
			{
				this.flush();
			}
		}

		if ((StringUtils.isEmptyString(strFileName)) && (objPw == null) && (objFile == null))
		{
			throw new IllegalArgumentException("Either of filename, printwriter or fileobject have to be passed");
		}

		boolean bPresent = false;

		for (int iCounter = 0; iCounter < CLogger.arrayByteValidModes.length; iCounter ++)
		{
			if (CLogger.arrayByteValidModes[iCounter] == byteMode)
			{
				bPresent = true;
				break;
			}
		}

		if (!bPresent)
		{
			throw new IllegalArgumentException("Invalid logging mode passed");
		}

		if (objPw != null)
		{
			this.objPw = objPw;
		}

		else if(objFile != null)
		{
			this.objFile = objFile;
			this.objPw = new java.io.PrintWriter(new java.io.FileOutputStream(this.objFile, true), true);
		}

		else if(StringUtils.isEmptyString(strFileName) == false)
		{
			this.objFile = new java.io.File(strFileName);
			this.objPw = new java.io.PrintWriter(new java.io.FileOutputStream(this.objFile, true), true);
		}

		this.byteLoggerMode = byteMode;
		this.setBufLength(iBufLength);
		this.lsLogObjects = new java.util.ArrayList<>();
		this.byteLoggingLevel = byteLoggingLevel;
    }
    
    private void init
    (
            String strFileName, 
            java.io.PrintWriter objPw, 
            java.io.File objFile, 
            int iBufLength, 
            byte byteMode
    ) throws java.io.FileNotFoundException, IllegalArgumentException
    {
        this.init(strFileName, objPw, objFile, iBufLength, byteMode, CLogEvent.INFO);
    }

    public void logInfo(String strMessage) throws Exception
    {
		this.log(strMessage, CLogEvent.INFO);
    }

    public void logWarn(String strMessage) throws Exception
    {
		this.log(strMessage, CLogEvent.WARNING);
    }

    public void logError(String strMessage) throws Exception
    {
		this.log(strMessage, CLogEvent.ERROR);
    }

    public void logDebug(String strMessage) throws Exception
    {
        this.log(strMessage, CLogEvent.DEBUG);
    }

    public void log(String strMessage, byte typeOfMsg) throws Exception
    {
		if (typeOfMsg > this.byteLoggingLevel)
		{
			return;
		}

		synchronized(this)
		{
			CLogEvent objLogEvt = new CLogEvent(this.iNoOfMessageLogged + 1, typeOfMsg, strMessage);
			this.lsLogObjects.add(objLogEvt);
			this.iNoOfMessageLogged ++;
		}
		if (this.lsLogObjects.size() > this.iBufLength)
		{
				this.flush();
		}
    }

    public void flush(ArrayList<Object> lsParams)
    {
            flush();
    }
    
    synchronized public void flush()
    {
		for (int iCounter = 0; iCounter < this.lsLogObjects.size(); iCounter ++)
		{
			this.objPw.println(this.lsLogObjects.get(iCounter));
		}

		this.objPw.flush();
		this.lsLogObjects.clear();
    }

    //  Don't call this method to set the log file name
    public void changeLogFile(String strFileName) throws Exception
    {
        //  by default, flush the buffer's contents
        //  in old filename.
        changeLogFile(strFileName, true);
    }
    
    public void changeLogFile(String strFileName, boolean bFlushBuffer) throws Exception
    {
        changeLogFile(new java.io.File(strFileName), bFlushBuffer);
    }
    
    public void changeLogFile(File objFile) throws Exception
    {
        changeLogFile(objFile, true);
    }
    
    public void changeLogFile(File objFile, boolean bFlushBuffer) throws Exception
    {
        if (bFlushBuffer)
        {
            this.flush();
        }
        this.objPw.close();
        this.init(null, null, objFile, this.iBufLength, this.byteLoggerMode);
    }
    
    public CLogEvent getEventById(long iIdx)
    {
		CLogEvent objReturnObject = null;

		for (int iCounter = 0; iCounter < this.lsLogObjects.size(); iCounter ++)
		{
				if (this.lsLogObjects.get(iCounter).getEvtId() == iIdx)
				{
						objReturnObject = this.lsLogObjects.get(iCounter);
						break;
				}
		}

		return objReturnObject;
    }

    //	TODO :- test this method by making synchronized, if it doesn't
    //			ensure integrity of list in concurrent access.
    //	TODO :- Test this method
    private boolean isValidSearchRange(int iIdx)
    {
		boolean bReturnVal = false;

		CLogEvent objFirstEventInBuf = this.lsLogObjects.get(0);
		CLogEvent objLastEventInBuf = this.lsLogObjects.get(this.lsLogObjects.size() - 1);

		//	This condition is not necessary to hold correct. Say if you are assigning
		//	nos. 4, 5, 6 to three clients and if client with no. 6 is requesting some
		//	data, then server will satisfy its request and store this request as logevent
		//	object in list. Now if search is made for iIdx 4 or 5, then this method will
		//	return true, as 4 and 5 lies between 1 to 6.
		if (
				(objFirstEventInBuf.getEvtId() <= iIdx) &&
				(objLastEventInBuf.getEvtId() >= iIdx)
				)
		{
				bReturnVal = true;
		}

		return bReturnVal;
    }

    //	TODO :- Test it
    /*
    private boolean isValidSearchRange(CLogEvent objEvent)
    {
            boolean bReturnVal = this.isValidSearchRange(objEvent.getEvtId());
    }
    */

    public CLogEvent search(CLogEvent objEvent)
    {
		CLogEvent objReturnObject = null;
		try
		{
			if (objEvent != null)
			{
				long iEvtId =  objEvent.getEvtId();

				if ((this.lsLogObjects.size() > 0))
				{
						objReturnObject = getEventById(iEvtId);

						if (objReturnObject == null)
						{
								//	search in file
						}
				}
				else
				{
						//	search in file
				}
			}
			else
			{
				throw new NullPointerException("objEvent can not null");
			}
		}
		catch(Exception e)
		{
		}
		return objReturnObject;
    }

    public void finalize()
    {
            /*
            Tried to invoke flush() method from finalize() method, but when the object is
            garbage collected, flush() is invoked, but in accessing this.lsLogObjects, it's
            not behaving much consistently, i.e., in the for loop in flush, it some times
            doesn't traverse completely N no. of times. Some times, if it's traversing loop
            correct number of times, then it does not actually write into file, i.e. execution
            of this.obPw.println() instruction don't writes into file. Thus, such critical
            operations must not be invoked from finalize method. It' very uncertain and unreliable.
            */
            //	this.flush();
    }
}
