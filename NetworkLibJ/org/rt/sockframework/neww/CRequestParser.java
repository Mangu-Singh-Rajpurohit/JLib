/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.neww;

import java.io.InputStream;

/**
 *
 * @author Krishna
 */
public abstract class CRequestParser extends ServerComponent
{
    public CRequestParser() {
        //  For request parser, bIsComponentActive will always be true.
        super(true);
    }
    public abstract ServerRequest parseRequest(InputStream in) throws Exception;
}
