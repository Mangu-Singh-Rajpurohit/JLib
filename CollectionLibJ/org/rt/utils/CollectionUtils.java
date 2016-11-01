/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils;

public class CollectionUtils 
{
	public static <E> boolean bAreArraysEqual(E[] array1, E[] array2)
	{
		boolean bReturnVal = true;

		if (array1.length != array2.length)
			bReturnVal = false;

		for (int iCounter = 0; bReturnVal == true && iCounter < array1.length; iCounter++)
		{
			if (array1[iCounter].equals(array2[iCounter]) == false)
			{
				bReturnVal = false;
				break;
			}
		}

		return bReturnVal;
	}
	
	public static byte[] sliceArray(byte [] inputArray, int iStartIdx, int iLength) throws Exception
	{
		if (iStartIdx < 0)
		{
			throw new IllegalArgumentException("iStartIdx can not be negative number");
		}

		if (iLength <= 0)
		{
			throw new IllegalArgumentException("iLength must be greater than zero");
		}

		if ((iStartIdx + iLength) > inputArray.length)
		{
			throw new ArrayIndexOutOfBoundsException(iStartIdx + iLength +  " > " + inputArray.length);
		}

		byte [] returnArray = new byte[iLength];
		for (int iCounter = 0; iCounter < iLength; iCounter++)
		{
			returnArray[iCounter] = inputArray[iStartIdx + iCounter];
		}

		return returnArray;
	}

}
