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
 * This exception is raised when Invalid socket address is passed to a function/method
 * as an argument
 */
public class InvalidSocketAddressException extends Exception
{

    public InvalidSocketAddressException() {
    }

    public InvalidSocketAddressException(String message) {
        super(message);
    }

    public InvalidSocketAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSocketAddressException(Throwable cause) {
        super(cause);
    }

    public InvalidSocketAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
