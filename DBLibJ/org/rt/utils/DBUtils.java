/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

package org.rt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils 
{
	public static Connection getDBConnection(String strDBDriver, String strDBURI, String strUserName, String strPasswd) throws IllegalArgumentException
	{
		if (StringUtils.isEmptyString(strDBDriver))
		{
			throw new IllegalArgumentException("strDBDriver is missing");
		}

		if (StringUtils.isEmptyString(strDBURI))
		{
			throw new IllegalArgumentException("strDBURI is missing");
		}

		if (StringUtils.isEmptyString(strUserName))
		{
			throw new IllegalArgumentException("strUserName is missing");
		}

		if (StringUtils.isEmptyString(strPasswd))
		{
			throw new IllegalArgumentException("strPasswd is missing");
		}

		Connection con = null;
		try
		{
                    Class.forName(strDBDriver);
                    con = DriverManager.getConnection(strDBURI, strUserName, strPasswd);
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace(System.out);
		}
		return con;
	}
}
