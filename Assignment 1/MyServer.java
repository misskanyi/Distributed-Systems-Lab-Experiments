import java.net.*;
import java.io.*;

public class MyServer {
    public static void main(String[] args) throws Exception {

        // Step 3a: Create a ServerSocket listening on port 5555
        ServerSocket ss = new ServerSocket(5555);
        System.out.println("Server is waiting for a client...");

        // Step 3b: Wait for client to connect (this line BLOCKS until a client connects)
        Socket s = ss.accept();
        System.out.println("Client connected!");

        // Step 3c: Set up streams to read FROM and write TO the client
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InputStream istream = s.getInputStream();
        BufferedReader receive = new BufferedReader(new InputStreamReader(istream));
        OutputStream ostream = s.getOutputStream();
        PrintWriter pw = new PrintWriter(ostream, true);

        String clientmessage = "";
        String servermessage = "";

        // Step 3d: Keep chatting until someone types "bye"
        while (true) {
            clientmessage = receive.readLine();
            System.out.println("Client: " + clientmessage);

            if (clientmessage.equals("bye")) break;

            System.out.print("Server: ");
            servermessage = br.readLine();
            pw.println(servermessage);

            if (servermessage.equals("bye")) break;
        }

        // Step 3e: Close everything
        s.close();
        ss.close();
        System.out.println("Connection Terminated");
    }
}