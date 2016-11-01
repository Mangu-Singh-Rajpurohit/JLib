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
 * This Exception is thrown when any unknown or additional tag is found
 * in pre-defined strctured xml file, while parsing that file
 */
public class UnKnownTagException extends java.lang.Exception
{

    public UnKnownTagException() {
    }

    public UnKnownTagException(String message) {
        super(message);
    }

    public UnKnownTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnownTagException(Throwable cause) {
        super(cause);
    }

    public UnKnownTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
