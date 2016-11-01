/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

package org.rt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.rt.utils.exceptions.FileFoundInsteadOfDirectoryException;

public class DirectoryUtils 
{
	public static boolean doesDirectoryExists(String strDirectoryNameWithFullPath) 
										throws FileFoundInsteadOfDirectoryException
	{
		boolean bExists = doesDirectoryExists(new java.io.File(strDirectoryNameWithFullPath));

		return bExists;
	}
        
	public static boolean doesDirectoryExists(File objFile) 
								throws FileFoundInsteadOfDirectoryException
	{
		boolean bExists = false;
		if (objFile.exists())
		{
			if (objFile.isFile())
			{
				throw new FileFoundInsteadOfDirectoryException(objFile + " is a file");
			}
			else if(objFile.isDirectory())
			{
				bExists = true;
			}
		}

		return bExists;
	}
        
	public static void listFilesRecursively
	(
		java.io.File objDir, 
		List<String> lsFiles
	) throws Exception
	{
		if (objDir.exists() == false)
			throw new Exception(objDir + " does not exists");

		if (objDir.isDirectory() == false)
			throw new Exception(objDir + " is not a directory");

		if (lsFiles == null)
			throw new Exception("Output argument lsFiles missing");

		String strFiles [] = objDir.list();
		for (String strFile : strFiles) 
		{
			File objFile1 = new File(objDir, strFile);
			if (objFile1.isDirectory())
			{
				listFilesRecursively(objFile1, lsFiles);
			}
			else
			{
				lsFiles.add(objFile1.toString());
			}
		}
	}

	public static void listFilesRecursively
	(
		String strDir, 
		List<String> lsFiles
	) throws Exception
	{
		listFilesRecursively(new java.io.File(strDir), lsFiles);
	}

	public static void removeDirs(String strDirPath) throws Exception
	{
		removeDirs(new java.io.File(strDirPath));
	}

	public static void removeDirs(java.io.File objFile) throws Exception
	{
		if (!objFile.exists())
			throw new Exception(objFile + " doesnot exists");

		if (!objFile.isDirectory())
			throw new Exception(objFile + " is not a directory");

		String strList [] = objFile.list();
		for (String strList1 : strList) 
		{
			java.io.File objFile1 = new java.io.File(objFile, strList1);
			if (objFile1.isDirectory())
			{
				removeDirs(objFile1);
			}
			else
			{
				objFile1.delete();
			}
		}
		objFile.delete();
	}

	public static void moveDirs
	(
		String strSrcDirPath, 
		String strDestDirPath
	) throws Exception
	{
		moveDirs(new java.io.File(strSrcDirPath), new java.io.File(strDestDirPath));
	}

	public static void moveDirs
	(
		java.io.File objSrcPath, 
		java.io.File objDestPath
	) throws Exception
	{
		copyDirs(objSrcPath, objDestPath);
		removeDirs(objSrcPath);
	}
        
	public static boolean mkdirs(String strDirPath) throws Exception
	{
		return mkdirs(new File(strDirPath));		
	}
	
	public static void copyDirs(String strOldLoc, String strNewLoc) throws Exception
	{
		copyDirs(new java.io.File(strOldLoc), new java.io.File(strNewLoc));
	}

	public static void copyDirs
	(
		java.io.File objOldLoc, 
		java.io.File objNewLoc
	) throws Exception
	{
		if (objOldLoc.exists() == false)
			throw new Exception("Source directory does not exists");

		if (objNewLoc.exists() == false)
			mkdirs(objNewLoc);

		copyDirsPrivate(objOldLoc, objNewLoc);
	}

	private static void copyDirsPrivate
	(
		java.io.File objOldLoc, 
		java.io.File objNewLoc
	)throws Exception
	{
		String strItems [] = objOldLoc.list();
		for (String strItem : strItems) 
		{
			java.io.File objFile1 = new java.io.File(objOldLoc, strItem);
			java.io.File objFile2 = new java.io.File(objNewLoc, strItem);
			if (objFile1.isDirectory())
			{
				objFile2.mkdir();
				copyDirsPrivate(objFile1, objFile2);
			}
			else
			{
				FileUtils.copyFile(objFile1, objFile2);
			}
		}
	}

	public static void zipDirectory
	(
		String strDirectoryName, 
		String strDestZipFile
	) throws InvalidParameterException, Exception
	{
		if (StringUtils.isEmptyString(strDestZipFile))
			throw new InvalidParameterException("strDestZipFile is missing");

		if (StringUtils.isEmptyString(strDirectoryName))
			throw new InvalidParameterException("strDirectoryName is missing");

		FileOutputStream fileWriter = new FileOutputStream(strDestZipFile);
		ZipOutputStream zip = new ZipOutputStream(fileWriter);

		addFolderToZip("", strDirectoryName, zip);
		zip.flush();
		zip.close();
	}  

	private static void addFileToZip
	(
		String path, 
		String srcFile, 
		ZipOutputStream zip
	) throws Exception 
	{
		File folder = new File(srcFile);
		if (folder.isDirectory()) 
		{
		  addFolderToZip(path, srcFile, zip);
		} 
		else 
		{
		  byte[] buf = new byte[1024];
		  int len;
		  FileInputStream in = new FileInputStream(srcFile);
		  zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));

		  while ((len = in.read(buf)) > 0) 
		  {
			zip.write(buf, 0, len);
		  }
		}
	}

	private static void addFolderToZip
	(
		String path, 
		String srcFolder, 
		ZipOutputStream zip
	) throws Exception 
	{
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) 
		{
			if (path.equals("")) 
			{
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} 
			else 
			{
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
		
	public static boolean mkdirs(File objFileDirPath) throws Exception
	{
		boolean bReturnVal = false;
		
		if (objFileDirPath == null)
		{
			//	implies that given directory does not exists.   eg. c:\\temp\\temp1 will be
			//	invoked as follows:- Iter 1 :- c:\\temp\\temp1
			//						 Iter 2 :- c:\\temp
			//						 Iter 3 :- c:\\		Here if c:\\ exists, then you will never reach itr4 and thus not in this if condition
			//						 Iter 4 :- null 	here this if condition will be triggered	
			
			//bReturnVal = false;
		}
		else if (objFileDirPath.exists())
		{
			bReturnVal = true;
		}
		else
		{
			bReturnVal = mkdirs(objFileDirPath.getParentFile());
			
			if (bReturnVal == true)
			{
				objFileDirPath.mkdir();
			}
		}
		return bReturnVal;
	}
}
