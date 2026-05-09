// ============================================================
// Assignment 3: CORBA - SERVER bootstrap
// ------------------------------------------------------------
// Steps performed here:
//   1. Initialize the ORB (Object Request Broker) - the CORBA
//      "engine" that routes calls between client and server.
//   2. Get the RootPOA (Portable Object Adapter) - it manages
//      our servant objects (creating, activating them, etc.).
//   3. Create our CalcObject servant and convert it into a
//      CORBA reference clients can use.
//   4. Register that reference in the Naming Service under
//      the name "Calculator" so clients can look it up.
//   5. Call orb.run() so the server keeps listening.
//
// Before running this, the Naming Service must be started:
//      orbd -ORBInitialPort 1050 &
// And run the server with:
//      java StartServer -ORBInitialPort 1050
// ============================================================

import Calculator.Calc;
import Calculator.CalcHelper;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class StartServer {
    public static void main(String args[]) {
        try {
            // 1. Start up CORBA's ORB (handles all networking).
            ORB orb = ORB.init(args, null);

            // 2. Get the RootPOA and activate its manager so it
            //    can begin handling incoming requests.
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // 3. Create the actual servant object and give it
            //    access to the ORB (needed for shutdown).
            CalcObject calcObj = new CalcObject();
            calcObj.setORB(orb);

            // Convert the servant into a CORBA object reference.
            // 'narrow' is CORBA's safe down-cast.
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calcObj);
            Calc href = CalcHelper.narrow(ref);

            // 4. Get the Naming Service (the "yellow pages" of CORBA)
            //    and register our reference under the name "Calculator".
            org.omg.CORBA.Object nsRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(nsRef);
            NameComponent path[] = ncRef.to_name("Calculator");
            ncRef.rebind(path, href); // 'rebind' replaces any existing entry

            System.out.println("CalculatorServer is listening...");

            // 5. Block here forever, processing client requests
            //    until exit() (oneway) is called and shuts us down.
            orb.run();
        } catch (Exception e) {
            // If anything goes wrong (naming service not running,
            // wrong port, etc.) print details so it's easy to debug.
            System.err.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
