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

    NewGame newGame = new NewGame(5);

    while (!newGame.hasWonGame()) {
    }

  }

  private Matrix matrix;

  private int currentMatrixSize;

  private int[][] currentMatrix;

  private List<Integer> currentMatrixElementList;

  NewGame(int matrixSize) {
    this.matrix = new Matrix(matrixSize);
    this.currentMatrixSize = matrix.getSize();
    this.currentMatrix = matrix.getMatrix();
    this.matrix.populateRandomElements();
    this.currentMatrixElementList = matrix.getMatrixElementsList();
    this.matrix.printMatrix();
  }

  public void printMatrix() {
    matrix.printMatrix();
  }

  public void announceANumber() {
    int computedNumber = computeNumberToBeAnnounced();
    markANumber(computedNumber);
    System.out.printf("%02d", computedNumber);
    System.out.println();
  }

  public void receiveANumber() {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Enter your number: ");
      int scannedNumber = sc.nextInt();

      markANumber(scannedNumber);
    }
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

    if (numberOfBingos == currentMatrixSize) {
      scoredRequiredNoOfBingos = true;
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
    for (int i = 0; i < currentMatrix.length; i++) {
      for (int j = 0; j < currentMatrix[i].length; j++) {
        if (currentMatrix[i][j] == number) {
          currentMatrix[i][j] = -1;
        }
      }
    }
  }

  private int computeNumberToBeAnnounced() {
    Collections.shuffle(currentMatrixElementList);
    Iterator<Integer> iterator = currentMatrixElementList.iterator();
    return iterator.next();
  }
}
