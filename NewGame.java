import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * NewGame
 */
public class NewGame {

  public static void main(String[] args) {

    NewGame newGame = new NewGame(5);

    while (!newGame.hasWonGame()) {
      newGame.goOneTurn();
    }

    newGame.printMatrix();
    newGame.closeScanner();
    System.out.println("The game is over.");
  }

  private Matrix matrix;

  private int currentMatrixSize;

  private int[][] currentMatrix;

  private List<Integer> currentMatrixElementList;

  private Scanner sc = new Scanner(System.in);

  NewGame(int matrixSize) {
    this.matrix = new Matrix(matrixSize);
    this.currentMatrixSize = matrix.getSize();
    this.currentMatrix = matrix.getMatrix();
    this.matrix.populateRandomElements();
    this.currentMatrixElementList = matrix.getMatrixElementsList();
  }

  NewGame() {
    this.matrix = new Matrix();
    this.currentMatrixSize = matrix.getSize();
    this.currentMatrix = matrix.getMatrix();
    this.matrix.populateRandomElements();
    this.currentMatrixElementList = matrix.getMatrixElementsList();
  }

  private void closeScanner() {
    sc.close();
  }

  private void printMatrix() {
    matrix.printMatrix();
  }

  private void announceANumber() {
    int computedNumber = computeNumberToBeAnnounced();
    markANumber(computedNumber);
    System.out.print("\nAnnounced Number: ");
    System.out.printf("%02d", computedNumber);
    System.out.println();
  }

  private void receiveANumber() {
    boolean validInput = false;
    while (!validInput) {
      System.out.print("Enter your number: ");
      String input;
      try {
        input = sc.nextLine().trim();
      } catch (NoSuchElementException e) {
        System.out.println("No more inputs available. Exiting the game.");
        return;
      }
      if (input.isBlank()) {
        System.out.println("No input provided. Please enter a valid number.");
        continue;
      }
      int scannedNumber = 0;
      try {
        scannedNumber = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
      if (scannedNumber > currentMatrixSize || scannedNumber < 1) {
        System.out.println("The number is not available.\nPlease enter a different number.");
      } else if (!currentMatrixElementList.contains(scannedNumber)) {
        System.out.println("The number is already marked, can not mark again.\nPlease enter a different number.");
      } else {
        markANumber(scannedNumber);
        validInput = true;
      }
    }
  }

  // TODO:- This is simple and working, but a better approach is required
  // eventually.
  private void goOneTurn() {
    announceANumber();
    receiveANumber();
  }

  private boolean hasWonGame() {
    boolean scoredRequiredNoOfBingos = false;
    int numberOfBingos;

    int numberOfHorizontalBingos = checkHorizontalBingos();
    int numberOfVerticalBingos = checkVerticalBingos();
    int numberOfDiagonalBingos = checkDiagonalBingos();

    numberOfBingos = numberOfHorizontalBingos + numberOfVerticalBingos + numberOfDiagonalBingos;

    if (numberOfBingos == currentMatrix.length) {
      scoredRequiredNoOfBingos = true;
      System.out.println("Bingo! I think you lost... Try again?");
    }

    return scoredRequiredNoOfBingos;
  }

  private int checkHorizontalBingos() {
    int horizontalBingos = 0;
    for (int i = 0; i < currentMatrix.length; i++) {
      if (Arrays.stream(currentMatrix[i]).allMatch(element -> element == -1)) {
        horizontalBingos++;
      }
    }
    return horizontalBingos;
  }

  private int checkVerticalBingos() {
    int verticalBingos = 0;
    // currentMatrix[0].length gives the number of columns
    for (int j = 0; j < currentMatrix[0].length; j++) {
      boolean allMinusOne = true;
      // this loops through number of rows, which currentMatrix.length returns
      for (int i = 0; i < currentMatrix.length; i++) {
        if (currentMatrix[i][j] != -1) {
          allMinusOne = false;
          break;
        }
      }
      if (allMinusOne) {
        verticalBingos++;
      }
    }
    return verticalBingos;
  }

  private int checkDiagonalBingos() {
    int diagonalBingos = 0;
    boolean mainDiagonalBingo = true;
    boolean antiDiagonalBingo = true;
    int length = currentMatrix.length;

    for (int i = 0; i < length; i++) {
      if (currentMatrix[i][i] != -1) {
        mainDiagonalBingo = false;
      }
      if (currentMatrix[i][length - i - 1] != -1) {
        antiDiagonalBingo = false;
      }
    }
    if (mainDiagonalBingo) {
      diagonalBingos++;
    }

    if (antiDiagonalBingo) {
      diagonalBingos++;
    }
    return diagonalBingos;
  }

  private void markANumber(int number) {
    for (int i = 0; i < currentMatrix.length; i++) {
      for (int j = 0; j < currentMatrix[i].length; j++) {
        if (currentMatrix[i][j] == number) {
          currentMatrix[i][j] = -1;
          currentMatrixElementList.remove(Integer.valueOf(number));
          return;
        }
      }
    }
  }

  // TODO:- Requires a better algorithm to be used
  private int computeNumberToBeAnnounced() {
    Collections.shuffle(currentMatrixElementList);
    Iterator<Integer> iterator = currentMatrixElementList.iterator();
    int number = iterator.next();
    return number;
  }
}
