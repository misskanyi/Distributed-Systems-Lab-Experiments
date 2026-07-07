// ============================================================
// Assignment 2: Java RMI - SERVER bootstrap
// ------------------------------------------------------------
// This program:
//   1. Creates the Servant object (the real implementation).
//   2. Registers it with the RMI Registry under the name "Server"
//      so that any client can look it up by that name.
//
// Before running this, you must start the RMI registry:
//      rmiregistry &
// ============================================================

import java.rmi.*; // Naming

public class Server {
    public static void main(String[] args) {
        try {
            // Create the actual remote object (our implementation).
            Servant s = new Servant(); // constructing this also exports it via UnicastRemoteObject

            // Publish it in the RMI Registry under the name "Server".
            // 'rebind' means: register it; if a previous one exists, replace it.
            // The client will later use this same name to find us.
            Naming.rebind("Server", s); // requires `rmiregistry` already running on this host

            System.out.println("Java Server is Running..."); // stays alive because the RMI runtime keeps a listener thread
        } catch (Exception e) {
            // Print any RMI/network errors so the student can debug them.
            System.out.println(e); // e.g. registry not running, port in use
        }
    }
}
