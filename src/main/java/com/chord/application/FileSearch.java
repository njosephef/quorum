
package com.chord.application;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.chord.dhash.FileNameKey;

/**
 *
 * @author haris
 */
public class FileSearch
{
	private Node thisNode;

	/**
	 * Searches for a file in the ring. Uses find_successor to get the
	 * responsible Node and then returns the Entry.
	 * @param filename
	 * @return FIDEntry of the file
	 */
	public FileEntry searchFile(String filename, Node connected)
	{
		thisNode = connected;
		FileNameKey searchKey = new FileNameKey(filename);
		Node responsibleNode = null;
		FileEntry fileEntry = null;
		try
		{
			responsibleNode = thisNode.find_successor(searchKey);
		}
		catch (RemoteException ex)
		{
			Logger.getLogger(FileSearch.class.getName()).log(Level.SEVERE, null, ex);
		}

		try
		{
			/*Request the file from the node*/
			fileEntry = responsibleNode.getEntry(searchKey);
		}
		catch (RemoteException ex)
		{
			Logger.getLogger(FileSearch.class.getName()).log(Level.SEVERE, null, ex);
		}

		return fileEntry;
	}


}