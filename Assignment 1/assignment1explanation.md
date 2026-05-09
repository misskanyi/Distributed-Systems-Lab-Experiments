# Assignment 1 – Socket Programming

## What It Does
A two-way chat application where a **Client** and **Server** exchange messages over a TCP connection using Java Sockets.

---

## How It Works

1. **Server** starts and listens on **port 5555** for an incoming connection
2. **Client** connects to the server using IP `127.0.0.1` (localhost) and port `5555`
3. Once connected, both sides communicate through **Input/Output streams**
4. Either side types `bye` to close the connection

```
Client  ──── message ────►  Server
Client  ◄─── reply   ────   Server
```

---

## Files
| File | Role |
|---|---|
| `MyServer.java` | Opens a port, waits for client, reads and replies to messages |
| `MyClient.java` | Connects to server, sends messages, displays server replies |

---

## How to Run

```bash
#cd to folder
cd "Assignment 1"
# 1. Compile
javac MyServer.java MyClient.java

# 2. Terminal 1 — run server first
java MyServer

# 3. Terminal 2 — then run client
java MyClient
```

> ⚠️ Always start the Server before the Client.

---

## Key Concepts
- **Socket** — endpoint for network communication between two machines
- **TCP** — reliable, connection-based protocol used here
- **Port** — number that identifies the service (5555 in this case)
- **Streams** — used to send (`PrintWriter`) and receive (`BufferedReader`) messages