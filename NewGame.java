import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * NewGame
 */
public class NewGame {

  public static void main(String[] args) {

    NewGame newGame = new NewGame();

    while (!newGame.hasWonGame()) {
      newGame.goOneTurn();
    }

    newGame.closeScanner();
    System.out.println("The game is over.");
  }

  private Matrix matrix;

  private int currentMatrixSize;

  private int[][] currentMatrix;

  private List<Integer> currentMatrixElementList;

  private Scanner sc;

  NewGame(int matrixSize) {
    sc = new Scanner(System.in);
    this.matrix = new Matrix(matrixSize);
    this.currentMatrixSize = matrix.getSize();
    this.currentMatrix = matrix.getMatrix();
    this.matrix.populateRandomElements();
    this.currentMatrixElementList = matrix.getMatrixElementsList();
    this.matrix.printMatrix();
  }

  NewGame() {
    sc = new Scanner(System.in);
    this.matrix = new Matrix();
    this.currentMatrixSize = matrix.getSize();
    this.currentMatrix = matrix.getMatrix();
    this.matrix.populateRandomElements();
    this.currentMatrixElementList = matrix.getMatrixElementsList();
    this.matrix.printMatrix();
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
    System.out.print("Enter your number: ");
    if (sc.hasNext()) {
      String nextLine = sc.nextLine();
      int scannedNumber = Integer.parseInt(nextLine);
      markANumber(scannedNumber);
    }
  }

  private void goOneTurn() {
    announceANumber();
    receiveANumber();
    printMatrix();
  }

  private boolean hasWonGame() {
    boolean scoredRequiredNoOfBingos = false;
    int numberOfBingos = 0;
    int numberOfHorizontalBingos;
    int numberOfVerticalBingos;
    int numberOfDiagonalBingos;

    numberOfHorizontalBingos = checkHorizontalBingos();
    numberOfVerticalBingos = checkVerticalBingos();
    numberOfDiagonalBingos = checkDiagonalBingos();

    numberOfBingos = numberOfHorizontalBingos + numberOfVerticalBingos + numberOfDiagonalBingos;

    if (numberOfBingos == currentMatrix.length) {
      scoredRequiredNoOfBingos = true;
      System.out.println("You won the game! Congratulations...");
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
    if (number > currentMatrixSize) {
      throw new IllegalArgumentException("The number provided is beyond the size of the matrix.");
    }
    boolean numberExists = false;
    for (int i = 0; i < currentMatrix.length; i++) {
      for (int j = 0; j < currentMatrix[i].length; j++) {
        if (currentMatrix[i][j] == number) {
          currentMatrix[i][j] = -1;
          numberExists = true;
        }
      }
    }
    if (!numberExists) {
      System.out.println("Please provide a different number.");
    }
  }

  private int computeNumberToBeAnnounced() {
    Collections.shuffle(currentMatrixElementList);
    Iterator<Integer> iterator = currentMatrixElementList.iterator();
    int number = iterator.next();
    currentMatrixElementList.remove(Integer.valueOf(number));
    return number;
  }
}
