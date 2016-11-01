/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.neww;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.rt.utils.FileUtils;

/**
 *
 * @author Krishna
 */
public class CServerConfigurationManager extends ServerComponent
{
    private Properties dictReadOnlyProperties;
    private Properties dictEditableProperties;
    
    //  multiple file names can be passed, which contains properties information.
    private List<String> lsStrReadOnlyPropertiesPath;
    private List<String> lsStrEditablePropertiesPath;

    {
        dictReadOnlyProperties = new Properties();
        dictEditableProperties = new Properties();
        
        lsStrReadOnlyPropertiesPath = new ArrayList<String>();
        lsStrEditablePropertiesPath = new ArrayList<String>();
    }
    
    //  invocation of this constructor function, implies that this server component
    //  is no more active.
    public CServerConfigurationManager() {
        super(false);
    }

    public CServerConfigurationManager(ArrayList<String> lsStrReadOnlyPropertiesPath, ArrayList<String> lsStrEditablePropertiesPath) {
        super(true);
        this.lsStrReadOnlyPropertiesPath = lsStrReadOnlyPropertiesPath;
        this.lsStrEditablePropertiesPath = lsStrEditablePropertiesPath;
    }
    
    public void loadConfigurationsFromFile() throws Exception
    {
        if (FileUtils.doesAllFileExists(lsStrReadOnlyPropertiesPath) == false)
        {
            throw new FileNotFoundException("Any of read-only configuration file does not exists " + lsStrReadOnlyPropertiesPath);
        }
        
        if (FileUtils.doesAllFileExists(lsStrEditablePropertiesPath) == false)
        {
            throw new FileNotFoundException("Any of editable configuration file does not exists " + lsStrEditablePropertiesPath);
        }
        
        Properties objTempProperties = new Properties();
        
        for (int iCounter = 0; iCounter < this.lsStrReadOnlyPropertiesPath.size(); iCounter++)
        {
            objTempProperties.load(new FileInputStream(this.lsStrReadOnlyPropertiesPath.get(iCounter)));
            this.dictReadOnlyProperties.putAll(objTempProperties);
        }
        
        for (int iCounter = 0; iCounter < this.lsStrEditablePropertiesPath.size(); iCounter++)
        {
            objTempProperties.load(new FileInputStream(this.lsStrEditablePropertiesPath.get(iCounter)));
            this.dictEditableProperties.putAll(objTempProperties);
        }
    }

    public Properties getDictReadOnlyProperties() {
        return dictReadOnlyProperties;
    }

    public void setDictReadOnlyProperties(Properties dictReadOnlyProperties) {
        this.dictReadOnlyProperties = dictReadOnlyProperties;
    }

    public Properties getDictEditableProperties() {
        return dictEditableProperties;
    }

    public void setDictEditableProperties(Properties dictEditableProperties) {
        this.dictEditableProperties = dictEditableProperties;
    }

    public void setReadOnlyProperty(String strPropertyName, String strPropertyValue)
                                        throws Exception
    {
        if (this.dictReadOnlyProperties.containsKey(strPropertyName) == false)
        {
            throw new Exception("Invalid propertyname received " + strPropertyName);
        }
        
        this.dictReadOnlyProperties.put(strPropertyName, strPropertyValue);
    }

    public void setEditableOnlyProperty(String strPropertyName, String strPropertyValue)
                                        throws Exception
    {
        if (this.dictEditableProperties.containsKey(strPropertyName) == false)
        {
            throw new Exception("Invalid propertyname received " + strPropertyName);
        }
        
        this.dictEditableProperties.put(strPropertyName, strPropertyValue);
    }
    
    public List<String> getLsStrReadOnlyPropertiesPath() {
        return lsStrReadOnlyPropertiesPath;
    }

    public void setLsStrReadOnlyPropertiesPath(ArrayList<String> lsStrReadOnlyPropertiesPath) {
        this.lsStrReadOnlyPropertiesPath = lsStrReadOnlyPropertiesPath;
    }

    public List<String> getLsStrEditablePropertiesPath() {
        return lsStrEditablePropertiesPath;
    }

    public void setLsStrEditablePropertiesPath(ArrayList<String> lsStrEditablePropertiesPath) {
        this.lsStrEditablePropertiesPath = lsStrEditablePropertiesPath;
    }
    
}
