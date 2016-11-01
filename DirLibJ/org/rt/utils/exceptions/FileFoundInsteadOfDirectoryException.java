/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.utils.exceptions;

public class FileFoundInsteadOfDirectoryException extends Exception
{

    public FileFoundInsteadOfDirectoryException() {
    }

    public FileFoundInsteadOfDirectoryException(String message) {
        super(message);
    }

    public FileFoundInsteadOfDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileFoundInsteadOfDirectoryException(Throwable cause) {
        super(cause);
    }

    public FileFoundInsteadOfDirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
