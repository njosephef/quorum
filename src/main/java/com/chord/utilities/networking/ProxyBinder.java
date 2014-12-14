package com.chord.utilities.networking;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author mariuska
 */
public class ProxyBinder extends UnicastRemoteObject implements ProxyBinderInt
{

    public ProxyBinder() throws RemoteException
    {
    }

    public void remoteBind(String prefix, Remote omg) throws RemoteException, AlreadyBoundException
    {
        Registry rmiHandle = LocateRegistry.getRegistry();
        rmiHandle.bind(prefix, omg);

    }

    public void remoteUnbind(String prefix) throws RemoteException, NotBoundException
    {
        Registry rmiHandle = LocateRegistry.getRegistry();
        rmiHandle.unbind(prefix);
    }
}