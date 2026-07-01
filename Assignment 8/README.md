# Assignment 8 — Web Services (JAX-WS SOAP)

A simple `Calculator` SOAP web service published with JAX-WS (`javax.jws` /
`javax.xml.ws`), plus a client that consumes it via generated stubs.

> **Note:** `javax.jws`/`javax.xml.ws` were removed from the JDK in Java 11+
> (Java EE modules). This needs **JDK 8**, or JDK 11+ with the JAX-WS RI jars
> (`javax.xml.ws-api`, `jaxws-rt`) on the classpath — same as the lab manual's
> NetBeans + GlassFish setup.

## Files
- `src/com/learn/ws/Calculator.java` — the web service (`add`, `subtract`,
  `multiply`, `divide`).
- `src/com/learn/ws/Server.java` — publishes the service via `Endpoint.publish`
  (no app server needed).
- `client/CalculatorClient.java` — consumes the service through generated
  `wsimport` stubs.

## Build & Run (JDK 8)

1. Compile and start the server:
   ```bash
   cd "Assignment 8"
   javac -d out src/com/learn/ws/*.java
   java -cp out com.learn.ws.Server
   ```
   This publishes the service at `http://localhost:8080/ws/calculator` and the
   WSDL at `http://localhost:8080/ws/calculator?wsdl`.

2. In another terminal, generate the client stubs from the running service:
   ```bash
   wsimport -keep -p com.learn.ws.client -d client-out \
     http://localhost:8080/ws/calculator?wsdl
   ```

3. Compile and run the client:
   ```bash
   javac -cp client-out -d client-out client-out/com/learn/ws/client/*.java client/CalculatorClient.java
   java -cp client-out:. CalculatorClient
   ```

Alternative (no coding, quick check): open
`http://localhost:8080/ws/calculator?Tester` in a browser (or the WSDL URL) to
invoke methods directly — see the "Method Invocation" screenshots in the lab
manual for the expected output shape.

Video walkthrough referenced for this assignment: https://youtu.be/0z-HvSfr-M4

## Key concepts
- SOAP vs REST, WSDL, UDDI, service provider/requestor/registry roles.
- `@WebService` / `@WebMethod` / `@WebParam` annotations drive WSDL generation.
- `wsimport` turns a WSDL into client-side stub + helper classes so the client
  calls remote methods like local ones.
