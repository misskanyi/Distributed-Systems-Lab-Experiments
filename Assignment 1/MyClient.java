// ============================================================
// Assignment 1: Socket Programming - CLIENT side
// ------------------------------------------------------------
// This program connects to a server running on the same
// machine (127.0.0.1) on port 5555 and chats with it.
// IMPORTANT: Run MyServer FIRST, then run MyClient.
// ============================================================

import java.net.*;   // Socket - used to dial in to the server
import java.io.*;    // Streams used to send/receive text

public class MyClient {
    public static void main(String[] args) throws Exception {

        // Step 4a: Connect ("dial in") to the server.
        //   "127.0.0.1" = localhost = this same computer
        //   5555        = the port number the server is listening on
        Socket s = new Socket("127.0.0.1", 5555);
        System.out.println("Connected to Server! Type your message and hit Enter.");

        // Step 4b: Set up the streams we need.
        //   - br      : reads what WE (the client user) type in the console
        //   - pw      : writes (sends) text TO the server
        //   - receive : reads text the SERVER sends back to us
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = s.getOutputStream();
        PrintWriter pw = new PrintWriter(ostream, true); // 'true' = auto-flush
        InputStream istream = s.getInputStream();
        BufferedReader receive = new BufferedReader(new InputStreamReader(istream));

        String clientmessage = "";
        String servermessage = "";

        // Step 4c: Chat loop - the client speaks first, then waits
        // for the server's reply. Stops when either side types "bye".
        while (true) {
            // Type our message and send it TO the server
            System.out.print("Client: ");
            clientmessage = br.readLine();
            pw.println(clientmessage);

            if (clientmessage.equals("bye")) break;

            // Read the server's reply
            servermessage = receive.readLine();
            System.out.println("Server: " + servermessage);

            if (servermessage.equals("bye")) break;
        }

        // Step 4d: Close the socket to release network resources.
        s.close();
        System.out.println("Connection Terminated");
    }
}
