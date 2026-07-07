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
//connects to the server, calls the remote method, and displays the result.

import java.rmi.*; // Naming
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        try {
            Scanner s = new Scanner(System.in); // reads operator input from console

            // Step 1: Where is the server running?
            // Type "localhost" if it's on the same machine.
            System.out.println("Enter the Server address: "); // prompt
            String server = s.nextLine(); // e.g. "localhost"

            // Step 2: Ask the RMI Registry on that machine for
            // the object registered under the name "Server".
            // The URL form is:  rmi://<host>/<name-in-registry>
            // The cast to ServerInterface lets us call its methods.
            ServerInterface si = (ServerInterface) Naming.lookup("rmi://" + server + "/Server"); // resolves stub

            // Step 3: Get the two inputs we want to concatenate.
            System.out.println("Enter first string: "); // prompt
            String first = s.nextLine(); // first operand
            System.out.println("Enter second string: "); // prompt
            String second = s.nextLine(); // second operand

            // Step 4: This LOOKS like a normal method call,
            // but it actually executes on the SERVER and
            // sends the result back to us.
            System.out.println("Concatenated String: " + si.concat(first, second)); // remote invocation + result

            s.close(); // release the Scanner
        } catch (Exception e) {
            // Common causes: server not running, rmiregistry not started,
            // or wrong host name. Printing the error helps debug.
            System.out.println(e); // dump the exception for debugging
        }
    }
}
