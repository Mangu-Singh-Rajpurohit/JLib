/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils;

import org.rt.utils.exceptions.ObjectRebuildFailedException;

public class CLogEvent implements java.io.Serializable, Loggable
{
	public static final byte ERROR		= 0;
	public static final byte WARNING	= 1;
	public static final byte INFO		= 2;
	public static final byte DEBUG		= 3;
	
	private static final long serialVersionUID			= 3349992342231L;	
	private static final byte [] byteArrayValidTypes	= {ERROR, WARNING, INFO, DEBUG};
	private static final String [] strArrayValidTypes	= {"ERROR", "WARNING", "INFO", "DEBUG"};
	
	private long lEvtId;
	private byte byteEvtType;
	private String strMessage;
	private java.sql.Timestamp objEvtTime;
	
	private void readObject(java.io.ObjectInputStream oIn) 
				throws java.io.IOException, ClassNotFoundException
	{
		oIn.defaultReadObject();
	}
	
	private void writeObject(java.io.ObjectOutputStream oOut) 
				throws java.io.IOException
	{
		oOut.defaultWriteObject();
	}
	
	public CLogEvent()
	{
		this(1, INFO, "", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	public CLogEvent(final long lEvtId)
	{
		this(lEvtId, INFO, "", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	public CLogEvent(final long lEvtId, final byte byteEvtType)
	{
		this(lEvtId, byteEvtType, "", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	public CLogEvent(final long lEvtId, final byte byteEvtType, final String strMessage)
	{
		this(lEvtId, byteEvtType, strMessage, new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	public CLogEvent(final long lEvtId, final byte byteEvtType, final String strMessage, final java.sql.Timestamp objEvtTime)
															throws IllegalArgumentException
	{	
		this.setEvtId(lEvtId);
		this.setEvtType(byteEvtType);
		this.setMessage(strMessage);
		this.setEvtTime(objEvtTime);
	}
	
	public  static String [] getArrayValidTypes()
	{
		return strArrayValidTypes;
	}
	
	public long getEvtId()
	{
		return this.lEvtId;
	}
	
	public void setEvtId(long lEvtId) throws IllegalArgumentException
	{
		if (lEvtId < 1)
		{
			throw new IllegalArgumentException("EventId must be positive number");
		}
		
		this.lEvtId = lEvtId;
	}
	
	public byte getEvtType()
	{
		return this.byteEvtType;
	}
	
	public void setEvtType(byte byteEvtType) throws IllegalArgumentException
	{
		boolean bPresent = false;
		for (int iCounter = 0; iCounter < CLogEvent.byteArrayValidTypes.length; iCounter ++)
		{
			if (CLogEvent.byteArrayValidTypes[iCounter] == byteEvtType)
			{
				bPresent = true;
				break;
			}
		}
		
		if (!bPresent)
		{
			throw new IllegalArgumentException("Valid event type must be passed");
		}
		
		this.byteEvtType = byteEvtType;
	}
	
	public String getMessage()
	{
	
	return this.strMessage;
	}
	
	public void setMessage(String strMessage)
	{
		this.strMessage = strMessage;
	}
	
	public java.sql.Timestamp getEvtTime()
	{
		return this.objEvtTime;
	}
	
	public void setEvtTime(java.sql.Timestamp objEvtTime) throws IllegalArgumentException
	{
		if (objEvtTime == null)
		{
			throw new IllegalArgumentException("Event time can not be null");
		}
		
		this.objEvtTime = objEvtTime;
	}
	
	@Override
	public String toString()
	{
		StringBuilder objBuffer = new StringBuilder();
		
		objBuffer.append(this.lEvtId);
		objBuffer.append("\t\t#").append(CLogEvent.strArrayValidTypes[this.byteEvtType]);
		objBuffer.append("\t\t#").append(this.objEvtTime);
		objBuffer.append("\t\t\t#").append(this.strMessage);
		
		return objBuffer.toString();
	}
	
	public static Loggable makeObject(String strSource)
						throws java.lang.IllegalArgumentException, ObjectRebuildFailedException
	{
		if (strSource == null || strSource.isEmpty())
		{
			throw new java.lang.IllegalArgumentException("strSource is empty");
		}
		
		CLogEvent objEvent = new CLogEvent();
		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(strSource, "#");
		
		try
		{
			objEvent.setEvtId(Long.parseLong(tokenizer.nextToken()));
			String str = tokenizer.nextToken();
			byte byteIdx = -1;
			
			for (byte iCounter = 0; iCounter < strArrayValidTypes.length ; iCounter ++)
			{
				if (strArrayValidTypes[iCounter] == null ? str == null : strArrayValidTypes[iCounter].equals(str))
				{
					byteIdx = iCounter;
					break;
				}
			}
			
			if (byteIdx == -1)
			{
				byteIdx = 0;
			}
			
			objEvent.setEvtType(byteIdx);
			objEvent.setEvtTime(java.sql.Timestamp.valueOf(tokenizer.nextToken()));
			objEvent.setMessage(tokenizer.nextToken());
		}
		catch(Exception e)
		{
			throw new ObjectRebuildFailedException("Can not build object successfully.", "CLogEvent");
		}

		return objEvent;
	}
	
	public static Loggable makeObject(java.util.Scanner sc)
								throws java.lang.IllegalArgumentException,
								java.io.IOException, ObjectRebuildFailedException
	{
		if (sc == null)
		{
			throw new java.lang.IllegalArgumentException("Scanner object is null");
		}
		
		CLogEvent objEvent = new CLogEvent();
		while (sc.hasNextLine())
		{
			String strLine = sc.nextLine();
		
			if (strLine.isEmpty())
				continue;
			
			objEvent = (CLogEvent)makeObject(strLine);
			break;
		}
			
		return objEvent;
	}
}