# Assignment 2 — Remote Method Invocation (RMI)

A client calls a `concat(String, String)` method on a remote object hosted by
an RMI server, using the RMI registry for lookup.

## Files
| File | Role |
|---|---|
| `ServerInterface.java` | Remote interface — declares `concat(String, String)` |
| `Servant.java` | Implements the remote interface (extends `UnicastRemoteObject`) |
| `Server.java` | Creates the servant and binds it to the RMI registry as `"Server"` |
| `Client.java` | Looks up `"Server"` in the registry and invokes `concat` remotely |

## How to Run
```bash
cd "Assignment 2"
javac ServerInterface.java Servant.java Server.java Client.java

# Terminal 1
rmiregistry &
java Server

# Terminal 2
java Client
```
Client prompts for the server address (use `localhost` or `127.0.0.1`) and two
strings to concatenate.

## Key Concepts
- **Stub/Skeleton** — proxy objects that make remote calls look local.
- **RMI Registry** — naming service mapping names to remote object references.
- **`Naming.rebind` / `Naming.lookup`** — publish and resolve remote objects.
- **`RemoteException`** — thrown when a remote call fails (network/marshalling issue).
