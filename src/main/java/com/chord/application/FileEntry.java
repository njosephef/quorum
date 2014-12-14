package com.chord.application;

import java.io.Serializable;

import com.chord.dhash.BigInt;
import com.chord.dhash.BigIntImpl;
import com.chord.dhash.FileNameKey;
import com.chord.dhash.Hashing;
import com.chord.dhash.IdKey;
import com.chord.dhash.Key;

/**
 *
 * @author gasparosoft
 * @version 0.0.0.1
 * TESTING
 */
public class FileEntry implements Serializable, Comparable<Object>
{

	/**
     * 
     */
    private static final long serialVersionUID = 6427111711308032717L;
    private final Key fKey;
	private final Key idKey;
	private final String ip;
	private final int FSPort;


    /**
     * Constructor
     *
     * @param fileName
     * @param ip
     * @param pid
     * @param FSPort
     */
	public FileEntry(String fileName, String ip, int pid, int FSPort)
	{
		fKey = new FileNameKey(fileName);
		idKey = new IdKey(Hashing.hash((ip + "|" + pid).getBytes()), pid, ip);
		this.ip = ip;
		this.FSPort = FSPort;

	}


    /**
     * Method description
     *
     * @return
     */
	public Key getfKey()
	{
		return fKey;
	}


    /**
     * Method description
     *
     * @return
     */
	public int getFSPort()
	{
		return FSPort;
	}


    /**
     * Method description
     *
     * @return
     */
	public String getIp()
	{
		return ip;
	}


    /**
     * Method description
     *
     * @return
     */
	public Key getIdKey()
	{
		return idKey;
	}


    /**
     * Method description
     *
     * @return
     */
	public BigInt getHashKey()
	{
		return fKey.getHashKey();
	}


    /**
     * Method description
     *
     * @return
     */
	public BigInt getHashedIDKey()
	{
		return new BigIntImpl(idKey.getByteKey());
	}


    /**
     * Method description
     *
     * @param k
     * @return
     */
	public boolean equals(Key k)
	{
		return this.fKey.equals(k);
	}


    /**
     * Method description
     *
     * @return
     */
	public byte[] getByteKey()
	{
		BigInt hashKey = new BigIntImpl(fKey.getByteKey());
		return hashKey.getBytes();
	}


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Object o)
	{
		if (o instanceof FileEntry)
		{
			FileEntry entry = (FileEntry) o;
			return this.fKey.getHashKey().compareTo(entry.getHashKey());
		}
        return this.compareTo(o);
	}
}