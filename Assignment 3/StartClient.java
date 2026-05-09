// ============================================================
// Assignment 3: CORBA - CLIENT
// ------------------------------------------------------------
// What this does:
//   1. Initialize the ORB.
//   2. Look up the remote "Calculator" object in the Naming
//      Service (registered earlier by StartServer).
//   3. Read commands from the user like "+ 10 20"  and call
//      calcObj.calculate(...) on the SERVER over the network.
//   4. Type "exit" to ask the server to shut down.
//
// Run with the same naming-service port as the server:
//      java StartClient -ORBInitialPort 1050
// ============================================================

import Calculator.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import java.util.*;

public class StartClient {
    // Reference to the remote calculator object (lives on the server).
    private static Calc calcObj;

    public static void main(String[] args) {
        try {
            // 1. Initialize the ORB.
            ORB orb = ORB.init(args, null);

            // 2. Find the Naming Service, then ask it for the
            //    object registered under the name "Calculator".
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            calcObj = (Calc) CalcHelper.narrow(ncRef.resolve_str("Calculator"));

            // 3. REPL: read user input, send the request to the
            //    server, and print the result.
            while (true) {
                System.out.println("\nEnter: [operator] [operand1] [operand2]  e.g: + 1 2");
                Scanner c = new Scanner(System.in);
                String input = c.nextLine();

                // Type "exit" to gracefully stop both client and server.
                // calcObj.exit() is a 'oneway' call - it returns immediately.
                if (input.toLowerCase().equals("exit")) {
                    calcObj.exit();
                    break;
                }

                // Expecting input in the form: <op> <num1> <num2>
                String[] params = input.split(" ");
                if (params.length != 3) {
                    System.out.println("Wrong number of parameters. Try again...");
                    continue;
                }

                // Map text operator to the integer code the server expects.
                // (This must match what CalcObject.calculate() understands.)
                int operatorCode;
                if      (params[0].equals("+")) operatorCode = 1;
                else if (params[0].equals("-")) operatorCode = 2;
                else if (params[0].equals("*")) operatorCode = 3;
                else if (params[0].equals("/")) operatorCode = 4;
                else { System.out.println("Unknown operator."); continue; }

                int op1 = Integer.parseInt(params[1]);
                int op2 = Integer.parseInt(params[2]);

                // Catch divide-by-zero on the client side so we don't
                // crash the server with an arithmetic exception.
                if (operatorCode == 4 && op2 == 0) {
                    System.out.println("Can't divide by zero.");
                    continue;
                }

                // This call goes over the network: the actual math
                // runs on the SERVER and returns a result here.
                int result = calcObj.calculate(operatorCode, op1, op2);
                System.out.println("Result: " + result);
            }
        } catch (Exception e) {
            // Likely causes: server not running, naming service not
            // running, or wrong -ORBInitialPort value.
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}
