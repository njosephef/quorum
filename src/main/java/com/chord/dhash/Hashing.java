package com.chord.dhash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author gasparosoft
 * @version 0.0.0.1
 */
public final class Hashing
{

	private static String DEFAULT_METHOD =  "SHA-1";
	private static String hashMethod;
	private static MessageDigest md;

	public static void setMethod(String hashMethod)
	{
		Hashing.hashMethod = hashMethod;
	}

	public static BigInt hash(byte[] key)
	{
		// SHA - 1 implementation
		if (hashMethod == null)
		{
			hashMethod = DEFAULT_METHOD;
		}

		try
		{
			md = MessageDigest.getInstance(hashMethod);
		}
		catch (NoSuchAlgorithmException ex)
		{
		}
		// ???? synchronized(Hashing.class) {
		md.reset();
		md.update(key);

		return new BigIntImpl(md.digest());
		//}
	}
}
