# Assignment 7 — Election Algorithms (Bully & Ring)

Simulates two classic leader-election algorithms for distributed systems.
Both are single-process console simulations (no real networking) that print
the message flow between simulated processes.

## Files
- `Bully.java` — Bully algorithm simulation for 5 processes (IDs 1-5, process 5
  starts as coordinator). Menu lets you bring a process up/down or trigger it
  to send an election message.
- `Ring1.java` — Ring algorithm simulation. You choose the number of processes
  and their IDs; the highest ID is elected coordinator, then you can trigger
  further election rounds.

## Build
```bash
cd "Assignment 7"
javac *.java
```

## Run
```bash
java Bully
```
Menu options: `1` bring a process up, `2` bring a process down, `3` make a
process send an election message, `4` exit.

```bash
java Ring1
```
Enter number of processes and their IDs, then choose `1` to run an election
(enter the initiating process index) or `2` to quit.

## Key concepts
- **Bully**: higher-ID processes "bully" lower ones; the highest surviving ID
  always wins and broadcasts a COORDINATOR message.
- **Ring**: election message circulates the ring collecting IDs; the highest
  ID seen is announced as coordinator once the message returns to the
  initiator.
- See `T8_Leader_Election.pdf` notes for Chang-Roberts and Hirschberg-Sinclair
  ring algorithms (message complexity O(n log n) / O(n) depending on model),
  and the impossibility of deterministic leader election in anonymous rings.
