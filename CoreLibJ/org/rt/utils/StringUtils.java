/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Krishna
 */
public class StringUtils 
{
    public static List<String> tokenizeString
	(
		String strSrc, 
		String strDelimiter, 
		boolean bReturnDelimiter
	) throws Exception
	{
		java.util.StringTokenizer objTokenizer;
		List<String> lsTokens;
		
		try
		{
			objTokenizer = new java.util.StringTokenizer(strSrc, strDelimiter, bReturnDelimiter);
			lsTokens = new ArrayList<String>();
			
			while(objTokenizer.hasMoreTokens())
			{
				lsTokens.add(objTokenizer.nextToken());
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		
		return lsTokens;
	}
	
	public static String diffPath
	(
		String strParentPath, 
		String strChildPath, 
		String strSeperator
	) throws IllegalArgumentException
        {
                String strReturnVal = new String();
                
                if (isEmptyString(strParentPath))
                    throw new IllegalArgumentException("strParentPath can not be null");
                
                if (isEmptyString(strChildPath))
                    throw new IllegalArgumentException("strChildPath can not be null");
                
                if (isEmptyString(strSeperator))
                    throw new IllegalArgumentException("strSeperator can not be null");
                
                if ((strSeperator.equals("/") == false) && (strSeperator.equals("\\") == false))
                    throw new IllegalArgumentException("strSeperator must be either \\ or /");
                
                if (strSeperator.equals("/"))
                {
                    strParentPath = strParentPath.replace("\\", strSeperator);
                    strChildPath = strChildPath.replace("\\", strSeperator);
                }
                else
                {
                    strParentPath = strParentPath.replace("/", strSeperator);
                    strChildPath = strChildPath.replace("/", strSeperator);
                }
                
                int iIdx = strParentPath.indexOf(strChildPath);
                
                if (iIdx != -1)
                {
                    if (iIdx == 0)
                    {
                        strReturnVal = strParentPath.substring(iIdx + strChildPath.length());
                        
                        if (strReturnVal.length() <= 0)
                            strReturnVal = "/";
                    }
                    else
                    {
                        strReturnVal = strParentPath.substring(1, iIdx);
                    }
                }
                
                return strReturnVal;
        }
	
	public static boolean isEmptyString(String strString)
	{
		boolean bReturnVal = false;
		
		if ((strString == null) || (strString.trim().isEmpty()))
		{
			bReturnVal = true;
		}
			
		return bReturnVal;
	}
	
	public static boolean isAlphaNumericValue(String strValue)
	{
		boolean bIsAlphaNumericVal = isNumericValue(strValue);

		bIsAlphaNumericVal = bIsAlphaNumericVal ? true : isAlphabeticValue(strValue);

		return bIsAlphaNumericVal;
	}

	public static boolean isAlphabeticValue(String strValue)
	{
		boolean bIsAlphabeticVal = true;

		byte [] strToBytes = strValue.toUpperCase().getBytes();

		for (int iCounter = 0; iCounter < strToBytes.length; iCounter++)
		{
			if (strToBytes[iCounter] < 65 || strToBytes[iCounter] > 90)
			{
				bIsAlphabeticVal = false;
				break;
			}
		}

		return bIsAlphabeticVal;
	}

	public static boolean isNumericValue(String strValue)
	{
		boolean bIsNumericValue = true;

		try
		{
			Double.valueOf(strValue);
		}
		catch(NumberFormatException e)
		{
			bIsNumericValue = false;
		}

		return bIsNumericValue;
	}
	
	public static int countOccurenceOfStr1InStr2(String str1, String str2)
	{
		int iOccurencesCount = 0;
		for (int iCounter = 0; iCounter < str2.length(); iCounter++)
		{
			int iIdx = str2.substring(iCounter, str2.length()).indexOf(str1);
			if (iIdx != -1)
			{
				iOccurencesCount ++;
				iCounter += iIdx + str1.length();
				continue;
			}
			break;
		}

		return iOccurencesCount;
	}
}
