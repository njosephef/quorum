/*
 * Node.java
 *
 * Copyright by Orell F�ssli Wirtschaftsinformationen AG
 * Z�rich
 * All rights reserved.
 */
package com.chord.protocol;

import java.net.InetAddress;

/**
 * 
 *
 * @author ngvtien
 * @version $Revision:  $
 */
public interface Node
{
    /**
     * Method description
     *
     * @return NodeIdentifier
     */
    NodeIdentifier getNodeIdentifier();


    /**
     * Method description
     *
     */
    void join();


    /**
     * Method description
     *
     */
    void leave();


    /**
     * Method description
     *
     * @return InetAddress
     */
    InetAddress address();

}


/*
 * Changes:
 * $Log: $
 */