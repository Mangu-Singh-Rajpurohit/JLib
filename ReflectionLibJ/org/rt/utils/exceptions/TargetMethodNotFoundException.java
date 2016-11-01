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
 * This exception is raised when method name passed to ExtendedThread object
 * for execution in thread, doesn't exists.
 */
public class TargetMethodNotFoundException extends Exception
{

    public TargetMethodNotFoundException() {
    }

    public TargetMethodNotFoundException(String message) {
        super(message);
    }

    public TargetMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TargetMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    public TargetMethodNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}