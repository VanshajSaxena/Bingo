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

  private boolean hasWonGame() {
    boolean numberOfBingoEqualsFive = false;
    int numberOfBingos = 0;
    int numberOfDiagonalBingos;
    int numberOfVerticalBingos;
    int numberOfHorizontalBingos;

    checkDiagonalBingos();
    checkVerticalBingos();
    checkHorizontalBingos();

    return numberOfBingoEqualsFive;
  }

  private void checkVerticalBingos() {

  }

  private static void checkDiagonalBingos() {

  }

  private static void checkHorizontalBingos() {

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
