import java.io.*;
import java.net.*;

// Main class representing a node in a token ring network
public class TokenClient1 {
    public static void main(String arg[]) throws Exception {
        InetAddress lclhost;
        BufferedReader br;
        String str = "";
        TokenClient12 tkcl, tkser;

        while (true) {
            // Initialize network instances for client (receive) and server (send) roles
            lclhost = InetAddress.getLocalHost();
            tkcl = new TokenClient12(lclhost);
            tkser = new TokenClient12(lclhost);
            
            // Configure UDP ports for communication
            tkcl.setSendPort(9004);
            tkcl.setRecPort(8002);
            lclhost = InetAddress.getLocalHost();
            tkser.setSendPort(9000);

            // Check if this node currently holds the token
            if (tkcl.hasToken == true) {
                System.out.println("Do you want to enter the Data -> YES/NO");
                br = new BufferedReader(new InputStreamReader(System.in));
                str = br.readLine();

                if (str.equalsIgnoreCase("yes")) {
                    // Send user-entered data over the network
                    System.out.println("ready to send");
                    tkser.setSendData = true;
                    tkser.sendData();
                    tkser.setSendData = false;
                } else if (str.equalsIgnoreCase("no")) {
                    // Pass the empty token along and wait to receive
                    System.out.println("i m in else");
                    tkcl.sendData();
                    tkcl.recData();
                    System.out.println("i m leaving else");
                }
            } else {
                // Wait to receive the token or data from the previous node
                System.out.println("ENTERING RECEIVING MODE...");
                tkcl.recData();
            }
        }
    }
}

// Helper class managing UDP socket communication
class TokenClient12 {
    InetAddress lclhost;
    int sendport, recport;
    boolean hasToken = true;
    boolean setSendData = false;
    TokenClient12 tkcl, tkser;

    TokenClient12(InetAddress lclhost) {
        this.lclhost = lclhost;
    }

    void setSendPort(int sendport) {
        this.sendport = sendport;
    }

    void setRecPort(int recport) {
        this.recport = recport;
    }

    void sendData() throws Exception {
        BufferedReader br;
        String str = "Token"; // Default payload is just the token
        DatagramSocket ds;
        DatagramPacket dp;

        // If user opted to send data, overwrite the default token string
        if (setSendData == true) {
            System.out.println("sending ");
            System.out.println("Enter the Data");
            br = new BufferedReader(new InputStreamReader(System.in));
            str = "ClientOne....." + br.readLine();
            System.out.println("now sending");
        }
        
        // Open socket and send the packet to the target port
        ds = new DatagramSocket(sendport);
        dp = new DatagramPacket(str.getBytes(), str.length(), lclhost, sendport - 1000);
        ds.send(dp);
        ds.close();
        
        // Reset flags after sending
        setSendData = false;
        hasToken = false; 
    }

    void recData() throws Exception {
        String msgstr;
        byte buffer[] = new byte[256];
        DatagramSocket ds;
        DatagramPacket dp;

        // Open socket and wait to receive a packet
        ds = new DatagramSocket(recport);
        dp = new DatagramPacket(buffer, buffer.length);
        ds.receive(dp);
        ds.close();

        // Decode and print the received message
        msgstr = new String(dp.getData(), 0, dp.getLength());
        System.out.println("The data is " + msgstr);

        // If the message is the token, update this node's status
        if (msgstr.equals("Token")) {
            hasToken = true;
        }
    }
}