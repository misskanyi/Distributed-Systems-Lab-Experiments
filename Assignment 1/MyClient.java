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
        Socket s = new Socket("127.0.0.1", 5555); // opens TCP connection to MyServer
        System.out.println("Connected to Server! Type your message and hit Enter."); // status message

        // Step 4b: Set up the streams we need.
        //   - br      : reads what WE (the client user) type in the console
        //   - pw      : writes (sends) text TO the server
        //   - receive : reads text the SERVER sends back to us
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // console input (our messages)
        OutputStream ostream = s.getOutputStream(); // raw byte stream going to the server socket
        PrintWriter pw = new PrintWriter(ostream, true); // 'true' = auto-flush
        InputStream istream = s.getInputStream(); // raw byte stream coming from the server socket
        BufferedReader receive = new BufferedReader(new InputStreamReader(istream)); // reads lined text from server

        String clientmessage = ""; // last line we typed to send
        String servermessage = ""; // last line received from the server

        // Step 4c: Chat loop - the client speaks first, then waits
        // for the server's reply. Stops when either side types "bye".
        while (true) {
            // Type our message and send it TO the server
            System.out.print("Client: "); // prompt (no newline) for our input
            clientmessage = br.readLine(); // read what the client operator types
            pw.println(clientmessage); // send that line over the socket to the server

            if (clientmessage.equals("bye")) break; // we ended the chat

            // Read the server's reply
            servermessage = receive.readLine(); // blocks until server sends a line
            System.out.println("Server: " + servermessage); // echo it to our console

            if (servermessage.equals("bye")) break; // server ended the chat
        }

        // Step 4d: Close the socket to release network resources.
        s.close(); // close the connection to the server
        System.out.println("Connection Terminated"); // final status message
    }
}
