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

import java.rmi.*;

public class Server {
    public static void main(String[] args) {
        try {
            // Create the actual remote object (our implementation).
            Servant s = new Servant();

            // Publish it in the RMI Registry under the name "Server".
            // 'rebind' means: register it; if a previous one exists, replace it.
            // The client will later use this same name to find us.
            Naming.rebind("Server", s);

            System.out.println("Java Server is Running...");
        } catch (Exception e) {
            // Print any RMI/network errors so the student can debug them.
            System.out.println(e);
        }
    }
}
