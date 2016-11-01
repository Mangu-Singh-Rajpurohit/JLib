/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.extended;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.rt.utils.JavaUtils;
import org.rt.utils.exceptions.InvalidObjectStateForOperation;
import org.rt.utils.exceptions.TargetMethodNotFoundException;

/**
 *
 * @author Krishna
 */
public class ExtendedThread extends Thread
{
    private Object objTarget;
    private Method ObjTargetMethod;
    private List lsArguments;
    
    public ExtendedThread() {
    }

    public ExtendedThread(String name) 
    {
        super(name);
    }

    public ExtendedThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public ExtendedThread(Runnable target, String name) {
        super(target, name);
    }

    public ExtendedThread(Object objTarget, Method ObjTargetMethod, ArrayList<Object> lsArguments) {
        this.ObjTargetMethod = ObjTargetMethod;
        this.lsArguments = lsArguments;
        this.objTarget = objTarget;
    }
    
    public ExtendedThread(Object objTarget, String strClassName, String strTargetMethod, Class<?>[] paramTypeClasses, ArrayList<Object> lsArguments) throws Exception 
    {
        this(objTarget, strClassName, strTargetMethod, paramTypeClasses, lsArguments, "ExtendedThread-1");
    }

    public ExtendedThread(Object objTarget, Method ObjTargetMethod, ArrayList<Object> lsArguments, String name) 
    {
        super(name);
        this.ObjTargetMethod = ObjTargetMethod;
        this.lsArguments = lsArguments;
        this.objTarget = objTarget;
    }
    
    public ExtendedThread(Object objTarget, String strClassName, String strTargetMethod, Class<?>[] paramTypeClasses, ArrayList<Object> lsArguments, String name) throws Exception
    {
        super(name);
        this.ObjTargetMethod = JavaUtils.getMethodForClass(strClassName, strTargetMethod, paramTypeClasses, false);
        
        if (this.ObjTargetMethod == null)
        {
            this.ObjTargetMethod = JavaUtils.getMethodForClass(strClassName, strTargetMethod, paramTypeClasses, true);
            
            if (this.ObjTargetMethod == null)
            {
                throw new TargetMethodNotFoundException("Target Method " + strClassName + "." + strTargetMethod + " does not exists");
            }
        }
        this.lsArguments = lsArguments;
        this.objTarget = objTarget;
    }

    public ExtendedThread(Object objTarget, Method ObjTargetMethod, ArrayList<Object> lsArguments, ThreadGroup group, String name) {
        super(group, name);
        this.ObjTargetMethod = ObjTargetMethod;
        this.lsArguments = lsArguments;
        this.objTarget = objTarget;
    }
    
    public ExtendedThread(Object objTarget, String strClassName, String strTargetMethod, Class<?>[] paramTypeClasses, ArrayList<Object> lsArguments, ThreadGroup group, String name) throws Exception 
    {
        super(group, name);
        this.ObjTargetMethod = JavaUtils.getMethodForClass(strClassName, strTargetMethod, paramTypeClasses);
        this.lsArguments = lsArguments;
        this.objTarget = objTarget;
    }

    public Object getObjTarget() {
        return objTarget;
    }

    public void setObjTarget(Object objTarget) {
        this.objTarget = objTarget;
    }

    public Method getObjTargetMethod() {
        return ObjTargetMethod;
    }

    public void setObjTargetMethod(Method ObjTargetMethod) {
        this.ObjTargetMethod = ObjTargetMethod;
    }

    public List getLsArguments() {
        return lsArguments;
    }

    public void setLsArguments(ArrayList<Object> lsArguments) {
        this.lsArguments = lsArguments;
    }
    
    public void safeStart() throws InvalidObjectStateForOperation
    {
        if (this.objTarget == null)
            throw new InvalidObjectStateForOperation("objTarget must be set prior to calling start()");

        if (this.ObjTargetMethod == null)
            throw new InvalidObjectStateForOperation("ObjTargetMethod must be set prior to calling start()");
        
        if (this.lsArguments == null)
            throw new InvalidObjectStateForOperation("lsArguments can not be a null value");
        
        super.start();
    }
    
	@Override
    public void run()
    {
        try
        {
            this.ObjTargetMethod.invoke(this.objTarget, (Object)this.lsArguments);
        }
        catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace(System.err);
            System.out.println("Method name" + this.ObjTargetMethod.getName());
            System.out.println("Class name" + this.objTarget.getClass().getName());
        }
    }
}