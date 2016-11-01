/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.old.examples;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import org.rt.extended.ExtendedThread;
import org.rt.sockframework.old.BaseSocketServer;
import org.rt.utils.NetworkUtils;


/**
 *
 * @author Krishna
 */
public class AddToClientInputServer extends BaseSocketServer
{

    public AddToClientInputServer(int port, String locationOfConfigFile, String strServerLoc) throws Exception {
        super(port, locationOfConfigFile, strServerLoc);
    }

    public AddToClientInputServer(Properties dictProperties) throws Exception {
        super(dictProperties);
    }

    public AddToClientInputServer() throws Exception {
    }

    public AddToClientInputServer(int port) throws Exception {
        super(port);
    }

    public AddToClientInputServer(String locationOfConfigFile) throws Exception {
        super(locationOfConfigFile);
    }

    public AddToClientInputServer(String strLocationOfConfigFile, String strServerLoc) throws Exception {
        super(strLocationOfConfigFile, strServerLoc);
    }

    public AddToClientInputServer(String strServerLoc, int iPort) throws Exception {
        super(strServerLoc, iPort);
    }

    
    @Override
    public Object satisfyRequest(Socket objSocket) throws Exception 
    {
        try
        {
            Properties objProperties = null;
            
            if (objProperties == null)
            {
                objProperties = new Properties();
                objProperties.load(new FileInputStream("E:\\programs\\dsLib\\src\\Scripts\\ServerSocketOptions.txt"));
            }
            NetworkUtils.setSocketOptions(objSocket, objProperties);
            NetworkUtils.printSocketOptions(objSocket, System.out);
            
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = objSocket.getInputStream();
                out = objSocket.getOutputStream();
            }
            catch(IOException e)
            {
                this.logError("Error in Obtaining InputStream/OutputStream on the socket connection", this.getObjLogger());
                throw new Exception("Problem in getting Input and OutputStream");
            }
            int iNumberOfBytesRead = -999;
            byte [] buf = new byte[5000];
            
            while (1 == 1)
            {
                try
                {
                    iNumberOfBytesRead = in.read(buf);
                    System.out.println("Received bytes " + iNumberOfBytesRead + ' ' + new java.sql.Timestamp(System.currentTimeMillis()));
                }
                catch(IOException e)
                {
                    this.logError("Error in reading InputStream", this.getObjLogger());
                    throw new Exception("Error in reading InputStream");
                }
                
                if (iNumberOfBytesRead == -1)
                {
                    break;
                }
                
                try
                {
                    for (int iCounter = 0; iCounter < iNumberOfBytesRead; iCounter++)
                    {
                        out.write(buf[iCounter] + 2);
                    }
                    //out.flush();
                    System.out.println("Send " + iNumberOfBytesRead + ' ' + new java.sql.Timestamp(System.currentTimeMillis()));
                }
                catch(IOException e)
                {
                    this.logError("Error in writing OutputStream " + iNumberOfBytesRead, this.getObjLogger());
                    throw new Exception("Error in writing OutputStream " + iNumberOfBytesRead);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(this.getObjLogger().getOut());
            e.printStackTrace(System.out);
        }
        finally
        {
            try
            {
                if (objSocket.isClosed() == false)
                {
                    objSocket.close();
                }
            }
            catch(IOException e)
            {
                
            }
        }
        
        return null;
    }

    @Override
    public void start() throws Exception
    {
        super.start();
        ExtendedThread objThread;
		objThread = new ExtendedThread(
			this, this.getClass().getName(), "listen",
			new Class[] {ArrayList.class},
			new ArrayList<>(),
			"AddToClientInputServer Thread"
		);
        objThread.safeStart();
    }
    
    @Override
    public void terminateActiveConnection() throws Exception {
        
    }
    
}
