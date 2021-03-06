package com.chord.application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chord.dhash.FileNameKey;
import com.chord.dhash.IdKey;
import com.chord.dhash.Key;
import com.chord.exceptions.AlreadyConnectedException;
import com.chord.exceptions.IDNotFoundException;

/**
 *
 * @author gasparosoft
 */
public interface Node extends Remote
{

	public void create() throws AlreadyConnectedException, IDNotFoundException, RemoteException;

	public void notifyNode(Node possiblePredecessor) throws RemoteException;

	public void join(Node connectedNode) throws RemoteException;

	public Node find_successor(Key id) throws RemoteException;

	public IdKey find_successor_ID(Key id) throws RemoteException;

	public void disconnect() throws RemoteException;

	public IdKey getLocalID() throws RemoteException;

	java.rmi.registry.Registry getRMIHandle() throws RemoteException;

	public boolean isConnected() throws RemoteException;

	public void addSuccessor(IdKey id, int index) throws RemoteException;

	public IdKey getSuccessor(IdKey idKey) throws RemoteException;

	public void addEntries(IdKey idKey, Set<FileEntry> entries) throws RemoteException;

	public boolean isAlive() throws RemoteException;

	public IdKey getPredecessor() throws RemoteException;

	public IdKey getImmediateSuccessor() throws RemoteException;

	public void setPredecessor(IdKey idKey) throws RemoteException;

	public IdKey[] getFingers() throws RemoteException;

	public IdKey getFinger(int index) throws RemoteException;

	public void setFinger(IdKey key, int index) throws RemoteException;

	public Map<IdKey, Set<FileEntry>> giveEntries(IdKey predecessorID) throws RemoteException;

	public void setEntries(Map<IdKey, Set<FileEntry>> mEntries) throws RemoteException;

	public void removeSuccessor(int index) throws RemoteException;

	public List<IdKey> copySuccessorsList() throws RemoteException;

	public void setSuccessorsList(List<IdKey> newList) throws RemoteException;

	public void checkIntegrityOfSucList() throws RemoteException;

	public IdKey getSucList(int index) throws RemoteException;

	public void restartThreads() throws RemoteException;

	public List<IdKey> getWholeSucList() throws RemoteException;

	public Map<IdKey, Set<FileEntry>> getAllEntries() throws RemoteException;

	public void setConnected(boolean connected) throws RemoteException;

	public int getFingerSize() throws RemoteException;

	public int getSucListSize() throws RemoteException;

	public void addEntry(IdKey idKey, FileEntry entry) throws RemoteException;

	public void removeFinger(IdKey key) throws RemoteException;

	public void removeFinger(int index) throws RemoteException;

	public List<FingerEntry> getFingerEntries() throws RemoteException;

	public boolean existsInEntries(FileEntry fid, IdKey id) throws RemoteException;

	public void removeEntrySet(IdKey id) throws RemoteException;

	public FileEntry getEntry(IdKey owner, FileNameKey fkey) throws RemoteException;

	public FileEntry getEntry(FileNameKey fkey) throws RemoteException;

	public void clearSuccessorList() throws RemoteException;

	public List<IdKey> getFingerKeys() throws RemoteException;

	public String[] getWholeLog() throws RemoteException;

	public void clearFingers() throws RemoteException;

	public int getNumberOfEntries() throws RemoteException;

	public int getNumberOfFiles() throws RemoteException;

	public NodeProperties getProperties() throws RemoteException;

	public void getNodeStatistics() throws RemoteException;

	public void getRingStatistics() throws RemoteException;

	public long getTime() throws RemoteException;

	public int getHops() throws RemoteException;

	public int getNumSearches() throws RemoteException;

	public void addTime(long time) throws RemoteException;

	public void addHops(int hops) throws RemoteException;
}
