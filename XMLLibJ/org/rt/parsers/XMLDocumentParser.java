/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.parsers;

import org.rt.parsers.exceptions.DocumentNodeNotFoundException;
import org.rt.parsers.exceptions.DocumentNodeEmptyOrNullException;
import org.rt.parsers.exceptions.InvalidChildElementException;
import org.rt.parsers.exceptions.MandatoryChildNodeMissingException;
import org.rt.parsers.exceptions.TagTypeMismatchException;
import org.rt.parsers.exceptions.UnKnownTagException;
import org.rt.parsers.exceptions.InvalidElementAttributeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.rt.utils.FileUtils;
import org.rt.utils.StringUtils;
import org.rt.utils.exceptions.DirectoryFoundInsteadOfFileException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Krishna
 */
public class XMLDocumentParser
{
    private String strFileName;
    private String strRoot;
    private Map<String, ArrayList<String>> dictNodes;
    private Map<String, ArrayList<String>> dictAttributes;
    private Map<String, Short> dictTypes;
    private Map<String, ArrayList<String>> dictRequiredChildNodes;
    private Map<String, ArrayList<String>> dictValuesOfTextNodes;
    private Map<String, Map<String, ArrayList<String>>> dictValueOfAttributes;
    
    public XMLDocumentParser()
    {
        this.strFileName = "";
        this.strRoot = "Root";
        this.dictAttributes = new HashMap<>();
        this.dictNodes = new HashMap<>();
        this.dictTypes = new HashMap<>();
        this.dictRequiredChildNodes = new HashMap<>();
    }

    public XMLDocumentParser(String strFileName) 
    {
        this.strFileName = strFileName;
        this.strRoot = "Root";
        this.dictAttributes = new HashMap<>();
        this.dictNodes = new HashMap<>();
        this.dictTypes = new HashMap<>();
        this.dictRequiredChildNodes = new HashMap<>();
    }

    public XMLDocumentParser(String strFileName, Map<String, ArrayList<String>> dictNodes) 
    {
        this.strRoot = "Root";
        this.strFileName = strFileName;
        this.dictNodes = dictNodes;
        this.dictAttributes = new HashMap<>();
        this.dictTypes = new HashMap<>();
        this.dictRequiredChildNodes = new HashMap<>();
    }
    
    public XMLDocumentParser(String strFileName, Map<String, ArrayList<String>> dictNodes, HashMap<String, ArrayList<String>> dictAttributes) 
    {
        this.strRoot = "Root";
        this.strFileName = strFileName;
        this.dictNodes = dictNodes;
        this.dictAttributes = dictAttributes;
        this.dictTypes = new HashMap<>();
        this.dictRequiredChildNodes = new HashMap<>();
    }

    public XMLDocumentParser(String strFileName, Map<String, ArrayList<String>> dictNodes, Map<String, ArrayList<String>> dictAttributes, Map<String, Short> dictTypes) 
    {
        this.strRoot = "Root";
        this.strFileName = strFileName;
        this.dictNodes = dictNodes;
        this.dictAttributes = dictAttributes;
        this.dictTypes = dictTypes;
        this.dictRequiredChildNodes = new HashMap<>();
    }

    public XMLDocumentParser(String strFileName, String strRoot, Map<String, ArrayList<String>> dictNodes, Map<String, ArrayList<String>> dictAttributes, Map<String, Short> dictTypes) {
        this.strFileName = strFileName;
        this.strRoot = strRoot;
        this.dictNodes = dictNodes;
        this.dictAttributes = dictAttributes;
        this.dictTypes = dictTypes;
        this.dictRequiredChildNodes = new HashMap<>();
    }

    public XMLDocumentParser(String strFileName, String strRoot, Map<String, ArrayList<String>> dictNodes, Map<String, ArrayList<String>> dictAttributes, Map<String, Short> dictTypes, Map<String, ArrayList<String>> dictRequiredChildNodes) {
        this.strFileName = strFileName;
        this.strRoot = strRoot;
        this.dictNodes = dictNodes;
        this.dictAttributes = dictAttributes;
        this.dictTypes = dictTypes;
        this.dictRequiredChildNodes = dictRequiredChildNodes;
    }

    public String getStrFileName() {
        return strFileName;
    }

    public void setStrFileName(String strFileName) {
        this.strFileName = strFileName;
    }

    public Map<String, ArrayList<String>> getDictNodes() {
        return dictNodes;
    }

    public void setDictNodes(Map<String, ArrayList<String>> dictNodes) {
        this.dictNodes = dictNodes;
    }

    public Map<String, ArrayList<String>> getDictAttributes() {
        return dictAttributes;
    }

    public void setDictAttributes(Map<String, ArrayList<String>> dictAttributes) {
        this.dictAttributes = dictAttributes;
    }

    public Map<String, Short> getDictTypes() {
        return dictTypes;
    }

    public void setDictTypes(Map<String, Short> dictTypes) {
        this.dictTypes = dictTypes;
    }

    public String getStrRoot() {
        return strRoot;
    }

    public void setStrRoot(String strRoot) {
        this.strRoot = strRoot;
    }

    public Map<String, ArrayList<String>> getDictRequiredChildNodes() {
        return dictRequiredChildNodes;
    }

    public void setDictRequiredChildNodes(Map<String, ArrayList<String>> dictRequiredChildNodes) {
        this.dictRequiredChildNodes = dictRequiredChildNodes;
    }

    public Map<String, ArrayList<String>> getDictValuesOfTextNodes() {
        return dictValuesOfTextNodes;
    }

    public void setDictValuesOfTextNodes(Map<String, ArrayList<String>> dictValuesOfTextNodes) {
        this.dictValuesOfTextNodes = dictValuesOfTextNodes;
    }

    public Map<String, Map<String, ArrayList<String>>> getDictValueOfAttributes() {
        return dictValueOfAttributes;
    }

    public void setDictValueOfAttributes(Map<String, Map<String, ArrayList<String>>> dictValueOfAttributes) {
        this.dictValueOfAttributes = dictValueOfAttributes;
    }

    public static List<String> getArrayListOfNodeNamesFromNodeList(final NodeList lsNodes)
    {
        List<String> lsReturnValues = new ArrayList<String>();
        
        for (int iCounter = 0; iCounter < lsNodes.getLength(); iCounter++)
        {
            Node objNode = lsNodes.item(iCounter);
            
            if (objNode.getNodeType() == Node.ELEMENT_NODE ||
                objNode.getNodeType() == Node.TEXT_NODE)
            {
                if (objNode.getNodeType() == Node.TEXT_NODE)
                {
                    if (StringUtils.isEmptyString(objNode.getNodeValue()) == false)
                    {
                        lsReturnValues.add(objNode.getNodeName());
                    }
                }
                else
                {
                    lsReturnValues.add(objNode.getNodeName());
                }
            }
        }
        
        return lsReturnValues;
    }
    
    public void  parseNodeRecursively(Node objNode) throws TagTypeMismatchException, 
                UnKnownTagException, InvalidElementAttributeException, 
                MandatoryChildNodeMissingException
    {
        String strNodeName = objNode.getNodeName();
        boolean bIsIdAttributePresent = false;
        String strIdAttributeValue = "";
        
        try
        {
            short sNodeType = this.dictTypes.get(strNodeName);
            if (objNode.getNodeType() != sNodeType)
            {
                throw new TagTypeMismatchException("Element type mismatch for " + strNodeName);
            }
            
            if (objNode.getNodeType() == Node.ELEMENT_NODE)
            {
                NamedNodeMap objDict = objNode.getAttributes();
                ArrayList<String> lsAttributes = this.dictAttributes.get(strNodeName);
                
                if (lsAttributes != null)
                {
                    for (int iCounter = 0; iCounter < objDict.getLength(); iCounter++)
                    {
                        if (lsAttributes.contains(((Attr)objDict.item(iCounter)).getName()) == false)
                        {
                            throw new InvalidElementAttributeException("Invalid attribute has been found " + ((Attr)objDict.item(iCounter)).getName());
                        }
                        
                        Map<String, ArrayList<String>> dictAttributesForNode = this.dictValueOfAttributes.get(strNodeName);
                        
                        if (dictAttributesForNode == null)
                        {
                            dictAttributesForNode = new HashMap<>();
                            this.dictValueOfAttributes.put(strNodeName, dictAttributesForNode);
                        }
                        
                        ArrayList<String> lsValuesForAttribute = dictAttributesForNode.get(((Attr)objDict.item(iCounter)).getName());
                        
                        if (lsValuesForAttribute == null)
                        {
                            lsValuesForAttribute = new ArrayList<>();
                            dictAttributesForNode.put(((Attr)objDict.item(iCounter)).getName(), lsValuesForAttribute);
                        }
                        lsValuesForAttribute.add(((Attr)objDict.item(iCounter)).getValue());
                        
                        if (((Attr)objDict.item(iCounter)).getName().equals("Id"))
                        {
                            bIsIdAttributePresent = true;
                            strIdAttributeValue = ((Attr)objDict.item(iCounter)).getValue();
                        }
                    }
                }
                NodeList lsChildren = objNode.getChildNodes();
                List<String> lsALOfChildren = XMLDocumentParser.getArrayListOfNodeNamesFromNodeList(lsChildren);

                ArrayList<String> lsRequiredChildren = this.dictRequiredChildNodes.get(strNodeName);
                if (lsRequiredChildren == null)
                {
                    lsRequiredChildren = new ArrayList<>();
                }
                
                for (int iCounter = 0; iCounter < lsRequiredChildren.size(); iCounter++)
                {
                    if (lsALOfChildren.contains(lsRequiredChildren.get(iCounter)) == false)
                    {
                        throw new MandatoryChildNodeMissingException("Required Child node " + lsRequiredChildren.get(iCounter) + " for parent node " + strNodeName + " is missing");
                    }
                }
                
                for (int iCounter = 0; iCounter < lsChildren.getLength(); iCounter++)
                {
                   Node objChildNode = lsChildren.item(iCounter);
                   
                   if (objChildNode.getNodeType() == Node.COMMENT_NODE)
                       continue;
                   
                   if (objChildNode.getNodeType() == Node.TEXT_NODE)
                   {
                       if (StringUtils.isEmptyString(objChildNode.getNodeValue()) == false)
                       {
                           ArrayList<String> lsValues = this.dictValuesOfTextNodes.get(strNodeName);
                           
                           if (lsValues == null)
                           {
                                lsValues = new java.util.ArrayList<>();
                                if (bIsIdAttributePresent == false)
                                    this.dictValuesOfTextNodes.put(strNodeName, lsValues);
                                else
                                    this.dictValuesOfTextNodes.put(strNodeName + "-" + strIdAttributeValue, lsValues);
                                   
                           }
                           lsValues.add(objChildNode.getNodeValue());
                       }
                       continue;
                   }
                   
                   if (this.dictNodes.get(strNodeName).contains(objChildNode.getNodeName()) == false
                        && objChildNode.getNodeType() != Node.COMMENT_NODE)
                   {
                       throw new InvalidChildElementException("Received unknown child node " + objChildNode.getNodeName() + " for parent node " + strNodeName);
                   }
                   
                   this.parseNodeRecursively(objChildNode);
                }
            }
        }
        catch(NullPointerException e)
        {
            e.printStackTrace(System.out);
            throw new UnKnownTagException("Unknown tag ");
        }
    }
    
    public Document validateDocument(Document objDocument) throws TagTypeMismatchException,
            UnKnownTagException, InvalidElementAttributeException, IOException, 
            SAXException, ParserConfigurationException, DocumentNodeNotFoundException,
            DocumentNodeEmptyOrNullException, MandatoryChildNodeMissingException,
            FileNotFoundException
    {
        if (objDocument == null)
        {
            DocumentBuilderFactory objFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder objBuilder = objFactory.newDocumentBuilder();
            
            try
            {
                if (FileUtils.doesFileExists(this.strFileName) == false)
                {
                    throw new java.io.FileNotFoundException(this.strFileName + " does not exists");
                }
            }
            catch(DirectoryFoundInsteadOfFileException e)
            {
                throw new java.io.FileNotFoundException(this.strFileName + " does not exists");
            }
            
            objDocument = objBuilder.parse(this.strFileName);
        }
        Element objRoot = objDocument.getDocumentElement();
        objRoot.normalize();
        
        if (objRoot.getNodeType() != Node.ELEMENT_NODE)
        {
            throw new DocumentNodeNotFoundException("NodeType() of root is " + Node.DOCUMENT_NODE);
        }
        
        if (StringUtils.isEmptyString(this.strRoot) || 
            (this.strRoot.equals(objRoot.getNodeName()) == false))
        {
            throw new DocumentNodeEmptyOrNullException("strRoot member variable must not be null or empty");
        }
        
        this.dictValuesOfTextNodes = new HashMap<>();
        this.dictValueOfAttributes = new HashMap<>();
        parseNodeRecursively(objRoot);
        
        //System.out.println("Value of Text nodes");
        java.util.Iterator objIter = this.dictValuesOfTextNodes.keySet().iterator();
        
        while (objIter.hasNext())
        {
            String strElement = (String)objIter.next();
            //System.out.println(strElement + " : " + this.dictValuesOfTextNodes.get(strElement));
        }
        //System.out.println("value of attributes");
        //System.out.println(this.dictValueOfAttributes);
        //System.out.println("Finished");
        
        return objDocument;
    }
    
    public Document parse() throws TagTypeMismatchException,
            UnKnownTagException, InvalidElementAttributeException, IOException, 
            SAXException, ParserConfigurationException, DocumentNodeNotFoundException,
            DocumentNodeEmptyOrNullException, MandatoryChildNodeMissingException,
            FileNotFoundException
    {
        return validateDocument(null);
    }
    
    /*
    public Document parseInstantly(Document objDocument)
            throws ParserConfigurationException,
            java.io.FileNotFoundException, SAXException,
            java.io.IOException, DocumentNodeNotFoundException, DocumentNodeEmptyOrNullException
    {
        if (objDocument == null)
        {
            DocumentBuilderFactory objFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder objBuilder = objFactory.newDocumentBuilder();
            
            try
            {
                if (system.util.misc.CUtility.doesFileExists(this.strFileName) == false)
                {
                    throw new java.io.FileNotFoundException(this.strFileName + " does not exists");
                }
            }
            catch(system.util.misc.DirectoryFoundInsteadOfFileException e)
            {
                throw new java.io.FileNotFoundException(this.strFileName + " does not exists");
            }
            
            objDocument = objBuilder.parse(this.strFileName);
        }
        Element objRoot = objDocument.getDocumentElement();
        objRoot.normalize();
        
        if (objRoot.getNodeType() != Node.ELEMENT_NODE)
        {
            throw new DocumentNodeNotFoundException("NodeType() of root is " + Node.DOCUMENT_NODE);
        }
        
        if (system.util.misc.CUtility.isEmptyString(this.strRoot) || 
            (this.strRoot.equals(objRoot.getNodeName()) == false))
        {
            throw new DocumentNodeEmptyOrNullException("strRoot member variable must not be null or empty");
        }
        parseAndPopulateNode(objRoot, null);
    }
    
    public void parseAndPopulateNode(Node objChild, Node objParent)
    {
        String strParentNodeName = "";
        String strChildNodeName = objChild.getNodeName();
        
        if (objParent != null)
            strParentNodeName = objParent.getNodeName();
        
        
    }
    */
}
