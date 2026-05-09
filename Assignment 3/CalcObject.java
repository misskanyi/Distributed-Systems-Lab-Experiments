// ============================================================
// Assignment 3: CORBA - SERVANT (the actual implementation)
// ------------------------------------------------------------
// CalcPOA is auto-generated from Calculator.idl. By extending
// it, this class becomes a valid CORBA "servant" - the real
// object that runs whenever a client calls a method.
//
// We also keep a reference to the ORB so we can shut the
// server down when the client calls exit().
// ============================================================

import org.omg.CORBA.ORB;
import Calculator.CalcPOA;

public class CalcObject extends CalcPOA {
    private ORB orb;   // kept so we can shutdown later from exit()

    // Called once by StartServer to give us access to the ORB.
    public void setORB(ORB orb) {
        this.orb = orb;
    }

    // Performs the calculation requested by the client.
    // We use 'long' internally to detect overflow before
    // squeezing the result back into an int.
    @Override
    public int calculate(int type, int a, int b) {
        long result;
        if (type == 1)      result = (long) a + b;   // 1 = add
        else if (type == 2) result = (long) a - b;   // 2 = subtract
        else if (type == 3) result = (long) a * b;   // 3 = multiply
        else                result = (long) a / b;   // 4 = divide

        // Clamp the result to int range to avoid overflow surprises.
        if (result >= Integer.MAX_VALUE) return Integer.MAX_VALUE;
        else if (result <= Integer.MIN_VALUE) return Integer.MIN_VALUE;
        else return (int) result;
    }

    // 'oneway' method from the IDL - the client does NOT wait.
    // We tell the ORB to stop running, which ends the server.
    @Override
    public void exit() {
        orb.shutdown(false);
    }
}
