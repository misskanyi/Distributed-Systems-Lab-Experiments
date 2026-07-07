import java.util.Scanner;

public class Bully {
    // Array to track the alive (true) or dead (false) status of 5 processes
    static boolean[] state = new boolean[5];
    int coordinator;

    // Brings a process back online and triggers an election
    public static void up(int up) {
        if (state[up - 1]) {
            System.out.println("Process " + up + " is already up");
        } else {
            int i;
            Bully.state[up - 1] = true;
            System.out.println("Process " + up + " held election");
            
            // Send election messages to all processes with a higher ID
            for (i = up; i < 5; ++i) {
                System.out.println("Election message sent from process " + up + " to process " + (i + 1));
            }
            
            // Receive "Alive" messages back from higher-ID processes
            for (i = up + 1; i <= 5; ++i) {
                if (!state[i - 1]) continue;
                System.out.println("Alive message send from process " + i + " to process " + up);
                break;
            }
        }
    }

    // Simulates a process crashing or going offline
    public static void down(int down) {
        if (!state[down - 1]) {
            System.out.println("Process " + down + " is already dowm.");
        } else {
            Bully.state[down - 1] = false;
        }
    }

    // Simulates a process trying to communicate with the coordinator
    public static void mess(int mess) {
        if (state[mess - 1]) {
            // If the default coordinator (Process 5) is alive, everything is fine
            if (state[4]) {
                System.out.println("OK");
            } else if (!state[4]) {
                // If Process 5 is down, start a new election
                int i;
                System.out.println("Process " + mess + " election");
                
                // Ping all higher-ID processes
                for (i = mess; i < 5; ++i) {
                    System.out.println("Election send from process " + mess + " to process " + (i + 1));
                }
                
                // The highest ID process that is currently alive becomes the new coordinator
                for (i = 5; i >= mess; --i) {
                    if (!state[i - 1]) continue;
                    System.out.println("Coordinator message send from process " + i + " to all");
                    break;
                }
            }
        } else {
            System.out.println("Process " + mess + " is down");
        }
    }

    public static void main(String[] args) {
        int choice;
        Scanner sc = new Scanner(System.in);
        
        // Initialize all 5 processes to be up
        for (int i = 0; i < 5; ++i) {
            Bully.state[i] = true;
        }
        
        System.out.println("5 active process are:");
        System.out.println("Process up  = p1 p2 p3 p4 p5");
        System.out.println("Process 5 is coordinator");
        
        // Interactive menu loop
        do {
            System.out.println(".........");
            System.out.println("1) Up a process.");
            System.out.println("2) Down a process");
            System.out.println("3) Send a message");
            System.out.println("4) Exit");
            choice = sc.nextInt();
            
            switch (choice) {
                case 1: {
                    System.out.println("Bring proces up");
                    int up = sc.nextInt();
                    // Process 5 is the highest, so bringing it up makes it coordinator instantly
                    if (up == 5) {
                        System.out.println("Process 5 is co-ordinator");
                        Bully.state[4] = true;
                        break;
                    }
                    Bully.up(up);
                    break;
                }
                case 2: {
                    System.out.println("Bring down any process.");
                    int down = sc.nextInt();
                    Bully.down(down);
                    break;
                }
                case 3: {
                    System.out.println("Which process will send message");
                    int mess = sc.nextInt();
                    Bully.mess(mess);
                    break;
                }
            }
        } while (choice != 4);
        
        sc.close();
    }
}