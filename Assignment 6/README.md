# Assignment 6 — Mutual Exclusion (Token Ring)

Token ring based mutual exclusion using UDP `DatagramSocket`s. A token circulates
between two client processes; whoever holds the token may enter the critical
section (send data), then passes the token along.

## Files
- `TokenServer1.java` — listens on port 8000, prints any message received (simple sink/monitor).
- `TokenClient1.java` — first ring node (send port 9004, receive port 8002).
- `TokenClient2.java` — second ring node (send port 9002, receive port 8004).

## Build
```bash
cd "Assignment 6"
javac *.java
```

## Run
Open three terminals:
```bash
# Terminal 1
java TokenServer1

# Terminal 2
java TokenClient1

# Terminal 3
java TokenClient2
```
`TokenClient1` starts holding the token. Type `yes` to send data (enter a message
when prompted) or `no` to just pass the token on without entering the critical
section. The token alternates between `TokenClient1` and `TokenClient2`.

## Key concepts
- Race condition, deadlock, starvation, mutual exclusion (see FAQ in lab manual).
- Token-based mutual exclusion: only the token holder may access the shared resource.
