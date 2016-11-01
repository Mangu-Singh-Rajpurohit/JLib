/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.neww;

import java.util.ArrayList;
import org.rt.utils.StringUtils;
import org.rt.utils.exceptions.InvalidObjectStateForOperation;

/**
 *
 * @author Krishna
 */
public class CNewBaseSocketServer 
{
    private boolean bHasServerStarted;
    private CServerConfigurationManager objConfigurationMgr;
    private CRequestParser objRequestParser;
    private CRequestValidator objValidator;
    private CLogManager objLogManager;
    private CServerCounterManager objCounterManager;
    
    {
        bHasServerStarted = false;
        objConfigurationMgr = new CServerConfigurationManager();
        objLogManager = new CLogManager();
        objCounterManager = new CServerCounterManager();
    }
    
    public CNewBaseSocketServer()
    {
    }

    public CNewBaseSocketServer
    (
        CRequestParser objRequestParser, 
        CRequestValidator objValidator,
        ArrayList<String> lsStrReadOnlyPropertiesPath, 
        ArrayList<String> lsStrEditablePropertiesPath,
        String strLogMgrProperitesFileName,
        String strCounterFileLocation
    ) throws Exception
    {
        this.setObjRequestParser(objRequestParser);
        this.setObjValidator(objValidator);
        
        if (lsStrEditablePropertiesPath != null && lsStrEditablePropertiesPath != null)
        {
            this.objConfigurationMgr = new CServerConfigurationManager(lsStrReadOnlyPropertiesPath, lsStrEditablePropertiesPath);
        }
        
        if (StringUtils.isEmptyString(strCounterFileLocation) == false)
        {
            this.objCounterManager = new CServerCounterManager(strCounterFileLocation);
        }
        
        if (StringUtils.isEmptyString(strLogMgrProperitesFileName) == false)
        {
            this.objLogManager = new CLogManager(strLogMgrProperitesFileName);
        }
    }

    public boolean isbHasServerStarted() 
    {
        return bHasServerStarted;
    }

    public void setbHasServerStarted(boolean bHasServerStarted) 
    {
        this.bHasServerStarted = bHasServerStarted;
    }

    public CServerConfigurationManager getObjConfigurationMgr() 
    {
        return objConfigurationMgr;
    }

    public void setObjConfigurationMgr(CServerConfigurationManager objConfigurationMgr) throws Exception
    {
        if (bHasServerStarted == true)
        {
            throw new InvalidObjectStateForOperation("Can not set ConfigurationMgr object, after once server has started");
        }
        this.objConfigurationMgr = objConfigurationMgr;
    }

    public CRequestParser getObjRequestParser() 
    {
        return objRequestParser;
    }

    public void setObjRequestParser(CRequestParser objRequestParser) throws Exception
    {
        if (bHasServerStarted == true)
        {
            throw new InvalidObjectStateForOperation("Can not set Requestparser object, after once server has started");
        }
        this.objRequestParser = objRequestParser;
    }

    public CRequestValidator getObjValidator() {
        return objValidator;
    }

    public void setObjValidator(CRequestValidator objValidator) throws Exception
    {
        if (bHasServerStarted == true)
        {
            throw new InvalidObjectStateForOperation("Can not set Validator object, after once server has started");
        }
        this.objValidator = objValidator;
    }

    public CLogManager getObjLogManager() {
        return objLogManager;
    }

    public void setObjLogManager(CLogManager objLogManager)  throws Exception
    {
        if (bHasServerStarted == true)
        {
            throw new InvalidObjectStateForOperation("Can not set logmanager object, after once server has started");
        }
        this.objLogManager = objLogManager;
    }

    public CServerCounterManager getObjCounterManager() 
    {
        return objCounterManager;
    }

    public void setObjCounterManager(CServerCounterManager objCounterManager)  throws Exception
    {
        if (bHasServerStarted == true)
        {
            throw new InvalidObjectStateForOperation("Can not set ServerCounter object, after once server has started");
        }
        this.objCounterManager = objCounterManager;
    }
    
    public void start() throws Exception
    {
        if (
                this.objConfigurationMgr.isbIsComponentActive() == false ||
                this.objCounterManager.isbIsComponentActive() == false ||
                this.objLogManager.isbIsComponentActive() == false ||
                this.objRequestParser.isbIsComponentActive() == false ||
                this.objValidator.isbIsComponentActive() == false
            )
        {
            throw new Exception("One or more components are in invactive state");
        }
        
        
    }
    
    public void listen() throws Exception
    {
        
    }
    
    public void stop() throws Exception
    {
        
    }
}
