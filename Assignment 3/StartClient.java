import Calculator.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import java.util.*;

public class StartClient {
    private static Calc calcObj;

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            calcObj = (Calc) CalcHelper.narrow(ncRef.resolve_str("Calculator"));

            while (true) {
                System.out.println("\nEnter: [operator] [operand1] [operand2]  e.g: + 1 2");
                Scanner c = new Scanner(System.in);
                String input = c.nextLine();

                if (input.toLowerCase().equals("exit")) {
                    calcObj.exit();
                    break;
                }

                String[] params = input.split(" ");
                if (params.length != 3) {
                    System.out.println("Wrong number of parameters. Try again...");
                    continue;
                }

                int operatorCode;
                if      (params[0].equals("+")) operatorCode = 1;
                else if (params[0].equals("-")) operatorCode = 2;
                else if (params[0].equals("*")) operatorCode = 3;
                else if (params[0].equals("/")) operatorCode = 4;
                else { System.out.println("Unknown operator."); continue; }

                int op1 = Integer.parseInt(params[1]);
                int op2 = Integer.parseInt(params[2]);

                if (operatorCode == 4 && op2 == 0) {
                    System.out.println("Can't divide by zero.");
                    continue;
                }

                int result = calcObj.calculate(operatorCode, op1, op2);
                System.out.println("Result: " + result);
            }
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}