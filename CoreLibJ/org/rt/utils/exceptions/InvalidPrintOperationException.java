/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

package org.rt.utils.exceptions;

/**
 *
 * @author Krishna
 * This exception is thrown, when printDetailsOfLatestOperations() is invoked on
 * the object of CTimer class, before it has made any timing measurements
 */
public class InvalidPrintOperationException extends UnsupportedOperationException
{
    public InvalidPrintOperationException() {
    }

    public InvalidPrintOperationException(String message) {
        super(message);
    }

    public InvalidPrintOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPrintOperationException(Throwable cause) {
        super(cause);
    }
    
}
