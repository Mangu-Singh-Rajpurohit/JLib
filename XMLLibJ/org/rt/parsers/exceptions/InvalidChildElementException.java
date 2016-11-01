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
 * This Exception is raised, when an unknown elmement is present as a
 * child element in the xml file being parsed, but it's not registered
 * as child with the object of XMLParser class
 */
public class InvalidChildElementException extends UnKnownTagException
{

    public InvalidChildElementException() {
    }

    public InvalidChildElementException(String message) {
        super(message);
    }

    public InvalidChildElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidChildElementException(Throwable cause) {
        super(cause);
    }

    public InvalidChildElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
