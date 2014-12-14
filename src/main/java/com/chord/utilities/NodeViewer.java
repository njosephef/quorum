/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chord.utilities;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chord.application.Node;
import com.chord.dhash.IdKey;

/**
 *
 * @author gasparosoft
 */
public class NodeViewer
{
	private Node node;
	
	public NodeViewer(Node node) throws RemoteException
	{
		System.out.println("********************* Node Viewer - " + node.getLocalID().toString());
		this.node = node;
		update();
//        File file = fileChooser.getSelectedFile();
//        NodeViewer.this.writeImage(new File(file.getAbsolutePath() + ".jpg"));
        
//		updateButton.addActionListener(new ActionListener()
//		update();
	}

	private void createGraph() throws RemoteException
	{
		createNodes(false);
	}

	private void update() throws RemoteException
	{
		createNodes(true);
	}

	private void createNodes(boolean remove) throws RemoteException
	{
		Map<String, String> map = new HashMap<String, String>();
		IdKey[] fingers = node.getFingers();
		IdKey pred = node.getPredecessor();
		IdKey suc = node.getImmediateSuccessor();

		List<String> listVertices = new ArrayList<String>();
		List<String> listFingers = new ArrayList<String>();
		listVertices.add(node.getLocalID().toString());

		map.put(node.getLocalID().toString(), node.getLocalID().hashKeytoHexString());

		if (suc == null && pred == null)
		{
			return;
		}

		if (suc != null)
		{
			listVertices.add(suc.toString());
			map.put(suc.toString(), suc.hashKeytoHexString());
		}
		if (fingers != null)
		{
			for (int i = 0; i < fingers.length; i++)
			{
				if (fingers[i] != null)
				{
					listFingers.add(fingers[i].toString());
					map.put(fingers[i].toString(), fingers[i].hashKeytoHexString());
				}
			}
		}

		if (pred != null)
		{
			listVertices.add(pred.toString());
			map.put(pred.toString(), pred.hashKeytoHexString());
		}
	}
}