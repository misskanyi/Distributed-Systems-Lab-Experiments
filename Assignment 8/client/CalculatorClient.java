import com.learn.ws.client.Calculator;
import com.learn.ws.client.CalculatorService;

// Generate the com.learn.ws.client stubs first with:
//   wsimport -keep -p com.learn.ws.client http://localhost:8080/ws/calculator?wsdl
// Server (Assignment 8/src/com/learn/ws/Server.java) must be running before generating stubs.
public class CalculatorClient {
	public static void main(String[] args) {
		CalculatorService service = new CalculatorService();
		Calculator calculator = service.getCalculatorPort();

		System.out.println("add(5, 3) = " + calculator.add(5, 3));
		System.out.println("subtract(5, 3) = " + calculator.subtract(5, 3));
		System.out.println("multiply(5, 3) = " + calculator.multiply(5, 3));
		System.out.println("divide(6, 3) = " + calculator.divide(6, 3));
	}
}
