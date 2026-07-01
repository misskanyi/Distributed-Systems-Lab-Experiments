# Assignment 4 — MPI Hello World

Rank-0 (master) receives a "Hello-Participants" message from every other
process in `MPI_COMM_WORLD`, demonstrating basic point-to-point message
passing.

## Files
- `HelloWorld.java` — every non-zero rank sends a message to rank 0, which
  receives and prints one message per rank.

## Requirements
MPJ Express (or equivalent MPI-for-Java library) installed, with `MPJ_HOME` set.

## How to Run
```bash
cd "Assignment 4"
javac -cp $MPJ_HOME/lib/mpj.jar HelloWorld.java
mpirun -np 4 java -cp .:$MPJ_HOME/lib/mpj.jar HelloWorld
```
`-np 4` spawns 4 processes (1 master + 3 workers) — adjust as needed.

## Key Concepts
- **`MPI_Init` / `MPI_Finalize`** — bracket every MPI program.
- **Rank** — unique ID (0..n-1) MPI assigns each process in a communicator.
- **`MPI_Send` / `MPI_Recv`** — point-to-point message passing primitives.
- **`MPI_COMM_WORLD`** — default communicator containing all processes.
