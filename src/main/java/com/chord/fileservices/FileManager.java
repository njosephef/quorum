package com.chord.fileservices;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.chord.application.FileEntry;
import com.chord.application.Node;
import com.chord.dhash.FileNameKey;
import com.chord.utilities.Log;

/**
 *
 * @author haris
 */
public class FileManager
{

	Share share;
	Node myNode;

	public FileManager(Share share, Node myNode)
	{
		this.share = share;
		this.myNode = myNode;
	}

	/**
	 * Distributes files throughout the ring
	 */
	public void distributeFiles()
	{
		Log.addMessage("Distributing Files", Log.ERROR);
		String[] filenames = share.getFilenames();
		Log.addMessage(filenames.length + "", Log.ERROR);

		// remove extension of files
		//remove postfix
		/*for (int i =0; i < filenames.length; i++)
		{
			if (filenames[i].lastIndexOf(".") > 0)
				filenames[i] = filenames[i].substring(0, filenames[i].lastIndexOf("."));
		}*/

		for (int i = 0; i < share.getNumberOfFiles(); i++)
		{
			try
			{
				/*Hash every filename*/
				FileEntry entry = new FileEntry(filenames[i], myNode.getLocalID().getIP(), myNode.getLocalID().getPID(), myNode.getProperties().getFSPort());

				FileNameKey fnk = new FileNameKey(filenames[i]);

				/*Distribute the file info to the appropriate node*/
				System.out.println("**************Distribute the file info to the appropriate node**********");
				Node owner = myNode.find_successor(fnk);

				owner.addEntry(myNode.getLocalID(), entry);
				System.out.println("distributing " + filenames[i] + " to " + owner.getLocalID().hashKeytoHexString() + "\nwith ID " + entry.getHashKey().toHexString());
				/*Log.addMessage("distributing " + filenames[i] + " to " + owner.getLocalID().hashKeytoHexString() + "\nwith ID " + entry.getHashKey().toHexString() , Log.ERROR);*/
			}
			catch (RemoteException ex)
			{
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
