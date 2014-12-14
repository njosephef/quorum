package com.chord.utilities;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.chord.application.Node;

/**
 *
 * @author gasparosoft
 */
public class PopUpMousePlugin<V, E>
{
	public PopUpMousePlugin()
	{
	}
	
	private class ShowGui implements Runnable
	{

		private int i;
		private String ip, pid;

		ShowGui(int i, String ip, String pid)
		{
			this.i = i;
			this.ip = ip;
			this.pid = pid;
		}

		public void run()
		{
			Node node = null;
			try
			{
				node = (Node) Naming.lookup("/" + ip + ":1099/" + pid);
			}
			catch (NotBoundException ex)
			{
//				Log.addMessage("PopUpMousePlugin - NBexc: Node is not bound or was unbound.", Log.ERROR);
			}
			catch (MalformedURLException ex)
			{
//				Log.addMessage("PopUpMousePlugin - MURLexc: A problem occurred. Please try again.", Log.ERROR);
			}
			catch (RemoteException ex)
			{
				Log.addMessage("PopUpMousePlugin - Rexc: A problem occurred. Please try again.", Log.ERROR);
			}

			if (node == null)
				return;

			if (i == 0)
			{
				GeneralInfoGui gGui = new GeneralInfoGui(node);

			}
			else if (i == 1)
			{
			}
			else
			{
				LogInfoGui lGui = new LogInfoGui(node);
			}

		}
	}
}
