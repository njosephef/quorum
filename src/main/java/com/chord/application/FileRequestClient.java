package com.chord.application;

/*Distributed Systems Chord Implementation over LAN*/
/*@author Haris Fokas*/
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.chord.dhash.IdKey;
import com.chord.exceptions.AlreadyConnectedException;
import com.chord.exceptions.IDNotFoundException;
import com.chord.fileservices.FileManager;
import com.chord.fileservices.ServerThread;
import com.chord.fileservices.Share;
import com.chord.utilities.FileServiceServer;
import com.chord.utilities.networking.IPTools;
import com.chord.utilities.networking.LocateBootstrapNode;
import com.chord.utilities.networking.PeerDiscoveryServer;

public class FileRequestClient
{
	static protected Registry rmiReg;
	static NodeImpl thisNode;

	public static void main(String[] argv) throws IOException, InterruptedException
	{
		/*Basic node info declaration*/
		rmiReg = null; //Registry handle
		
		int FSPort;
		Share share = null;
		NodeProperties myProperties;

		/*Threading declaration*/
		Thread fileServer;

		/*Store processID*/
		String runtimeID;
		runtimeID = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("********************* runtimeID: " + runtimeID);
		
		/*To make it John's IDKey compliant :P*/
		int processID;
		processID = Integer.parseInt(runtimeID.substring(0, runtimeID.indexOf("@")));
		System.out.println("********************* processID: " + processID);
		
		/*Get Own IP Address*/
		InetAddress lanIP;
		lanIP = IPTools.getIP();
		System.out.println("********************* lanIP: " + lanIP);
		
		System.setProperty("java.rmi.server.hostname", lanIP.getHostAddress());
		System.out.println("********************* lanIP.getHostAddress(): " + lanIP.getHostAddress());
		
		thisNode = new NodeImpl(new IdKey(processID, lanIP.toString()));
		System.out.println("new NodeImpl: processID, lanIP.toString() " + lanIP.toString());
		
		FileServiceServer mainFrame = new FileServiceServer(thisNode);
		System.out.println("thisNode.toString() " + thisNode.toString());
		
		/*Get Settings from dialog*/
		/*Share files*/
//		String folder = settingsDialog.getShareFolder();
		String folder = "E:\\share";
		if (folder == null)
		{
			System.exit(1);
		}

		share = new Share(folder);

		// Lame implementation - To be reviewed
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());

		FSPort = r.nextInt(16383) + 49152;
		fileServer = new Thread(new ServerThread(share, FSPort));

		while (true)
		{
			try
			{
				fileServer.start();
				break;
			}
			catch (NullPointerException nExc)
			{
				r.setSeed(System.currentTimeMillis());
				FSPort = r.nextInt(16383) + 49152;
				fileServer = new Thread(new ServerThread(share, FSPort));
			}
		}

		/*Node creation*/
		myProperties = new NodeProperties(lanIP, FSPort, processID, thisNode.getLocalID().toString());
		thisNode.setProperties(myProperties);
		System.out.println("Process ID: " + processID);
		System.out.println("Workstation IP: " + lanIP.toString());
		
//		Log.addMessage("************************* Process ID: " + processID, Log.INFORMATION);
//		Log.addMessage("************************* Workstation IP: " + lanIP.toString(), Log.INFORMATION);


		/*Create RMI Registry*/
		try
		{
			rmiReg = LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry ready");
//			Log.addMessage("RMI registry ready", Log.INFORMATION);
			/*RMIServer rmi = new RMIServer();
			Thread ser = new Thread(rmi);
			ser.start();*/
		}
		catch (java.rmi.server.ExportException ee)
		{
			rmiReg = LocateRegistry.getRegistry();
			System.out.println("Registered on local rmi server");
//			Log.addMessage("Registered on local rmi server", Log.INFORMATION);
		}

		NodeProperties bootstrapNode = LocateBootstrapNode.locate();

		if (bootstrapNode == null) /*This is the first node*/
		{
		    System.out.println("***************** This is the first node");
			try
			{
				/*Bind the service to the registry*/
				//rmiReg.bind(String.valueOf(myProperties.getPid()), thisNode);
			    System.out.println("***************** Bind the service to the registry " + String.valueOf(myProperties.getPid()));
				Naming.bind(String.valueOf(myProperties.getPid()), thisNode);
			}
			catch (RemoteException ex)
			{
//				Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (AlreadyBoundException ex)
			{
//				Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
			}

			try
			{
				thisNode.create();
			}
			catch (AlreadyConnectedException ex)
			{
				System.out.println("AlreadyConnectedException");
			}
			catch (IDNotFoundException ex)
			{
				System.out.println("IDNotFoundException");
			}

			/*Start peer discovery server*/
			System.out.println("***************** Start peer discovery server");
			PeerDiscoveryServer peer = new PeerDiscoveryServer(myProperties);
			Thread peerServer = new Thread(peer);
			peerServer.start();
		}
		else /*Existing node to join*/
		{
		    System.out.println("***************** Existing node to join");
			Registry remoteReg = LocateRegistry.getRegistry(bootstrapNode.getNodeIP().getHostName());

			/*Testing!*/
			for (String obj : remoteReg.list())
			{
				System.out.println(obj);
			}

			/*Get remote object from its registry*/
			Node startingNode = null;
			try
			{
				//startingNode = (Node) remoteReg.lookup(bootstrapNode.getBindName());
				startingNode = (Node) Naming.lookup("//" + bootstrapNode.getNodeIP().getHostAddress() + ":1099/" + bootstrapNode.getBindName());
			}
			catch (RemoteException ex)
			{
				Logger.getLogger(FileRequestClient.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (NotBoundException ex)
			{
				Logger.getLogger(FileRequestClient.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (Exception genE)
			{
				genE.printStackTrace();
			}

			try
			{
				System.out.println("---------------------__>" + "//" + lanIP.getHostAddress() + ":1099/" + myProperties.getPid());
				System.out.println(thisNode.getLocalID().getIP());
				Naming.bind("//" + lanIP.getHostAddress() + ":1099/" + myProperties.getPid(), thisNode);

			}
			catch (RemoteException ex)
			{
				Logger.getLogger(FileRequestClient.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (AlreadyBoundException ex)
			{
				Logger.getLogger(FileRequestClient.class.getName()).log(Level.SEVERE, null, ex);
			}

			try
			{
				thisNode.join(startingNode);
			}
			catch (NullPointerException nexc) {}

			/*Set up peer discovery server*/
			PeerDiscoveryServer peer = new PeerDiscoveryServer(myProperties);
			Thread peerServer = new Thread(peer);
			peerServer.start();
		}

		FileManager fm = new FileManager(share, thisNode);
		fm.distributeFiles();

		thisNode.setShare(share);
		thisNode.getNodeStatistics();

		/*while (true)
		{

//			Thread.sleep(5000);

//			Log.addMessage("IdKey : " + thisNode.getLocalID().hashKeytoHexString(), Log.INFORMATION);
//			IdKey id;

			if (thisNode.getSucListSize() > 0)
			{
				id = thisNode.getSucList(0);
				if (id == null)
				{
					Log.addMessage("Successor : NULL", Log.WARNING);
				}
				else
				{
					Log.addMessage("Successor : " + id.hashKeytoHexString(), Log.INFORMATION);
				}
			}

//			id = thisNode.getPredecessor();
			if (id == null)
			{
				Log.addMessage("Predecessor : NULL", Log.WARNING);
			}
			else
			{
				Log.addMessage("Predecessor : " + id.hashKeytoHexString(), Log.INFORMATION);
			}



//			Log.addMessage("Successor List", Log.TABLES);
			
			for (IdKey temp1 : thisNode.getWholeSucList())
			{
				Log.addMessage(temp1.hashKeytoHexString(), Log.TABLES);
			}

			if (thisNode.getFingerSize() <= 0 || thisNode.getFingers() == null)
			{
				Log.addMessage("Finger List : NULL", Log.WARNING);
			}
			else
			{
				Log.addMessage("Finger List", Log.TABLES);

				List<FingerEntry> fingerList = thisNode.getFingerEntries();
				Iterator<FingerEntry> iter = fingerList.iterator();
				while (iter.hasNext())
				{
					FingerEntry t = iter.next();
					Log.addMessage(t.getKey().hashKeytoHexString() + " [2^" + t.getFirstElement() + " , 2^" + t.getLastElement() + "]", Log.TABLES);
				}
			}
			
			
		}*/
		
		mainFrame.fetchFile("A Brief History of Computing");
	}
}
