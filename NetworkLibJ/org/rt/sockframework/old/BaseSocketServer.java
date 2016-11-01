/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */
package org.rt.sockframework.old;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import org.rt.extended.ExtendedArrayList;
import org.rt.extended.ExtendedThread;

import org.rt.utils.CLogger;
import org.rt.utils.DirectoryUtils;
import org.rt.utils.GlobalUtils;
import org.rt.utils.StringUtils;

/**
 *
 * @author Krishna
 */
public abstract class BaseSocketServer 
{
    private int port;
    private int shutDownPort;
    private int currentRequestCount;
    private int activeRequestCount;
    private int invalidRequestCount;
    private int peakLoadCount;
    private int maxCapacity;
    
    private boolean bHasServerStarted;
    private boolean bEnableLogging;
    
    private ServerSocket objServer;
    private ServerSocket objShutDownServer;
    
    private Socket objActiveConnection;
    
    private Boolean bMonitorOrSemaphore;
    private CLogger objLogger;
    private ExtendedArrayList<Socket> objActiveConnections = new ExtendedArrayList<>();
    private Object lockOnCounters = new Object();
    
    private String strServerLoc;
    private String strShutDownMsg;
    private String locationOfConfigFile;
    private String strServerName;
    
    //  by default allow maximum 100 requests active at a time.
    public static final int DEFAULT_MAX_CAPACITY = 100;
    public static final int DEFAULT_PORT = 23234;
    public static final int DEFAULT_SHUT_DOWN_PORT = 29333;
    public static final String DEFAULT_SHUTDOWN_MSG = "ShutitDown";
    
	//  Directory in which config file is present, that directory will be
    //  regarded as server directory by default.
    public static final String DEFAULT_CONFIGFILE_LOC = "c:\\SocketServer\\config.xml";
    public static final String DEFAULT_SERVER_LOC = "c:\\temp\\tempServer";

    public BaseSocketServer(int port, String locationOfConfigFile, String strServerLoc) throws Exception
    {
        this.port = port;
        this.locationOfConfigFile = locationOfConfigFile;
        this.strServerLoc = strServerLoc;
        this.bEnableLogging = true;
        this.shutDownPort = BaseSocketServer.DEFAULT_SHUT_DOWN_PORT;
        this.strShutDownMsg = BaseSocketServer.DEFAULT_SHUTDOWN_MSG;
        //  false means continue running server, 
        //  true means stop the server.
        this.bMonitorOrSemaphore = false;
        this.objLogger = new CLogger();
    }

    public BaseSocketServer(Properties dictProperties) throws Exception
    {
        this
        (
                Integer.parseInt(dictProperties.getProperty("PortNumber", String.valueOf(DEFAULT_PORT))),
                dictProperties.getProperty("ConfigFileLocation", DEFAULT_CONFIGFILE_LOC),
                dictProperties.getProperty("ServerLocation", DEFAULT_SERVER_LOC)    
        );
        
        this.bEnableLogging = Boolean.parseBoolean(dictProperties.getProperty("LoggingEnabled", String.valueOf(true)));
        this.objLogger.setByteLoggingLevel(Byte.parseByte(dictProperties.getProperty("LoggingLevel", "2")));
        this.shutDownPort = Integer.parseInt(dictProperties.getProperty("ShutdownPort", String.valueOf(DEFAULT_SHUT_DOWN_PORT)));
        this.strShutDownMsg = dictProperties.getProperty("StringShutDownPassword", "ShutItdown");
        this.strServerName = dictProperties.getProperty("ServerName", "Server1");
        this.maxCapacity = Integer.parseInt(dictProperties.getProperty("MaximumActiveRequests", "100"));
    }
    
    public BaseSocketServer() throws Exception{
        this(DEFAULT_PORT, DEFAULT_CONFIGFILE_LOC, DEFAULT_SERVER_LOC);
    }

    public BaseSocketServer(int port) throws Exception {
        this(port, DEFAULT_CONFIGFILE_LOC, DEFAULT_SERVER_LOC);
    }

    public BaseSocketServer(String locationOfConfigFile) throws Exception {
        this(DEFAULT_PORT, locationOfConfigFile, DEFAULT_SERVER_LOC);
    }

    public BaseSocketServer(String strLocationOfConfigFile, String strServerLoc) throws Exception {
        this(DEFAULT_PORT, strLocationOfConfigFile, strServerLoc);
    }
    
    public BaseSocketServer(String strServerLoc, int iPort) throws Exception
    {
        this(iPort, DEFAULT_CONFIGFILE_LOC, strServerLoc);
    }

    public boolean isbEnableLogging() {
        return bEnableLogging;
    }

    public CLogger getObjLogger() {
        return objLogger;
    }

    public void setbEnableLogging(boolean bEnableLogging) {
        this.bEnableLogging = bEnableLogging;
    }

    public Socket getObjActiveConnection() {
        return objActiveConnection;
    }

    public void setObjActiveConnection(Socket objActiveConnection) {
        this.objActiveConnection = objActiveConnection;
    }
    
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ExtendedArrayList<Socket> getObjActiveConnections() {
        return objActiveConnections;
    }

    public void setObjActiveConnections(ExtendedArrayList<Socket> objActiveConnections) {
        this.objActiveConnections = objActiveConnections;
    }

    public String getStrServerName() {
        return strServerName;
    }

    public void setStrServerName(String strServerName) {
        this.strServerName = strServerName;
    }

    public int getShutDownPort() {
        return shutDownPort;
    }

    public void setShutDownPort(int shutDownPort) {
        this.shutDownPort = shutDownPort;
    }

    public String getLocationOfConfigFile() {
        return locationOfConfigFile;
    }

    public void setLocationOfConfigFile(String locationOfConfigFile) {
        this.locationOfConfigFile = locationOfConfigFile;
    }

    public ServerSocket getObjServer() {
        return objServer;
    }

    public Boolean isbMonitorOrSemaphore() {
        return bMonitorOrSemaphore;
    }

    public void setbMonitorOrSemaphore(Boolean bMonitorOrSemaphore) {
        this.bMonitorOrSemaphore = bMonitorOrSemaphore;
    }

    public String getStrServerLoc() {
        return strServerLoc;
    }

        public int getMaxCapacity() 
    {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) 
    {
        this.maxCapacity = maxCapacity;
    }

    public int getInvalidRequestCount() {
        return invalidRequestCount;
    }

    public void setInvalidRequestCount(int invalidRequestCount) {
        this.invalidRequestCount = invalidRequestCount;
    }

    public int getCurrentRequestCount() 
    {
        return currentRequestCount;
    }

    public int getPeakLoadCount() 
    {
        return peakLoadCount;
    }
    
    public void setStrServerLoc(String strServerLoc) throws Exception
    {
        this.setStrServerLoc(strServerLoc, true);
    }

    public void setStrServerLoc(String strServerLoc, boolean bReinitialize) throws Exception
    {
        if (bHasServerStarted)
        {
            throw new Exception("Can not set location of server, once start() is invoked");
        }
        
        this.strServerLoc = strServerLoc;
        this.objLogger.setByteLoggerMode(CLogger.FILE_MODE);
        
        if (bReinitialize)
        {
            this.objLogger.reinitialize(GlobalUtils.join(strServerLoc, "logs.txt"), this.objLogger.getByteLoggingLevel());
        }
    }

    public ServerSocket getObjShutDownServer() {
        return objShutDownServer;
    }

    public void setObjShutDownServer(ServerSocket objShutDownServer) {
        this.objShutDownServer = objShutDownServer;
    }

    public String getStrShutDownMsg() {
        return strShutDownMsg;
    }

    public void setStrShutDownMsg(String strShutDownMsg) {
        this.strShutDownMsg = strShutDownMsg;
    }

    public int getActiveRequestCount() {
        return activeRequestCount;
    }

    public void incrementInvalidRequestCount()
    {
        synchronized(this.getLockOnCounters())
        {
            this.invalidRequestCount += 1;
        }
    }
    
    public void incrementActiveRequestCount() 
    {
        synchronized(this.getLockOnCounters())
        {
            this.currentRequestCount += 1;
            this.activeRequestCount += 1;
        }
    }
    
    public void decrementActiveRequestCount() 
    {
        synchronized(this.getLockOnCounters())
        {
            this.activeRequestCount -= 1;
        }
    }
    
    public Object getLockOnCounters() {
        return lockOnCounters;
    }

    public void setLockOnCounters(Object lockOnCounters) {
        this.lockOnCounters = lockOnCounters;
    }
    
    public boolean isbHasServerStarted() 
    {
        return bHasServerStarted;
    }
    
    public void start() throws Exception
    {
        this.objServer = new ServerSocket(this.port);
        this.objShutDownServer = new ServerSocket(this.shutDownPort);
        
        DirectoryUtils.mkdirs(strServerLoc);
        this.bHasServerStarted = true;

        this.objLogger.reinitialize(GlobalUtils.join(strServerLoc, "server_logs.txt"), this.objLogger.getByteLoggingLevel(), CLogger.FILE_MODE, 0);
        
        this.logInfo("Server " + this.getSocketAddress() + " started");
        System.out.println("Server " + this.getSocketAddress() + " started");
    }

    public void listenShutDown(ArrayList<Object> lsArguments) throws Exception
    {
        this.listenShutDown();
    }
    
    public void listenShutDown() throws Exception
    {
        if (this.objShutDownServer == null)
        {
            throw new Exception("listenShutDown() can't be invoked prior to start");
        }
        
        System.out.println(this.getSocketAddress() +  " : started listening for shutting down");
        this.logInfo(this.getSocketAddress() +  " : started listening for shutting down");
        
        while (true)
        {
            Socket objSocket = this.objShutDownServer.accept();
            this.logInfo(this.getSocketAddress() + " : Received request for stopping from remote address : " + objSocket.getRemoteSocketAddress().toString());
            System.out.println(this.getSocketAddress() + " : Received request for stopping from remote address : " + objSocket.getRemoteSocketAddress().toString());
            
            synchronized(this.bMonitorOrSemaphore)
            {
                if (this.bMonitorOrSemaphore)
                {
                    this.logDebug("Received stop() signal from main thread");
                    System.out.println("Received stop() signal from main thread");
                    objSocket.close();
                    break;
                }
            }
            
            Scanner sc = new Scanner(objSocket.getInputStream());
            PrintWriter pw = new PrintWriter(objSocket.getOutputStream());
            if (sc.hasNext())
            {
                String strMsg = sc.nextLine();
                
                if (strMsg.equals(this.getStrShutDownMsg()))
                {
                    this.stop();
                    //  1 indicates that server has been shut down
                    //  successfully
                    pw.print("1");
                    pw.flush();
                    
                    pw.close();
                    sc.close();
                    
                    this.logInfo(this.getSocketAddress() + " Shutting down server. Closing shutdown port");
                    System.out.println(this.getSocketAddress() + " Shutting down server. Closing shutdown port");
                    break;
                }
                else
                {
                    //  -2 indicates that invalid shutdownmsg have been
                    //  received
                    this.logInfo(this.getSocketAddress() + " Received invalid shutdown message from remote location : " + objSocket.getRemoteSocketAddress().toString());
                    System.out.println(this.getSocketAddress() + " Received invalid shutdown message from remote location : " + objSocket.getRemoteSocketAddress().toString());
                    pw.print("-2");
                    pw.flush();
                }
            }
            else
            {
                //  -1 indicates that server 
                pw.print("-1");
                pw.flush();
            }
            
            pw.close();
            sc.close();
        }
        
        this.objShutDownServer.close();
        this.logInfo(this.getSocketAddress() + " server stopped");
        System.out.println(this.getSocketAddress() + " server stopped");
    }
    
    public void listen(ArrayList<Object> lsArgs) throws Exception
    {
        ExtendedThread objThread = new ExtendedThread(
                                                    this, BaseSocketServer.class.getName(),
                                                    "listenShutDown", new Class[] {ArrayList.class}, 
                                                    lsArgs,
                                                    "ShutdownListenerThread"
                                                    );
        objThread.start();
        this.listen();
    }
    
    public abstract Object satisfyRequest(Socket objSocket) throws Exception;
    
    public void satisfyRequest(ArrayList<Object> args) throws Exception
    {
        if (args == null || args.size() != 1)
        {
            throw new Exception("args must contains only Socket object");
        }
        
        satisfyRequest((Socket)args.get(0));
    }
    
    public void stop()
    {
        synchronized(this.bMonitorOrSemaphore)
        {
            this.bMonitorOrSemaphore = true;
            try
            {
				Socket objSocketShutDown;
				try (Socket objSocket = new Socket("127.0.0.1", this.port)) 
				{
					objSocketShutDown = new Socket("127.0.0.1", this.shutDownPort);
					System.out.println(objSocket.getLocalSocketAddress() + " " + objSocketShutDown.getLocalSocketAddress());
				}
                objSocketShutDown.close();
            }
            catch(java.net.UnknownHostException e)
            {
                e.printStackTrace(System.out);
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
    }
    
    public void logInfo(String strMsg, CLogger objLogger) throws Exception
    {
        if (this.bEnableLogging)
        {
            objLogger.logInfo(strMsg);
        }
    }
    
    public void logInfo(String strMsg) throws Exception
    {
        logInfo(strMsg, this.objLogger);
    }
    
    public void logError(String strMsg, CLogger objLogger) throws Exception
    {
        if (this.bEnableLogging)
        {
            objLogger.logError(strMsg);
        }
    }
    
    public void logError(String strMsg) throws Exception
    {
        logError(strMsg, this.objLogger);
    }
    
    public void logWarn(String strMsg, CLogger objLogger) throws Exception
    {
        if (this.bEnableLogging)
        {
            objLogger.logWarn(strMsg);
        }
    }
    
    public void logWarn(String strMsg) throws Exception
    {
        logWarn(strMsg, this.objLogger);
    }

    public void logDebug(String strMsg, CLogger objLogger) throws Exception
    {
        if (this.bEnableLogging)
        {
            objLogger.logDebug(strMsg);
        }
    }
    
    public void logDebug(String strMsg) throws Exception
    {
        logDebug(strMsg, this.objLogger);
    }
    
    public String getSocketAddress()
    {
        String strServerName = StringUtils.isEmptyString(this.strServerName) ? "Server" : this.strServerName;
        return strServerName + ":" + this.getPort();
    }
    
    public void listen() throws Exception
    {
        if (this.objServer == null)
        {
            throw new Exception("listen() can't be invoked prior to start");
        }
        
        System.out.println("Server" + this.getSocketAddress() + " started listening");
        this.logInfo("Server" + this.getSocketAddress() + " started listening");
        
        while (true)
        {
            Socket objSocket = this.objServer.accept();
            
            synchronized(this.bMonitorOrSemaphore)
            {
                if (this.bMonitorOrSemaphore == true)
                {
                    System.out.println(this.getSocketAddress() + " received signal for closing the server");
                    this.logInfo(this.getSocketAddress() + " received signal for closing the server");
                    objSocket.close();
                    
                    for (int iCounter = 0; iCounter < this.getObjActiveConnections().size(); iCounter++)
                    {
                        if (((Socket)this.getObjActiveConnections().get(iCounter)) == this.objActiveConnection)
                        {
                            continue;
                        }
                        
                        try
                        {
                            ((Socket)this.getObjActiveConnections().get(iCounter)).close();
                        }
                        catch(Exception e)
                        {
                            System.out.println("Problem in closing socket connection " + ((Socket)this.getObjActiveConnections().get(iCounter)).getRemoteSocketAddress());
                        }
                    }
                    //  stop the server
                    break;
                }
            }
            
            this.objActiveConnection = objSocket;
            ArrayList<Object> objList = new ArrayList<>();
            objList.add(objSocket);
            
            ExtendedThread objThread = new ExtendedThread(
                                    this, 
                                    "system.util.servers.BaseSocketServer", 
                                    "satisfyRequest", new Class[] {ArrayList.class}, 
                                    objList,
                                    objSocket.getRemoteSocketAddress().toString()
                                   );
            
            objThread.safeStart();
        }
        
        //this.terminateActiveConnection();
        this.objServer.close();
        this.logInfo(this.getSocketAddress() + " Server closed");
        System.out.println(this.getSocketAddress() + " Server closed");
    }
    
    //  This method is required, because recently established connection may to
    //  to be terminated in a special way(specific to each server), as it may
    //  contain request for stopping server. So, each derived server class from this
    //  class have to implement this method in their own way.
    public abstract void terminateActiveConnection() throws Exception;
    
    public static void main(String args[]) throws Exception
    {
        /*
        BaseSocketServer server = new BaseSocketServer();
        server.start();
        ExtendedThread objThread = new ExtendedThread(
                server, server.getClass().getName(), "listen", 
                new Class[] {ArrayList.class}, 
                new ArrayList<Object>()
                        );
        objThread.start();
        //server.listen();
     /*   try
        {
            Thread.sleep(6000);
            server.stop();
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        */
    }
}
