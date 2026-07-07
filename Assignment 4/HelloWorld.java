import mpi.*;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();    // which process am I?
        int size = MPI.COMM_WORLD.Size();    // how many processes total?

        if (rank == 0) {
            // Master process: receive from all workers
            System.out.println("Master process (rank 0) started. Total processes: " + size);
            for (int i = 1; i < size; i++) {
                char[] message = new char[20];
                MPI.COMM_WORLD.Recv(message, 0, 20, MPI.CHAR, i, 100);
                System.out.println("Received from process " + i + ": " + new String(message).trim());
            }
        } else {
            // Worker process: sendinng message to master
            char[] message = "Hello-Participants".toCharArray();
            char[] padded = new char[20];
            System.arraycopy(message, 0, padded, 0, message.length);
            MPI.COMM_WORLD.Send(padded, 0, 20, MPI.CHAR, 0, 100);
            System.out.println("Process " + rank + " sent message to master.");
        }

        MPI.Finalize();
    }
}