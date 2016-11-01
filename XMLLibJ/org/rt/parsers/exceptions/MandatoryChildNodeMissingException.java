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
 * This Exception is thrown when any node registered as required-childnode with
 * the object of XMLParser, is not present in the XML document, being parsed with
 * 
 */
public class MandatoryChildNodeMissingException extends Exception
{
    public MandatoryChildNodeMissingException() {
    }

    public MandatoryChildNodeMissingException(String message) {
        super(message);
    }

    public MandatoryChildNodeMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MandatoryChildNodeMissingException(Throwable cause) {
        super(cause);
    }

    public MandatoryChildNodeMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
