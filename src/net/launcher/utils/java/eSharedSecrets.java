/*
 * Copyright (c) 2002, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.launcher.utils.java;

import sun.misc.SharedSecrets;

/**
 * A repository of "shared secrets", which are a mechanism for calling
 * implementation-private methods in another package without using reflection. A
 * package-private class implements a public interface and provides the ability
 * to call package-private methods within that package; the object implementing
 * that interface is provided through a third package to which access is
 * restricted. This framework avoids the primary disadvantage of using
 * reflection for this purpose, namely the loss of compile-time checking.
 */
public class eSharedSecrets extends SharedSecrets {

    private static eJavaNetAccess javaNetAccess;
    
    public static void seteJavaNetAccess(eJavaNetAccess jna)
    {
        javaNetAccess = jna;
    }

    public static eJavaNetAccess geteJavaNetAccess()
    {
        return javaNetAccess;
    }
}
