/*
 * Author :- RT RP
 * Purpose :-
 * Copyright (c) 2016, rt and/or its affiliates. All rights reserved.
 * Use is subject to license terms.
 *
 */

package org.rt.utils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import org.rt.extended.ExtendedArrayList;

import org.rt.utils.exceptions.InvalidSocketAddressException;

public class NetworkUtils 
{
	public static boolean isValidPortNumber(int iPort)
	{
		boolean bReturnVal = true;
		if (iPort <= 0 || iPort >= 65536)
		{
			bReturnVal = false;
		}
		return bReturnVal;
	}
	
	public static void connectToURL(String strURL, boolean bLogTimeInfo, String strLogFileName, int iNumberOfTimes) throws Exception
	{
		connectToURL(new java.net.URL(strURL), bLogTimeInfo, strLogFileName, iNumberOfTimes);
	}
	
	public static void connectToURL(java.net.URL objURL, boolean bLogTimeInfo, String strLogFileName, int iNumberOfRequestToSent) throws Exception
	{
		CLogger objLogger = new CLogger();

		if (bLogTimeInfo)
		{
			objLogger = new CLogger(strLogFileName);
		}

		for (int iCounter = 0; iCounter < iNumberOfRequestToSent; iCounter++)
		{
			CTimer objTimer = new CTimer();
			objTimer.start();
			java.net.URLConnection objConnection = objURL.openConnection();
			java.util.Scanner objScanner = new java.util.Scanner(objConnection.getInputStream());

			while (objScanner.hasNext())
			{
				objScanner.next();
			}

			if (bLogTimeInfo)
			{
				objLogger.logInfo((String)GlobalUtils.getFormattedTimeTakenFromMilliSeconds(objTimer.stop(), true));
			}
		}
	}
	
	public static List<Object> getIPAddressAndPortNumberFromSocketAddr(String strSocketAddress) throws InvalidSocketAddressException, Exception
	{
		List<Object> lsReturnValues = new ArrayList<>();

		int iOccurencesOfColon = StringUtils.countOccurenceOfStr1InStr2("://", strSocketAddress);

		String strModifiedSocketAddress = strSocketAddress;
		String strProtocolDescriptor = "";
		if (iOccurencesOfColon > 0)
		{
			iOccurencesOfColon = strSocketAddress.indexOf("://");
			strModifiedSocketAddress = strSocketAddress.substring(iOccurencesOfColon + 3);
			strProtocolDescriptor += strSocketAddress.substring(0, iOccurencesOfColon + 3);
		}
		StringTokenizer objTokenizer = new StringTokenizer(strModifiedSocketAddress, ":");

		if (objTokenizer.countTokens() != 2)
		{
			throw new InvalidSocketAddressException("Invalid Socket address received : " + strSocketAddress);
		}

		lsReturnValues.add(strProtocolDescriptor + objTokenizer.nextToken());
		int iPortNumber = Integer.parseInt(objTokenizer.nextToken());
		lsReturnValues.add(iPortNumber);

		return lsReturnValues;
	}
	
	public static void printSocketConfiguration(Socket objSocket, PrintWriter pw) throws Exception
	{
		pw.println("Send buffer size : " + objSocket.getSendBufferSize());
		pw.println("Receive buffer size : " + objSocket.getReceiveBufferSize());
		pw.println("Linger time " + objSocket.getSoLinger());
		pw.println("Time Out time " + objSocket.getSoTimeout());
		pw.println("TCP delay disabled " + objSocket.getTcpNoDelay());
		pw.println("Reuse addr " + objSocket.getReuseAddress());
	}

	public static void printSocketConfiguration(Socket objSocket, PrintStream pw) throws Exception
	{
		printSocketConfiguration(objSocket, pw);
	}

	public static void printSocketConfiguration(Socket objSocket, OutputStream pw) throws Exception
	{
		printSocketConfiguration(objSocket, new PrintWriter(pw));
	}

    public static ServerSocket establishServer() throws Exception
    {
        return establishServer(false, 0);
    }
    
    public static void printSocketOptions(Socket objSocket) throws Exception
    {
        printSocketOptions(objSocket, System.out);
    }
    
    public static void printSocketOptions(Socket objSocket, PrintStream out) throws Exception
    {
        out.println("Keep-Alive " + objSocket.getKeepAlive());
        out.println("Linger " + objSocket.getSoLinger());
        out.println("LINGER_TIME " + objSocket.getSoLinger());
        out.println("RCV_BUF_SIZE " + objSocket.getReceiveBufferSize());
        out.println("SND_BUF_SIZE " + + objSocket.getSendBufferSize());
        out.println("REUSE_ADDR " + objSocket.getReuseAddress());
        out.println("TIMEOUT_PERIOD " + objSocket.getSoTimeout());
        out.println("TCP_NO_DELAY " + objSocket.getTcpNoDelay());
        out.println("Local Address " + objSocket.getLocalSocketAddress());
        out.println("Remote Address " + objSocket.getRemoteSocketAddress());
    }
    
    public static void setSocketOptions(Socket objSocket, Properties objProperties) throws Exception
    {
        objSocket.setKeepAlive(Boolean.parseBoolean(objProperties.getProperty("KEEP_ALIVE")));
        objSocket.setReceiveBufferSize(Integer.parseInt(objProperties.getProperty("RCV_BUF_SIZE")));
        objSocket.setReuseAddress(Boolean.parseBoolean(objProperties.getProperty("REUSE_ADDR")));
        objSocket.setSendBufferSize(Integer.parseInt(objProperties.getProperty("SND_BUF_SIZE")));
        objSocket.setSoLinger(
                    Boolean.parseBoolean(objProperties.getProperty("LINGER")),
                    Integer.parseInt(objProperties.getProperty("LINGER_TIME"))
            );
        
        objSocket.setSoTimeout(Integer.parseInt(objProperties.getProperty("TIMEOUT_PERIOD")));
        objSocket.setTcpNoDelay(Boolean.parseBoolean(objProperties.getProperty("TCP_NO_DELAY")));
    }
    
    public static URLConnection openURLConnection(String URL, Proxy objProxy) throws Exception
    {
        return openURLConnection(new URL(URL), objProxy);
    }
    
    public static URLConnection openURLConnection(String URL) throws Exception
    {
        return openURLConnection(URL, Proxy.NO_PROXY);
    }
    
    public static URLConnection openURLConnection(URL objURL, Proxy objProxy) throws Exception
    {
        Object [] objIOStreams = new Object[2];
        
        URLConnection objConnection = null;
        
        if (objProxy == null)
        {
            objProxy = Proxy.NO_PROXY;
        }
        
        objConnection = objURL.openConnection(objProxy);
        
        return objConnection;
    }
    
    public static URLConnection openURLConnection(URL objURL) throws Exception
    {
        return openURLConnection(objURL, Proxy.NO_PROXY);
    }
    
    public static ByteArrayOutputStream readFromConnection(URLConnection objCon) throws Exception
    {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(1000);
        InputStream in = objCon.getInputStream();
        
        FileUtils.copyStreamData(in, bOut);
        
        return bOut;
    }
    
    public static ServerSocket establishServer(boolean bUseBind, int iTimeToSleep) throws Exception
    {
        ServerSocket objServer = null;
        
        ExtendedArrayList<String> lsSocketAddresses = ExtendedArrayList.loadFromFile("E:\\programs\\dsLib\\src\\system\\util\\simulations\\socket\\Servers.txt");
        int iBackLogSize = 50;
        
        for (int iCounter = 0; iCounter < lsSocketAddresses.size(); iCounter++)
        {
            String strSocketAddress = (String)lsSocketAddresses.get(iCounter);
            try
            {
                List lsStrIPAndPort = getIPAddressAndPortNumberFromSocketAddr(strSocketAddress);
                
                String strIPAddress = (String)lsStrIPAndPort.get(0);
                String strPort = String.valueOf(lsStrIPAndPort.get(1));
                
                try
                {
                    if (bUseBind)
                    {
                        objServer = new ServerSocket();
                        Thread.sleep(iTimeToSleep);
                        objServer.bind(new InetSocketAddress(strIPAddress, Integer.parseInt(strPort)), iBackLogSize);
                    }
                    else
                    {
                        objServer = new ServerSocket(Integer.valueOf(strPort), iBackLogSize, InetAddress.getByName(strIPAddress));
                    }
                    break;
                }
                catch(NumberFormatException e)
                {
                    throw new InvalidSocketAddressException("Invalid port number received " + strPort);
                }
                catch(UnknownHostException e)
                {
                    throw new InvalidSocketAddressException("Invalid Ip address received " + strIPAddress);
                }
                catch(SecurityException e)
                {
                    throw new Exception("You don't have permission to open socket connection " + e.getLocalizedMessage() + " " + strSocketAddress);
                }
            }
            catch(InvalidSocketAddressException e)
            {
                System.out.println("Invalid Socket address encountered "  + e.getLocalizedMessage() +  "  " + strSocketAddress);
            }
            catch(IOException e)
            {
                System.out.println("Problem in opening socket connection " + e.getLocalizedMessage() +  "  " + strSocketAddress);
            }
            catch(Exception e)
            {
                System.out.println(e.getLocalizedMessage() + e.getLocalizedMessage() +  "  " + strSocketAddress);
            }
        }
        
        return objServer;
    }
}
