/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils.exceptions;

public class ObjectRebuildFailedException extends java.lang.Exception
{
	private String strClassName;
	public ObjectRebuildFailedException(String strMessage, String strClassName)
	{
		super(strMessage);
		setClassName(strClassName);
	}
	
	public String getClassName()
	{
		return this.strClassName;
	}
	
	public void setClassName(final String strClassName)
	{
		this.strClassName = strClassName;
	}
}