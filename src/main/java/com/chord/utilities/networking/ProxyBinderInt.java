package com.chord.utilities.networking;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author mariuska
 */
public interface ProxyBinderInt extends Remote {
    public void remoteBind(String prefix, Remote omg) throws RemoteException, AlreadyBoundException;
    public void remoteUnbind(String prefix) throws RemoteException, NotBoundException;
}
