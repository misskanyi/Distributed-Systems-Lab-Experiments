// ============================================================
// Assignment 1: Socket Programming - SERVER side
// ------------------------------------------------------------
// This program opens a TCP "door" (port 5555), waits for a
// client to connect, and then chats back and forth with it.
// Either side can type "bye" to end the conversation.
// ============================================================

import java.net.*;   // Socket, ServerSocket - the networking classes
import java.io.*;    // Streams used to send/receive text

public class MyServer {
    public static void main(String[] args) throws Exception {

        // Step 3a: Create a ServerSocket listening on port 5555.
        // Think of this as opening a "door" with a number on it,
        // so any client that knows the number can knock on it.
        ServerSocket ss = new ServerSocket(5555); // bind + listen on TCP port 5555
        System.out.println("Server is waiting for a client..."); // status message for the operator

        // Step 3b: accept() pauses the program (BLOCKS) here
        // until a client actually connects. Once a client knocks,
        // we get a Socket object representing that connection.
        Socket s = ss.accept(); // blocks until MyClient connects
        System.out.println("Client connected!"); // confirms the handshake completed

        // Step 3c: Set up the streams we need.
        //   - br      : reads what WE (the server user) type in the console
        //   - receive : reads what the CLIENT sends to us over the network
        //   - pw      : writes (sends) text back to the CLIENT
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // console input (our replies)
        InputStream istream = s.getInputStream(); // raw byte stream coming from the client socket
        BufferedReader receive = new BufferedReader(new InputStreamReader(istream)); //reads lined text from client
        OutputStream ostream = s.getOutputStream(); // raw byte stream going to the client socket
        PrintWriter pw = new PrintWriter(ostream, true); // 'true' = auto-flush after println

        String clientmessage = ""; // last line received from the client
        String servermessage = ""; // last line we typed to send back

        // Step 3d: Chat loop - keep talking until someone types "bye".
        // Server reads first, then replies. Client does the opposite.
        while (true) {
            // Read message coming FROM the client
            clientmessage = receive.readLine(); // blocks until client sends a line
            System.out.println("Client: " + clientmessage); // echo it to our console

            if (clientmessage.equals("bye")) break; // client ended the chat

            // Type our reply and send it TO the client
            System.out.print("Server: "); // prompt (no newline) for our input
            servermessage = br.readLine(); // read what the server operator types
            pw.println(servermessage); // send that line over the socket to the client

            if (servermessage.equals("bye")) break; // we ended the chat
        }

        // Step 3e: Always close sockets to free up the port.
        s.close(); // close the client connection
        ss.close(); // close the listening socket
        System.out.println("Connection Terminated"); // final status message
    }
}
