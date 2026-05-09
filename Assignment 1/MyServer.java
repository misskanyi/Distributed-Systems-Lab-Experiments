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
        ServerSocket ss = new ServerSocket(5555);
        System.out.println("Server is waiting for a client...");

        // Step 3b: accept() pauses the program (BLOCKS) here
        // until a client actually connects. Once a client knocks,
        // we get a Socket object representing that connection.
        Socket s = ss.accept();
        System.out.println("Client connected!");

        // Step 3c: Set up the streams we need.
        //   - br      : reads what WE (the server user) type in the console
        //   - receive : reads what the CLIENT sends to us over the network
        //   - pw      : writes (sends) text back to the CLIENT
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InputStream istream = s.getInputStream();
        BufferedReader receive = new BufferedReader(new InputStreamReader(istream)); //reads lined text from client
        OutputStream ostream = s.getOutputStream();
        PrintWriter pw = new PrintWriter(ostream, true); // 'true' = auto-flush after println

        String clientmessage = "";
        String servermessage = "";

        // Step 3d: Chat loop - keep talking until someone types "bye".
        // Server reads first, then replies. Client does the opposite.
        while (true) {
            // Read message coming FROM the client
            clientmessage = receive.readLine();
            System.out.println("Client: " + clientmessage);

            if (clientmessage.equals("bye")) break;

            // Type our reply and send it TO the client
            System.out.print("Server: ");
            servermessage = br.readLine();
            pw.println(servermessage);

            if (servermessage.equals("bye")) break;
        }

        // Step 3e: Always close sockets to free up the port.
        s.close();
        ss.close();
        System.out.println("Connection Terminated");
    }
}
