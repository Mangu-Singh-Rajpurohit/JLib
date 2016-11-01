/*
 * Author :- RT RP
 * Purpose :- Contains all the global utilities, which are related to other libraries, 
 *            but certain "CoreLibJ" classes depends upon these utilities.
 *
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

package org.rt.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GlobalUtils 
{
	public static Object getFormattedTimeTakenFromMilliSeconds(long lTimeTakenInMilliSeconds, boolean bReturnString)
						throws java.lang.IllegalArgumentException
	{
		if (lTimeTakenInMilliSeconds < 0)
		{
			throw new java.lang.IllegalArgumentException("lTimeTakenInMilliseconds is less than zero " + lTimeTakenInMilliSeconds);
		}

		Object objReturnVal = null;

		//  contains HH:MM:SS:ms in terms of ms(milliseconds)
		int iStandardTimeInUnits [] = {3600000, 60000, 1000, 1};
		//  contains in term of HH:MM:SS:ms
		int iTimeTaken [] = {0, 0, 0, 0};
		long lCurrentVal = lTimeTakenInMilliSeconds;
		for (int iCounter = 0; iCounter < iStandardTimeInUnits.length; iCounter++)
		{
			iTimeTaken[iCounter] = (int)(lCurrentVal / iStandardTimeInUnits[iCounter]);
			lCurrentVal = lCurrentVal % iStandardTimeInUnits[iCounter];
		}

		if (bReturnString)
		{
			objReturnVal = iTimeTaken[0] + " : " + iTimeTaken[1] + " : " + iTimeTaken[2] + " : " + iTimeTaken[3];
		}
		else
		{
			List<Integer> lsTemp = new ArrayList<>();

			for (int iCounter = 0; iCounter < iTimeTaken.length; iCounter++)
			{
				lsTemp.add(iTimeTaken[iCounter]);
			}

			objReturnVal = lsTemp;
		}

		return objReturnVal;
	}
	
	public static String join(String strPath1, String strPath2) throws Exception
	{	
		if (strPath1 == null || strPath1.length() == 0)
			throw new Exception("path1 cannot be null or empty string");
			
		if (strPath2 == null)
			throw new Exception("path2 cannot be null");
		
		if (strPath1.equals("."))
		{
			strPath1 = getCurrentDirectory();
		}
		
		strPath1 = strPath1.replace("\\", "/");
		strPath2 = strPath2.replace("\\", "/");
		int iIdxOfSlashInFirstPath = strPath1.lastIndexOf("/");
		int iIdxOfSlashInSecondPath = strPath2.indexOf("/");
		
		if (iIdxOfSlashInFirstPath == (strPath1.length() - 1))
		{
			strPath1 = strPath1.substring(0, iIdxOfSlashInFirstPath);
		}
		
		if (iIdxOfSlashInSecondPath == 0)
		{
			strPath2 = strPath2.substring(1);
		}
		
		String strAbsPath = strPath1 + "/" + strPath2;
		
		return strAbsPath;
	}
	
	public static String getCurrentDirectory() throws Exception
	{
		String strAbsPathOfCurrentDir = null;
		try
		{
			File fl = new File("");
			strAbsPathOfCurrentDir = fl.getAbsolutePath();
		}
		catch(Exception e)
		{
			throw e;
		}
		return strAbsPathOfCurrentDir;
	}

	public static String getLineFromConsole(String strMsg) throws Exception
	{
		Scanner objScanner	= new Scanner(System.in);
		System.out.println(strMsg + "-----> ");
		return objScanner.nextLine();
	}
	
	public static String getNonEmptyInputFromConsole(String strMsg, int iMaxTimes) throws Exception
	{
		String strInput		= "";
		for (int iCounter = 0; iCounter < iMaxTimes || iMaxTimes == -1; iCounter++)
		{
			strInput			= getLineFromConsole(strMsg);
			if (strInput.trim().length() > 0)
				break;
		}
		return strInput;
	}
	
	public static String getNonEmptyInputFromConsole(String strMsg) throws Exception
	{
		return getNonEmptyInputFromConsole(strMsg, -1);
	}
}
