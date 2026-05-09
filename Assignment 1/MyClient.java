import java.net.*;
import java.io.*;

public class MyClient {
    public static void main(String[] args) throws Exception {

        // Step 4a: Connect to server at localhost (127.0.0.1) on port 5555
        Socket s = new Socket("127.0.0.1", 5555);
        System.out.println("Connected to Server! Type your message and hit Enter.");

        // Step 4b: Set up streams
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = s.getOutputStream();
        PrintWriter pw = new PrintWriter(ostream, true);
        InputStream istream = s.getInputStream();
        BufferedReader receive = new BufferedReader(new InputStreamReader(istream));

        String clientmessage = "";
        String servermessage = "";

        // Step 4c: Keep chatting until someone types "bye"
        while (true) {
            System.out.print("Client: ");
            clientmessage = br.readLine();
            pw.println(clientmessage);

            if (clientmessage.equals("bye")) break;

            servermessage = receive.readLine();
            System.out.println("Server: " + servermessage);

            if (servermessage.equals("bye")) break;
        }

        // Step 4d: Close everything
        s.close();
        System.out.println("Connection Terminated");
    }
}