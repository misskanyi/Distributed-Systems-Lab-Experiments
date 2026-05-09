// ============================================================
// Assignment 2: Java RMI - REMOTE INTERFACE
// ------------------------------------------------------------
// In RMI (Remote Method Invocation), the client and server
// MUST agree on a "contract" - a list of methods the client is
// allowed to call on the server. That contract is this file.
//
// Rules for any RMI remote interface:
//   1. It must extend java.rmi.Remote
//   2. Every method must declare 'throws RemoteException'
//      (because anything sent over the network can fail)
// ============================================================

import java.rmi.*;

public interface ServerInterface extends Remote {
    // Concatenate two strings on the SERVER and return the result.
    // Even though it looks like a normal Java call, it actually
    // travels across the network when the client invokes it.
    String concat(String a, String b) throws RemoteException;
}
