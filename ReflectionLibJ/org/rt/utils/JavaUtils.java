/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils;

import java.lang.reflect.Method;
import org.rt.utils.StringUtils;

public class JavaUtils 
{
	public static Method getMethodForClass
	(
		String strClassName, 
		String strMethodName,
		Class<?>[] objParamTypes
	) throws ClassNotFoundException 
	{
		return getMethodForClass
		(
			strClassName, 
			strMethodName, 
			objParamTypes, 
			false
		);
	}

	/*
	 * *
	 * @param strClassName
	 * @param strMethodName
	 * @param objParamTypes : Pass this argument as null, when your method does not receive
	 *                        any parameters.
	 * @return Object of type method
	 * @throws ClassNotFoundException 
	 */
	public static Method getMethodForClass
	(
		String strClassName, 
		String strMethodName,
		Class<?>[] objParamTypes, 
		boolean bSearchInParentClasses
	) throws ClassNotFoundException 
	{
		Method objMethodReturnValue = null;

		Class objClass = Class.forName(strClassName);

		Method objMethods[] = null;

		if (bSearchInParentClasses) 
		{
			objMethods = objClass.getMethods();
		} 
		else 
		{
			objMethods = objClass.getDeclaredMethods();
		}
		
		for (Method objMethod : objMethods) 
		{
			if (objMethod.getName().equals(strMethodName) == false) 
			{
				continue;
			}

			Class<?>[] objMethodParamTypes = objMethod.getParameterTypes();

			if (objParamTypes != null) 
			{
				if (objMethodParamTypes == null || objMethodParamTypes.length == 0) 
				{
					continue;
				}

				if (CollectionUtils.bAreArraysEqual(objParamTypes, objMethodParamTypes) == false) 
				{
					continue;
				}
			} 
			else 
			{
				if (objMethodParamTypes.length > 0) 
				{
					continue;
				}
			}

			objMethodReturnValue = objMethod;
			break;
		}

		return objMethodReturnValue;
	}
	
	public static String getClassLocation(Class objCls, String strPackage) throws Exception
	{
		String strUrlLocation = GlobalUtils.join(
				objCls.getProtectionDomain().getCodeSource().getLocation().getFile(),
				strPackage
			);

		return strUrlLocation;
	}
}
