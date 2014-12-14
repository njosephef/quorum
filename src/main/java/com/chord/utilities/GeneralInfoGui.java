/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chord.utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.chord.application.Node;
import com.chord.dhash.IdKey;

/**
 *
 * @author gasparosoft
 */
public class GeneralInfoGui
{

	private Node node;

	public GeneralInfoGui(Node n)
	{

		this.node = n;

		try
		{
			List<IdKey> listSuc = node.getWholeSucList();
			List<IdKey> listFin = node.getFingerKeys();
			IdKey pred = node.getPredecessor();
			IdKey localID = node.getLocalID();
			//info for files ????

			String text;
			text = "<html>"
			       + "<h3>Node</h3>"
			       + "<ul>"
			       + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + localID.getHashKey().toHexString() + "</font>" + "</li>"
			       + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + localID.getIP().substring(1) + "</font>" + "</li>"
			       + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + localID.getPID() + "</font>" + "</li>"
			       + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + localID.getMacAndInterface()[0] + "</font>" + "</li>" +
			       "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + localID.getMacAndInterface()[1] + "</font>" + "</li>" +
			       "</ul>";
			if (pred != null)
			{
				text += "<h3>Predecessor</h3>"
				        + "<ul>"
				        + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + pred.getHashKey().toHexString() + "</font>" + "</li>"
				        + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + pred.getIP().substring(1) + "</font>" + "</li>"
				        + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + pred.getPID() + "</font>" + "</li>"
				        + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + pred.getMacAndInterface()[0] + "</font>" + "</li>" +
				        "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + pred.getMacAndInterface()[1] + "</font>" + "</li>" +
				        "</ul>";
				//"</html>";
			}

			if (listSuc != null)
			{
				text += "<h3> Successor List </h3>"
				        + "<ul>";

				for (int i = 0; i < listSuc.size(); i++)
				{
					text +=
					    "<li> Successor[" + i + "] </li>"
					    + "<ul>"
					    + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + listSuc.get(i).getHashKey().toHexString() + "</font>" + "</li>"
					    + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + listSuc.get(i).getIP().substring(1) + "</font>" + "</li>"
					    + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + listSuc.get(i).getPID() + "</font>" + "</li>"
					    + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + listSuc.get(i).getMacAndInterface()[0] + "</font>" + "</li>" +
					    "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + listSuc.get(i).getMacAndInterface()[1] + "</font>" + "</li>" +
					    "</ul>";
				}
				text += "</ul>";
			}

			if (listFin != null)
			{
				text += "<h3> Finger List </h3>"
				        + "<ul>";

				for (int i = 0; i < listFin.size(); i++)
				{
					text +=
					    "<li> Finger[" + i + "] </li>"
					    + "<ul>"
					    + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + listFin.get(i).getHashKey().toHexString() + "</font>" + "</li>"
					    + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + listFin.get(i).getIP().substring(1) + "</font>" + "</li>"
					    + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + listFin.get(i).getPID() + "</font>" + "</li>"
					    + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + listFin.get(i).getMacAndInterface()[0] + "</font>" + "</li>" +
					    "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + listFin.get(i).getMacAndInterface()[1] + "</font>" + "</li>" +
					    "</ul>";
				}
				text += "</ul>";
			}

			text += "</html>";
		}
		catch (RemoteException ex)
		{
		}

	}
}
