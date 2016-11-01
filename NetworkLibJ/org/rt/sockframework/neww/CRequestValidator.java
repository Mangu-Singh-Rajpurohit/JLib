/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.neww;

/**
 *
 * @author Krishna
 */
public abstract class CRequestValidator extends ServerComponent
{

    public CRequestValidator(boolean bIsComponentActive) 
    {
        super(bIsComponentActive);
    }
    
    public abstract void validateRequest(ServerRequest objRequest) throws InvalidRequestException;
}
