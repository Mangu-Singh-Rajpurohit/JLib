/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.extended;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

/**
 *
 * @author Krishna
 */

public class ExtendedArrayList<E> extends java.util.ArrayList
{
	//  iMaxSize specifies maximum number of 
	//  elements to be added to arraylist.
	//  Now, the default and only behaviour is
	//  to remove the elements from the front
	//  of the arraylist, whe number of elements
	//  in arraylist exceeds the iMaxSize, instead
	//  of raising exception
	private int iMaxSize;
	
	public ExtendedArrayList()
	{
		super();
		this.setiMaxSize(-1);
	}
	
	public ExtendedArrayList(byte [] array)
	{
		super(array.length);

		for (int iCounter = 0; iCounter < array.length; iCounter++)
		{
			this.add(array[iCounter]);
		}
	}
        
	public ExtendedArrayList(int iMaxSize) throws IllegalArgumentException
	{
		super();
		this.setiMaxSize(iMaxSize);
	}

	public int getiMaxSize() 
	{
		return iMaxSize;
	}

	public void setiMaxSize(int iMaxSize)
	{
		this.iMaxSize = iMaxSize;
	}
        
	public ExtendedArrayList<E> union(ArrayList<E> objLs) throws IllegalArgumentException
	{
		if (objLs == null)
		{
			throw new java.lang.IllegalArgumentException("Invalid argument passed for objLs");
		}
	
		ExtendedArrayList<E> lsReturnVal = new ExtendedArrayList<>();
		
		for (int iCounter = 0; iCounter < this.size(); iCounter++)
		{
			if (lsReturnVal.contains(this.get(iCounter)) == false)
			{
				lsReturnVal.add(this.get(iCounter));
			}
		}
		
		for (int iCounter = 0; iCounter < objLs.size(); iCounter++)
		{
			if (lsReturnVal.contains(objLs.get(iCounter)) == false)
			{
				lsReturnVal.add(objLs.get(iCounter));
			}
		}
		
		return lsReturnVal;
	}
	
	public ExtendedArrayList<E> intersect(ArrayList<E> objLs) throws IllegalArgumentException
	{
		if (objLs == null)
		{
			throw new java.lang.IllegalArgumentException("Invalid argument passed for objLs");
		}

		ExtendedArrayList<E> lsReturnVal = new ExtendedArrayList<>();

		ArrayList<E> lsTempRef = this.size() < objLs.size() ? this : objLs;
		ArrayList<E> lsTempRef1 = this.size() < objLs.size() ? objLs : this;

		for (int iCounter = 0; iCounter < lsTempRef.size(); iCounter++)
		{
			if (
					(lsReturnVal.contains(lsTempRef.get(iCounter)) == false) &&
					(lsTempRef1.contains(lsTempRef.get(iCounter)))
				)
			{
				lsReturnVal.add(lsTempRef.get(iCounter));
			}
		}

		return lsReturnVal;
	}

	public ExtendedArrayList<E> minus(ArrayList<E> objLs) throws IllegalArgumentException
	{
		if (objLs == null)
		{
			throw new java.lang.IllegalArgumentException("Invalid argument passed for objLs");
		}

		ExtendedArrayList<E> lsReturnVal = new ExtendedArrayList<>();

		ArrayList<E> lsTempRef = this.size() > objLs.size() ? this : objLs;
		ArrayList<E> lsTempRef1 = this.size() > objLs.size() ? objLs : this;

		for (int iCounter = 0; iCounter < lsTempRef.size(); iCounter++)
		{
			if (
					(lsReturnVal.contains(lsTempRef.get(iCounter)) == false) &&
					(lsTempRef1.contains(lsTempRef.get(iCounter)) == false)
				)
			{
				lsReturnVal.add(lsTempRef.get(iCounter));
			}
		}

		return lsReturnVal;
	}

	public static ExtendedArrayList<String> loadFromFile(String strFileName)
						throws java.io.FileNotFoundException, java.io.IOException
	{
		ExtendedArrayList<String> lsReturnVal = loadFromFile(new File(strFileName));//, "\n");

		return lsReturnVal;
	}

	public static ExtendedArrayList<String> loadFromFile(File objFile) 
					throws java.io.FileNotFoundException, java.io.IOException
	{
		ExtendedArrayList<String> lsReturnVal = new ExtendedArrayList<String>();
		Scanner sc = new Scanner(objFile);

		while(sc.hasNext())
		{
			lsReturnVal.add(sc.nextLine());
		}

		return lsReturnVal;
	}

	public void writeToFile(String strFileName) throws IOException
	{
		writeToFile(new java.io.File(strFileName), "\n");
	}

	public void writeToFile(String strFileName, String strSeperator) throws IOException
	{
		writeToFile(new java.io.File(strFileName), strSeperator);
	}

	public void writeToFile(File objFile) throws IOException
	{
		writeToFile(objFile, "\n");
	}

	public void writeToFile(File objFile, String strSeperator) throws IOException
	{
		try (java.io.PrintWriter pw = new java.io.PrintWriter(objFile)) 
		{
			for (int iCounter = 0; iCounter < this.size(); iCounter++)
			{
				pw.print(this.get(iCounter) + strSeperator);
			}
			
			pw.flush();
		}
	}

	public static ExtendedArrayList<Integer> generateRange(int iEndVal)
	{
		ExtendedArrayList<Integer> lsReturnValues = generateRange(0, iEndVal, 1);

		return lsReturnValues;
	}

	public static ExtendedArrayList<Integer> generateRange(int iStartVal, int iStopVal)
	{
		ExtendedArrayList<Integer> lsReturnValues = generateRange(iStartVal, iStopVal, 1);

		return lsReturnValues;
	}

	public static ExtendedArrayList<Integer> generateRange(int iStartVal, int iStopVal, int iStepVal)
	{
		ExtendedArrayList<Integer> lsReturnValues = new ExtendedArrayList<>();

		for (int iCounter = iStartVal; iCounter < iStopVal; iCounter += iStepVal)
		{
			lsReturnValues.add(iCounter);
		}

		return lsReturnValues;
	}

	public static ExtendedArrayList<String> instantiateFromString(String strValue, String strSeperator, boolean bReturnSeperator)
	{
		ExtendedArrayList<String> lsReturnVal = new ExtendedArrayList<>();

		java.util.StringTokenizer objTokenizer = new java.util.StringTokenizer(strValue, strSeperator, bReturnSeperator);

		while (objTokenizer.hasMoreTokens())
		{
			lsReturnVal.add(objTokenizer.nextToken());
		}

		return lsReturnVal;
	}

	public static ExtendedArrayList<String> instantiateFromString(String strValue, String strSeperator)
	{    
		return instantiateFromString(strValue, strSeperator, false);
	}

	public void print()
	{
		for (int iCounter = 0; iCounter < this.size(); iCounter++)
		{
			System.out.println(this.get(iCounter));
		}
	}

	public ExtendedArrayList<Object> slice(int iStart, int iEnd) throws Exception
	{
		if (iStart < 0)
			throw new IndexOutOfBoundsException("iStart out of range " + iStart);

		if (iEnd > this.size())
			throw new IndexOutOfBoundsException("iEnd out of range " + iEnd);

		ExtendedArrayList<Object> lsReturnValues = new ExtendedArrayList<>();

		for (int iCounter = iStart; iCounter < iEnd; iCounter++)
		{
			lsReturnValues.add(this.get(iCounter));
		}
		return lsReturnValues;
	}

	public String asString()
	{
		return asString(0, this.size());
	}

	public String asString(int iStartRange, int iEndRange)
	{
		if (iStartRange < 0)
			throw new IndexOutOfBoundsException("iStart out of range " + iStartRange);

		if (iEndRange > this.size())
			throw new IndexOutOfBoundsException("iEnd out of range " + iEndRange);

		StringBuilder strReturnValue = new StringBuilder();

		for (int iCounter = 0; iCounter < this.size(); iCounter++)
		{
			strReturnValue.append(this.get(iCounter));
		}

		return strReturnValue.toString();
	}

	public static ArrayList<Integer> getAllIndicesOfSubStringInLsString(ArrayList<String> lsString, String strSubString, boolean bExactMatch)
	{
		ArrayList<Integer> lsReturnValues = new ArrayList<>();

		for (int iCounter = 0; iCounter < lsString.size(); iCounter++)
		{
			if (bExactMatch)
			{
				if (lsString.get(iCounter) == null ? strSubString == null : lsString.get(iCounter).equals(strSubString))
					lsReturnValues.add((iCounter));
			}
			else
			{
				if (lsString.get(iCounter).contains(strSubString))
					lsReturnValues.add((iCounter));
			}
		}
		return lsReturnValues;
	}

	public static ExtendedArrayList<Object> getListFromEnum(Enumeration objEnum)
	{
		ExtendedArrayList<Object> lsReturnValues = new ExtendedArrayList<>();

		while (objEnum.hasMoreElements())
		{
			lsReturnValues.add(objEnum.nextElement());
		}

		return lsReturnValues;
	}

	public synchronized Object get(int iIndex, boolean bUseSynchronized) throws IllegalArgumentException
	{
		if (bUseSynchronized == false)
		{
			throw new IllegalArgumentException("bUseSynchronized must always be true");
		}

		if (iIndex < 0)
		{
			iIndex = this.size() + iIndex;
		}

		return super.get(iIndex);
	}

	public synchronized boolean add(Object o, boolean bUseSynchronized) throws IllegalArgumentException
	{
		if (bUseSynchronized == false)
		{
			throw new IllegalArgumentException("bUseSynchronized must always be true");
		}

		boolean bResult = add(o);

		return bResult;
	}

	//  use only add() operation in order
	//  to ensure size < maxsize.
	//  TODO : implement remove() method
	//  which ensure size < maxsize
	@Override
	public boolean add(Object o)
	{
		if (this.iMaxSize > 0)
		{
			if (this.iMaxSize <= this.size())
			{
				super.remove(0);
			}
		}
		boolean bReturnValue = super.add(o);

		return bReturnValue;
	}
	/*
	TODO :- The below given functions are written for loading contents from file.
			But, they are not working properly. The problem is that '\n' character
			is not excluded from the read token, when that token is read from file.
	*/
	/*
	public static ExtendedArrayList<String> loadFromFile(String strFileName, String strDelimiter)
											throws java.io.FileNotFoundException, java.io.IOException
	{
		ExtendedArrayList<String> lsReturnVal = loadFromFile(new File(strFileName), strDelimiter);

		return lsReturnVal;
	}
	*/

	/*
	public static ExtendedArrayList<String> loadFromFile(File objFile) 
					throws java.io.FileNotFoundException, java.io.IOException
	{
		ExtendedArrayList<String> lsReturnVal = loadFromFile(objFile, "\n");

		return lsReturnVal;
	}
	*/

	/*
	public static ExtendedArrayList<String> loadFromFile(File objFile, String strDelimiter)
							throws java.io.FileNotFoundException, java.io.IOException
	{
		ExtendedArrayList<String> lsReturnVal = new ExtendedArrayList<String>();

		java.io.FileInputStream in = new java.io.FileInputStream(objFile);

		int iBufferSize = 1000;
		byte [] buffer = new byte[iBufferSize];

		StringBuffer strBuf = new StringBuffer();
		int iNumberOfBytesRead = -1;
		do
		{
			iNumberOfBytesRead = in.read(buffer);

			if (iNumberOfBytesRead == -1)
				break;

			strBuf.append(new String(buffer));
		}
		while (iNumberOfBytesRead != iBufferSize);

		java.util.StringTokenizer objTokenizer = new java.util.StringTokenizer(strBuf.toString(), strDelimiter);

		while (objTokenizer.hasMoreTokens())
		{
			lsReturnVal.add(objTokenizer.nextToken());
		}
		/*
		Scanner sc = new Scanner(objFile);
		sc.useDelimiter(strDelimiter);

		while(sc.hasNext())
		{
			lsReturnVal.add(sc.next());
		}
		*/
	/*
		return lsReturnVal;
	}*/
}