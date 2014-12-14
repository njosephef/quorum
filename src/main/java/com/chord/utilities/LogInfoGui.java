/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chord.utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.chord.application.Node;

/**
 *
 * @author gasparosoft
 */
public class LogInfoGui
{
	private Node node;

	public LogInfoGui(Node n)
	{
		this.node = n;
		String[] log;
		try
		{
			log = node.getWholeLog();
			String[] info = log[0].split("\n");
			String[] tables = log[1].split("\n");
			String[] warnings = log[2].split("\n");
			String[] errors = log[3].split("\n");
			int i;
			String text = "<html>"
			              + "<h3> Information </h3>"
			              + "<ul>";

			for (i = 0; i < info.length; i++)
			{
				text += "<li>" + info[i] + "</li>";
			}
			text += "</ul>"
			        + "<h3>Tables</h3>"
			        + "<ul>";
			for (i = 0; i < tables.length; i++)
			{
				text += "<li>" + tables[i] + "</li>";
			}
			text += "</ul>"
			        + "<h3>Warnings</h3>"
			        + "<ul>";
			for (i = 0; i < warnings.length; i++)
			{
				text += "<li>" + warnings[i] + "</li>";
			}
			text += "</ul>"
			        + "<h3>Errors</h3>"
			        + "<ul>";
			for (i = 0; i < errors.length; i++)
			{
				text += "<li>" + errors[i] + "</li>";
			}
			text += "</ul>"
			        + "</html>";
		}
		catch (RemoteException ex)
		{
		}
	}
}
