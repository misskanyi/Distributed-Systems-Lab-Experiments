# Assignment 5 — Berkeley Clock Synchronization

A Python master server averages clock differences reported by connected
clients and pushes a synchronized time back to each of them every 5 seconds
(Berkeley algorithm approximation).

## Files
- `Server.py` — listens on port 2050; per-client thread records
  `(clock_time, time_difference)`; a sync thread broadcasts `now + average_delta`
  to all clients every 5s.
- `Client.py` — connects to the server, sends its local time every 5s, and
  prints the synchronized time it receives back.

## How to Run
```bash
cd "Assignment 5"
# Terminal 1 — master
python3 Server.py

# Terminal 2+ — one or more slaves
python3 Client.py
```
No external dependencies beyond the Python standard library (`socket`,
`threading`, `datetime`, `dateutil` for parsing timestamps).

## Key Concepts
- **Berkeley algorithm** — a master polls slave clocks, averages the deltas,
  and broadcasts a corrected time (no assumption of an authoritative UTC source).
- **Physical vs logical clocks** — physical clocks track real elapsed time;
  logical clocks (e.g. Lamport) order events without needing real time.
- Alternative: Cristian's algorithm (single trusted time server), NTP
  (hierarchical, accounts for network delay).
