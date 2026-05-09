// ============================================================
// Assignment 2: Java RMI - CLIENT
// ------------------------------------------------------------
// The client looks up the remote object in the RMI Registry,
// then calls a method on it AS IF it were a normal local
// object. RMI hides all the network details.
//
// Flow:
//   1. Ask user for the server address (e.g. "localhost").
//   2. Look up the remote object by its registered name.
//   3. Call concat(...) on it - this call goes over the network.
//   4. Print the result returned by the server.
// ============================================================

import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        try {
            Scanner s = new Scanner(System.in);

            // Step 1: Where is the server running?
            // Type "localhost" if it's on the same machine.
            System.out.println("Enter the Server address: ");
            String server = s.nextLine();

            // Step 2: Ask the RMI Registry on that machine for
            // the object registered under the name "Server".
            // The URL form is:  rmi://<host>/<name-in-registry>
            // The cast to ServerInterface lets us call its methods.
            ServerInterface si = (ServerInterface) Naming.lookup("rmi://" + server + "/Server");

            // Step 3: Get the two inputs we want to concatenate.
            System.out.println("Enter first string: ");
            String first = s.nextLine();
            System.out.println("Enter second string: ");
            String second = s.nextLine();

            // Step 4: This LOOKS like a normal method call,
            // but it actually executes on the SERVER and
            // sends the result back to us.
            System.out.println("Concatenated String: " + si.concat(first, second));

            s.close();
        } catch (Exception e) {
            // Common causes: server not running, rmiregistry not started,
            // or wrong host name. Printing the error helps debug.
            System.out.println(e);
        }
    }
}
