/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.parsers.exceptions;

/**
 *
 * @author Krishna
 */
public class DocumentNodeEmptyOrNullException extends Exception {

	public DocumentNodeEmptyOrNullException() {
	}

	public DocumentNodeEmptyOrNullException(String message) {
		super(message);
	}

	public DocumentNodeEmptyOrNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentNodeEmptyOrNullException(Throwable cause) {
		super(cause);
	}

	public DocumentNodeEmptyOrNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
