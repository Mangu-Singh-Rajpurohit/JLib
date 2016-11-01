/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.neww;

public abstract class ServerComponent 
{
    private boolean bIsComponentActive;

    public ServerComponent(boolean bIsComponentActive) {
        this.bIsComponentActive = bIsComponentActive;
    }

    public boolean isbIsComponentActive() {
        return bIsComponentActive;
    }

    public void setbIsComponentActive(boolean bIsComponentActive) {
        this.bIsComponentActive = bIsComponentActive;
    }
}