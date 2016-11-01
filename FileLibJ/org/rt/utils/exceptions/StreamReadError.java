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
 * This exception is thrown, when IOException/other exception arises, when read()
 * or any other function is invoked to read the target InputStream.
 */
public class StreamReadError extends Exception
{

    public StreamReadError() {
    }

    public StreamReadError(String message) {
        super(message);
    }

    public StreamReadError(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamReadError(Throwable cause) {
        super(cause);
    }

    public StreamReadError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
