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
 * This exception is thrown, when type of element specified in XMLParser for the
 * tag is different than the type encountered in the document parsed
 */
public class TagTypeMismatchException extends Exception
{

    public TagTypeMismatchException() {
    }

    public TagTypeMismatchException(String message) {
        super(message);
    }

    public TagTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagTypeMismatchException(Throwable cause) {
        super(cause);
    }

    public TagTypeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
