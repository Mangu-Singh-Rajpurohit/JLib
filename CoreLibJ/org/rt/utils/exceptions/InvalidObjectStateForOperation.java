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
 * This class represents, when exception have to be raised, when an
 * operation is performed, on object, when object is not in appropriate
 * state for that operation to be performed.
 */
public class InvalidObjectStateForOperation extends Exception
{

    public InvalidObjectStateForOperation() 
    {
        super();
    }

    public InvalidObjectStateForOperation(String message) 
    {
        super(message);
    }
    
}
