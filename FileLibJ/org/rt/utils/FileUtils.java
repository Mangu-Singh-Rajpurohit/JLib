/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

package org.rt.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import org.rt.utils.exceptions.DirectoryFoundInsteadOfFileException;
import org.rt.utils.exceptions.StreamReadError;

public class FileUtils 
{
	public static boolean doesFileExists(String strFileName) throws DirectoryFoundInsteadOfFileException
	{
		boolean bExists = doesFileExists(new java.io.File(strFileName));

		return bExists;
	}

	public static boolean doesAllFileExists(List<String> lsStrFileName) throws DirectoryFoundInsteadOfFileException
	{
		boolean bExists = true;

		for (int iCounter = 0; iCounter < lsStrFileName.size(); iCounter++)
		{
			if (doesFileExists(lsStrFileName.get(iCounter)) == false)
			{
				bExists = false;
				break;
			}
		}

		return bExists;
	}

	public static boolean doesFileExists(File objFile) throws DirectoryFoundInsteadOfFileException
	{
		boolean bExists = false;
		if (objFile.exists())
		{
			if (objFile.isDirectory())
			{
				throw new DirectoryFoundInsteadOfFileException(objFile + " is a directory");
			}
			else if(objFile.isFile())
			{
				bExists = true;
			}
		}

		return bExists;
	}
	
	public static void copyFile(java.io.File objSrcFile, java.io.File objDestFile) throws Exception
	{
		FileOutputStream fout;
		try (FileInputStream fin = new java.io.FileInputStream(objSrcFile)) 
		{
			fout = new java.io.FileOutputStream(objDestFile);
			int iNumberOfByesRead = -1;
			int iBufferSize = 100;
			byte [] buffer = new byte[iBufferSize];
			do
			{
				iNumberOfByesRead = fin.read(buffer);
				fout.write(buffer);
			}while(iNumberOfByesRead == iBufferSize);
		}
		fout.close();
	}

	public static void copyFile(String strSrcFileName, String strDestFileName) throws Exception
	{
	   copyFile(new java.io.File(strSrcFileName), new java.io.File(strDestFileName));
	}

	public static byte[] readAll(String strFileName) throws Exception
	{
		FileInputStream fin = new FileInputStream(strFileName);

		ByteArrayOutputStream bout = new ByteArrayOutputStream(1000);
		copyStreamData(fin, bout);

		return bout.toByteArray();
	}

	public static void copyStreamData(InputStream in, OutputStream out, boolean bDebug) throws Exception
	{
		copyStreamData(in, out, bDebug, new PrintWriter(System.out), null);
	}

	public static void copyStreamData
	(
		InputStream in, 
		OutputStream out, 
		boolean bDebug, 
		PrintWriter pw, 
		Socket objSocket
	) throws Exception
	{
		if ((in == null) || (out == null))
		{
			throw new IllegalArgumentException("Either InputStream and OutputStream are null");
		}

		int iNumberOfBytesRead = 0;
		do
		{
			byte [] buf = new byte[100];
			try
			{
				iNumberOfBytesRead = in.read(buf);
			}
			catch(IOException e)
			{
				throw new StreamReadError("Problem in reading target InputStream " + e.getLocalizedMessage());
			}

			if (iNumberOfBytesRead == -1)
			{
				break;
			}

			if (bDebug)
			{
				pw.println(new String(CollectionUtils.sliceArray(buf, 0, iNumberOfBytesRead)));
			}

			try
			{
				out.write(buf, 0, iNumberOfBytesRead);
			}
			catch(IOException e)
			{
				throw new StreamReadError("Problem in reading target InputStream");
			}
		}while(1 == 1);
	}

	public static void copyStreamData(String in, OutputStream out) throws Exception
	{
		copyStreamData(in.getBytes(), out);
	}

	public static void copyStreamData(byte [] in, OutputStream out) throws Exception
	{
		out.write(in);
		out.flush();
	}

	public static void copyStreamData(InputStream in, OutputStream out) throws Exception
	{
		copyStreamData(in, out, false);
	}
	
	public static void writeStringToFile(String strFileName, String strStringToBeWritten) throws Exception
	{
		try (FileOutputStream fout = new FileOutputStream(strFileName)) 
		{
			copyStreamData(strStringToBeWritten, fout);
		}
	}
}
