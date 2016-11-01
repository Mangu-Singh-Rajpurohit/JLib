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
public class EchoServer extends BaseSocketServer
{
    public static final int CHAR_MODE = 0;
    public static final int WORD_MODE = 1;
    public static final int LINE_MODE = 2;
    
    private int mode;

    public EchoServer() throws Exception 
    {
        this.mode = CHAR_MODE;
    }
    
    public EchoServer(int mode, int port, String locationOfConfigFile, String strServerLoc) throws Exception {
        super(port, locationOfConfigFile, strServerLoc);
    }

    public EchoServer(int mode, Properties dictProperties) throws Exception {
        super(dictProperties);
    }

    public EchoServer(int mode) throws Exception {
        this.mode = mode;
    }

    public EchoServer(int mode, int port) throws Exception {
        super(port);
    }

    public EchoServer(int mode, String locationOfConfigFile) throws Exception {
        super(locationOfConfigFile);
    }

    public EchoServer(int mode, String strLocationOfConfigFile, String strServerLoc) throws Exception {
        super(strLocationOfConfigFile, strServerLoc);
    }

    public EchoServer(int mode, String strServerLoc, int iPort) throws Exception {
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
            
            InputStream in = objSocket.getInputStream();
            OutputStream out = objSocket.getOutputStream();
            
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
            int charRead = -999;
            while (1 == 1)
            {
                try
                {
                    charRead = in.read();
                }
                catch(IOException e)
                {
                    this.logError("Error in reading InputStream", this.getObjLogger());
                    throw new Exception("Error in reading InputStream");
                }
                
                if (charRead == -1)
                {
                    break;
                }
                
                try
                {
                    out.write(charRead);
                }
                catch(IOException e)
                {
                    this.logError("Error in writing OutputStream " + charRead, this.getObjLogger());
                    throw new Exception("Error in writing OutputStream " + charRead);
                }
            }
        }
        catch(Exception e)
        {
            
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
        ExtendedThread objThread = new ExtendedThread(
                this, this.getClass().getName(), "listen", 
                new Class[] {ArrayList.class}, 
                new ArrayList<>(),
                "EchoServer Thread"
                        );
        objThread.safeStart();
    }
    
    @Override
    public void terminateActiveConnection() throws Exception 
    {
        
    }
}
