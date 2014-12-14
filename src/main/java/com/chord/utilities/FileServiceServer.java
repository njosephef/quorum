package com.chord.utilities;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.chord.application.FileEntry;
import com.chord.application.FileSearch;
import com.chord.application.Node;
import com.chord.fileservices.RequestFile;

/**
 *
 * @author gasparosoft
 */
public class FileServiceServer
{
	private final Command com;
	private Node node;
	
	public FileServiceServer(final Node node) throws RemoteException
	{
		this.node = node;
		com = new Command();
	}

	public void fetchFile(String filename) throws IOException, java.net.UnknownHostException
	{
		FileSearch fs = new FileSearch();
		FileEntry entry;
		entry = fs.searchFile(filename, this.node);
		System.out.println("********************* entry.getIp().substring(1) " + entry.getIp().substring(1));
		System.out.println("********************* entry.getFSPort() " + entry.getFSPort());
		System.out.println("********************* filename) " + filename);
		RequestFile.getFile(InetAddress.getByName(entry.getIp().substring(1)), entry.getFSPort(), filename);
	}
	
	private void disconnect()
	{
		com.disconnect();
	}
	
	private void chordViewer()
	{
		ChordViewer viewer;
		try
		{
			viewer = new ChordViewer(node);
			viewer.create();
		}
		catch (RemoteException ex)
		{
			Logger.getLogger(FileServiceServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void saveAsItem()
	{
//				String fileName = file.getAbsolutePath() + ".txt";
//				file = new File(fileName);
//				FileOutputStream f = new FileOutputStream(file);
//				PrintStream p = new PrintStream(f);
//				p.print(message);
	}

	private void helpContents()
	{
		Help helpBox = new Help();
	}
	
	private void nodeViewer()  
	{
		try
		{
			// TODO add your handling code here:
			NodeViewer viewer = new NodeViewer(node);
		}
		catch (RemoteException ex)
		{
			Logger.getLogger(FileServiceServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/*public static void main(String[] args)
	{
	    try
        {
            fetchFile("A Brief History of Computing.pdf");
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}*/
}
