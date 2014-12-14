package com.chord.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.chord.application.Node;
import com.chord.dhash.BigInt;
import com.chord.dhash.IdKey;

/**
 *
 * @author gasparosoft
 */
public class ChordViewer
{

	private Node node;
	
	private static List<IdKey> nodesKey = new ArrayList<IdKey>();

	public ChordViewer(Node node) throws RemoteException
	{
//		super("Chord Ring Viewer - " + node.getLocalID().toString());

//	    File file = fileChooser.getSelectedFile();
		this.node = node;
	}

	private void writeImage(File file)
	{
		String s = file.getName();
	}

	public void create()
	{
		Map<String, Integer> map = new TreeMap<String, Integer>();
		IdKey key;
		Node nd;
		nd = node;
		
		int i = 0;
		try
		{

			BigInt bg = nd.getLocalID().getHashKey().powerOfTwo(0);
			IdKey id = new IdKey(bg, 0, "low");

			id = nd.find_successor_ID(id);
			try
			{
				node = (Node) Naming.lookup("/" + id.getIP() + ":1099/" + String.valueOf(id.getPID()));
			}
			catch (MalformedURLException ex)
			{
			}
			key = id;

			while (true)
			{
				nodesKey.add(key);
				map.put(key.hashKeytoHexString(), i);
//				graph.addVertex(Integer.toString(i));
				i++;

				key = node.getImmediateSuccessor();
				try
				{
					// node = (Node) nd.getRMIHandle().lookup(id.toString());
					node = (Node) Naming.lookup("/" + key.getIP() + ":1099/" + String.valueOf(key.getPID()));
				}
				catch (MalformedURLException ex)
				{
				}

				if (key.equals(id))
				{
					break;
				}
			}
		}
		catch (RemoteException re)
		{
		}
		catch (NotBoundException nb)
		{
		}

//		list = new ArrayList<String>();
		/*for (int j = 0; j < i; j++)
		{
			list.add(Integer.toString(j));
		}*/

		Map<String, String> nMap = new HashMap<String, String>();
		Set<String> set = map.keySet();
		Object[] array = set.toArray();

		for (i = 0; i < map.size(); i++)
		{
			nMap.put(Integer.toString(i), (String) array[i]);
		}
	}

	public static IdKey getNodeKey(int index)
	{
		return nodesKey.get(index);
	}
}