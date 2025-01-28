# Network Average Calculator (NAC)

## Application Description

The **Network Average Calculator (NAC)** is a Java-based application that implements a distributed system for calculating the average of numbers. The application operates in two modes: **master** and **slave**, depending on the system state and the provided parameters.

### Master Mode
In **master** mode, the application listens on a specified UDP port, receives messages from other instances of the application (in **slave** mode), stores the numeric values, and then calculates and broadcasts the average value to all nodes in the network.

### Slave Mode
In **slave** mode, the application sends its numeric value to the instance running in **master** mode and then terminates.

## How to Run the Application

The application is run from the command line with two parameters:

```bash
java NAC <port> <number>
```
- `<port>`: The UDP port number for communication.
- `<number>`: The integer value to be included in the average calculation.

## Requirements

- **Java 8 (JDK 1.8)** or higher
- Basic understanding of UDP and network communication.

## How to Compile and Run

### Compile the project:
Navigate to the directory containing your `.java` files and run:

```bash
javac NAC.java
```
This will compile the main class (`NAC.java`) and any other dependent classes automatically. The compiled `.class` files will be placed in the same directory as the source files.

### Example Usage

#### Master Mode Example:
```bash
java NAC 5000 10
```
This will run the application in master mode, opening port 5000 and using the value 10 for the average calculation

#### Slave Mode Example:
```bash
java NAC 5000 5
```
This sends the value 5 to the master instance running on port 5000 and then terminates.
