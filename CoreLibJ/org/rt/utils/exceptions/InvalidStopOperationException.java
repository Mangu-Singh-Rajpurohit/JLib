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
 * This exception is thrown when stop() operation is invoked prior to
 * start() operation on the object of CTimer class, or stop() is invoked twice
 * consecutively
 */
public class InvalidStopOperationException extends UnsupportedOperationException
{

    public InvalidStopOperationException() {
    }

    public InvalidStopOperationException(String message) {
        super(message);
    }

    public InvalidStopOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStopOperationException(Throwable cause) {
        super(cause);
    }
}
