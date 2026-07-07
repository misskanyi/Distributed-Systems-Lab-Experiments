import java.util.Scanner;

public class Ring1 {

    public static void main(String[] args) {
        int temp, i, j;
        Rr proc[] = new Rr[10];

        // 1. Initialize process objects
        for (i = 0; i < proc.length; i++)
            proc[i] = new Rr();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of process : ");
        int num = in.nextInt();

        // 2. Gather process IDs and set their initial status
        for (i = 0; i < num; i++) {
            proc[i].index = i;
            System.out.println("Enter the id of process : ");
            proc[i].id = in.nextInt();
            proc[i].state = "active";
            proc[i].f = 0; // 'f' is a flag used to track if a process has participated in the election
        }

        // 3. Sort processes by ID in ascending order
        // This helps easily identify the highest ID for the initial coordinator
        for (i = 0; i < num - 1; i++) {
            for (j = 0; j < num - 1; j++) {
                if (proc[j].id > proc[j + 1].id) {
                    temp = proc[j].id;
                    proc[j].id = proc[j + 1].id;
                    proc[j + 1].id = temp;
                }
            }
        }

        // Print the sorted process IDs
        for (i = 0; i < num; i++) {
            System.out.print(" [" + i + "]" + " " + proc[i].id);
        }

        int init;
        int ch;
        int temp1;
        int temp2;
        int arr[] = new int[10];

        // 4. Default Coordinator Setup
        // Set the highest ID process (last in the sorted array) as the initial coordinator and simulate a crash ("inactive")
        proc[num - 1].state = "inactive";
        System.out.println("\n process " + proc[num - 1].id + " select as co-ordinator");

        // 5. Interactive Election Loop
        while (true) {
            System.out.println("\n 1.election 2.quit ");
            ch = in.nextInt();

            // Reset all participation flags to 0 for a fresh election
            for (i = 0; i < num; i++) {
                proc[i].f = 0;
            }

            switch (ch) {
                case 1:
                    System.out.println("\n Enter the Process number who initialsied election : ");
                    init = in.nextInt(); // This is the INDEX, not the ID
                    if (init < 0 || init >= num) {
                        System.out.println("\n invalid process number, must be between 0 and " + (num - 1) + "\n");
                        break;
                    }
                    
                    temp2 = init;      // temp2 tracks the original initiator
                    temp1 = init + 1;  // temp1 looks at the next process in the ring
                    i = 0;

                    // Pass the election message around the ring
                    while (temp2 != temp1) {
                        // If the next process is active and hasn't been visited yet
                        if ("active".equals(proc[temp1].state) && proc[temp1].f == 0) {
                            System.out.println("\nProcess " + proc[init].id + " send message to " + proc[temp1].id);
                            proc[temp1].f = 1;       // Mark as visited
                            init = temp1;            // Update current sender
                            arr[i] = proc[temp1].id; // Add this ID to the active list
                            i++;
                        }
                        
                        // Move to the next process, wrapping around to 0 if at the end of the array
                        if (temp1 == num) {
                            temp1 = 0;
                        } else {
                            temp1++;
                        }
                    }

                    // Complete the ring by passing the final list back to the initiator
                    System.out.println("\nProcess " + proc[init].id + " send message to " + proc[temp1].id);
                    arr[i] = proc[temp1].id;
                    i++;
                    
                    // Find the maximum ID among all active processes that participated
                    int max = -1;
                    for (j = 0; j < i; j++) {
                        if (max < arr[j]) {
                            max = arr[j];
                        }
                    }

                    System.out.println("\n process " + max + " select as co-ordinator");

                    // Set the newly elected coordinator to inactive (simulating it taking charge)
                    for (i = 0; i < num; i++) {
                        if (proc[i].id == max) {
                            proc[i].state = "inactive";
                        }
                    }
                    break;
                case 2:
                    System.out.println("Program terminated ...");
                    sc_close(in);
                    return;
                default:
                    System.out.println("\n invalid response \n");
                    break;
            }
        }
    }

    private static void sc_close(Scanner in) {
        in.close();
    }
}

// Class representing a single process node in the ring
class Rr {
    public int index; // Array position
    public int id;    // Actual Process ID
    public int f;     // Flag to check if process was visited in the current election loop
    String state;     // "active" or "inactive"
}