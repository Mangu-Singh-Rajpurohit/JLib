/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.parsers.exceptions;

public class InvalidElementAttributeException extends Exception
{

    public InvalidElementAttributeException() {
    }

    public InvalidElementAttributeException(String message) {
        super(message);
    }

    public InvalidElementAttributeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidElementAttributeException(Throwable cause) {
        super(cause);
    }

    public InvalidElementAttributeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
