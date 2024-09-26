# Bingo Game

A simple console-based Bingo game implemented in Java. Players take turns marking numbers on a matrix, and the goal is to complete n lines (either horizontally, vertically, or diagonally) before the opponent, where n is the length of the matrix.

## Features

- Play with a customizable matrix size.
- Interactive gameplay with user inputs.
- Computer automatically marks numbers and evaluates the game status.
- Win condition: A complete line of marked numbers (horizontal, vertical, or diagonal).
  
## How to Play

1. After starting the game, you will be asked to enter the size of the matrix.
2. The game will randomly populate the matrix with numbers.
3. You will take turns entering a number to mark in the matrix.
4. The game will also announce numbers to mark.
5. The game continues until one player completes enough lines (Bingos) to win.
6. After each game, you'll be asked if you want to play again.

## Installation

### Prerequisites

- Java 8 or above installed on your system.

### Steps to Compile and Run

1. **Clone the repository** or download the source code:
   ```bash
   git clone https://github.com/your-username/BingoGame.git
   cd BingoGame
   ```

2. **Compile the program**:
   ```bash
   javac src/*.java
   ```

3. **Run the game**:
   ```bash
   java -cp src Bingo
   ```

4. If you prefer to package it as a JAR file, follow these steps:

   - Compile the Java files:
     ```bash
     javac -d bin src/*.java
     ```
   - Package the program into a JAR file:
     ```bash
     jar cfm BingoGame.jar manifest.txt -C bin .
     ```
   - Run the JAR file:
     ```bash
     java -jar BingoGame.jar
     ```

## Example Gameplay

```
What size of the matrix you want to play with?
Enter Size: 5

 01  02  03  04  05
 06  07  08  09  10
 11  12  13  14  15
 16  17  18  19  20
 21  22  23  24  25

Enter your number: 3
Announced Number: 07
```

## Code Structure

- `Bingo.java`: The main class that initiates the game loop and handles user input.
- `NewGame.java`: Handles the game logic, including turns, marking numbers, and checking for a winning condition.
- `Matrix.java`: Manages the game board, generating numbers and printing the matrix.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

