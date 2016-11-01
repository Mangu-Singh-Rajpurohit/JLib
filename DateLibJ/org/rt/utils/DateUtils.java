package org.rt.utils;

/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateUtils 
{
	public static String formatTimestamp
	(
		long lTimeInMilliseconds,
		String strFmt,
		String strTimeZone 
	) throws IllegalArgumentException
	{
		if (StringUtils.isEmptyString(strFmt))
			throw new IllegalArgumentException("Invalid value passed for argument strFmt -> " + strFmt);
		
		SimpleDateFormat objDtFmt	= new SimpleDateFormat(strFmt);
		if (!StringUtils.isEmptyString(strTimeZone))
		{
			objDtFmt.setTimeZone(TimeZone.getTimeZone(strTimeZone));
		}
		return objDtFmt.format(new java.util.Date(lTimeInMilliseconds));
	}
}
