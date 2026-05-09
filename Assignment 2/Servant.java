// ============================================================
// Assignment 2: Java RMI - SERVANT (the actual implementation)
// ------------------------------------------------------------
// The "servant" is the real code that runs when the client
// calls a method. It is the SERVER's implementation of the
// remote interface.
//
// Why extend UnicastRemoteObject?
//   It auto-exports the object to the RMI runtime so it can
//   receive remote calls (handles all the networking plumbing
//   for us - sockets, marshalling, threads, etc.).
// ============================================================

import java.rmi.*;
import java.rmi.server.*;

public class Servant extends UnicastRemoteObject implements ServerInterface {

    // Constructor must throw RemoteException because the parent
    // (UnicastRemoteObject) can fail while exporting the object.
    protected Servant() throws RemoteException {
        super();
    }

    // Actual logic the client triggers remotely.
    // Even though it looks trivial, this method runs on the
    // SERVER's JVM, not the client's.
    @Override
    public String concat(String a, String b) throws RemoteException {
        return a + b;
    }
}
