/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils.exceptions;

public class DirectoryFoundInsteadOfFileException extends Exception
{

    public DirectoryFoundInsteadOfFileException() {
    }

    public DirectoryFoundInsteadOfFileException(String message) {
        super(message);
    }

    public DirectoryFoundInsteadOfFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryFoundInsteadOfFileException(Throwable cause) {
        super(cause);
    }

    public DirectoryFoundInsteadOfFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }    
}
