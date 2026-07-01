# Socket Calculator (Java TCP Socket Application)

A modern client-server calculator application developed in Java using **TCP sockets**, **multithreading**, and **Java Swing**. The application allows multiple clients to connect to a central server and perform arithmetic calculations remotely.

## Overview

This project demonstrates the implementation of a distributed calculator system using Java networking concepts. A dedicated server accepts calculation requests from multiple clients simultaneously, evaluates mathematical expressions, and returns the results over a TCP connection.

The client application features a modern calculator-inspired graphical user interface built with Java Swing, while the server handles expression evaluation and client management.

---

## Features

### Client

* Modern calculator-style Swing GUI
* Real-time communication with the server
* Sends mathematical expressions as strings
* Displays computed results returned by the server
* Can be packaged and distributed as a JAR file

### Server

* TCP socket-based communication
* Supports multiple simultaneous client connections
* Multithreaded architecture (one thread per client)
* Evaluates complete arithmetic expressions
* Does not require spaces between operators and operands
* Supports operator precedence

### Expression Support

The server accepts expressions such as:

```text
5+2
10-3*2
4840+120/30
100/5+25*2
12.5*4
```

Supported operators:

```text
+
-
*
/
```

---

## Technologies Used

* Java
* Java Swing
* TCP Sockets
* Multithreading
* Object-Oriented Programming

---

## Project Structure

### Server Project

```text
SocketCalculatorServer
│
└── src
    │
    ├── CalculatorServer.java
    ├── ClientHandler.java
    └── ExpressionEvaluator.java
```

### Client Project

```text
SocketCalculatorClient
│
└── src
    │
    ├── CalculatorClient.java
    ├── RoundedButton.java
    └── SocketManager.java
```

---

## System Architecture

```text
+------------------+
|  Client #1       |
|  Swing GUI       |
+--------+---------+
         |
         |
         v
+------------------+
|                  |
| Calculator       |
| Server           |
| TCP Socket       |
| Multithreaded    |
|                  |
+------------------+
         ^
         |
         |
+--------+---------+
|  Client #2       |
|  Swing GUI       |
+------------------+
```

Each client establishes its own TCP connection with the server. The server creates a dedicated thread for every connected client, allowing multiple users to perform calculations simultaneously.

---

## How It Works

### Step 1: Start the Server

Run:

```bash
java CalculatorServer
```

The server starts listening for incoming connections on the configured port.

Example:

```text
Calculator Server running on port 5000
```

---

### Step 2: Start a Client

Run the Swing client application.

The client connects to the server using:

```java
Server IP Address
Port Number
```

Example:

```text
192.168.1.10
5000
```

---

### Step 3: Enter an Expression

Example:

```text
4840+120/30
```

The client sends:

```text
4840+120/30
```

to the server.

---

### Step 4: Server Computes the Result

The server evaluates:

```text
4840 + (120 / 30)
```

and obtains:

```text
4844
```

---

### Step 5: Result Returned

The client receives:

```text
4844
```

and displays it in the calculator interface.

---

## Multithreading Implementation

The server supports multiple clients using Java threads.

For every new connection:

```java
Socket client = serverSocket.accept();
new Thread(new ClientHandler(client)).start();
```

This ensures that one client does not block another client's requests.

---

## Example Session

### Client Request

```text
25*4+10
```

### Server Processing

```text
(25 × 4) + 10
```

### Server Response

```text
110
```

### Displayed Result

```text
110
```

---

## Running on Multiple Computers

### Server Computer

Run:

```bash
java CalculatorServer
```

Find the machine's local IP address.

Example:

```text
192.168.1.10
```

---

### Client Computers

Update the server address:

```java
private static final String SERVER_IP = "192.168.1.10";
```

Compile the client:

```bash
javac *.java
```

Create a JAR:

```bash
jar cfe CalculatorClient.jar CalculatorClient *.class
```

Run:

```bash
java -jar CalculatorClient.jar
```

Multiple client machines can connect to the same server simultaneously.

---

## Educational Objectives

This project demonstrates:

* TCP Client-Server Architecture
* Socket Programming
* Multithreading
* Concurrent Client Handling
* Java Swing GUI Development
* Expression Parsing
* Software Architecture and Separation of Concerns

---

## Future Improvements

* Parentheses support
* Calculation history
* User authentication
* Server activity dashboard
* Secure communication using SSL/TLS
* JavaFX version of the client
* Scientific calculator functions

---

## Author

Developed as an academic project to demonstrate networking, concurrency, and GUI development concepts in Java.
