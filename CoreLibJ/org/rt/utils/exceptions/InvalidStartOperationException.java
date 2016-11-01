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
 * This exception is thrown when Start operation is invoked, when once it has already
 * been invoked.
 */
public class InvalidStartOperationException extends UnsupportedOperationException
{

    public InvalidStartOperationException() {
    }

    public InvalidStartOperationException(String message) {
        super(message);
    }

    public InvalidStartOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStartOperationException(Throwable cause) {
        super(cause);
    }
}
