import java.util.ArrayList;
import java.util.Arrays;
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
    System.out.println("\nThe game is over, try again?");
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
    int computedNumber = getNumberToBeAnnounced();
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
        continue;
      }
      if (scannedNumber < 1 || scannedNumber > currentMatrixSize) {
        System.out.println("The number is out of range.\nPlease enter a different number.");
      } else if (!currentMatrixElementList.contains(scannedNumber)) {
        System.out.println("The number is already marked, can not mark again.\nPlease enter a different number.");
      } else {
        markANumber(scannedNumber);
        validInput = true;
      }
    }
  }

  private void goOneTurn() {
    announceANumber();
    receiveANumber();
  }

  private boolean hasWonGame() {
    int numberOfBingos;

    int numberOfHorizontalBingos = checkHorizontalBingos();
    int numberOfVerticalBingos = checkVerticalBingos();
    int numberOfDiagonalBingos = checkDiagonalBingos();

    numberOfBingos = numberOfHorizontalBingos + numberOfVerticalBingos + numberOfDiagonalBingos;

    int requiredBingos = currentMatrix.length;
    if (numberOfBingos >= requiredBingos) {
      System.out.println("\nBingo! You lost to me...");
      return true;
    }
    return false;
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

  private int getNumberToBeAnnounced() {
    int number = 0;
    double bestScore = -1;

    for (int i = 0; i < currentMatrix.length; i++) {
      for (int j = 0; j < currentMatrix[i].length; j++) {
        int currentNumber = currentMatrix[i][j];
        if (currentNumber != -1) {
          double score = evaluateNumber(i, j);
          if (score > bestScore) {
            bestScore = score;
            number = currentNumber;
          }
        }
      }
    }
    return number;
  }

  private double evaluateNumber(int row, int column) {
    double score = 1;
    ArrayList<int[]> winnableBingoLinesList = getWinnableBingoLines(row, column);

    for (int[] line : winnableBingoLinesList) {
      double currentCompleteness = getCurrentCompleteness(line);
      score += Math.pow(currentCompleteness, 2);
    }
    int lastIndex = currentMatrix.length - 1;
    if (row == 0 && column == 0 || row == 0 && column == lastIndex || row == lastIndex && column == 0
        || row == lastIndex && column == lastIndex) {
      score *= 1.2; // Bonus for corners
    }
    if (lastIndex % 2 == 0) {
      if (row == lastIndex / 2 && column == lastIndex / 2) {
        score *= 1.3; // Larger bonus for center (in odd-sized matrices)
      }
    }
    return score;
  }

  private double getCurrentCompleteness(int[] arr) {
    int[] numberOfAnnouncedNumbers = Arrays.stream(arr).filter(element -> element == -1).toArray();
    int countOfAnnouncedNumbers = numberOfAnnouncedNumbers.length;
    return countOfAnnouncedNumbers;
  }

  private ArrayList<int[]> getWinnableBingoLines(int row, int column) {
    ArrayList<int[]> winnableBingoLinesList = new ArrayList<>();

    if (row == column) {
      int[] mainDiagonalWinnableLines = new int[currentMatrix.length];
      for (int i = 0; i < currentMatrix.length; i++) {
        mainDiagonalWinnableLines[i] = currentMatrix[i][i];
      }
      winnableBingoLinesList.add(mainDiagonalWinnableLines);
    }

    if (row + column == currentMatrix.length - 1) {
      int[] antiDiagonalWinnableLines = new int[currentMatrix.length];
      for (int i = 0; i < currentMatrix.length; i++) {
        antiDiagonalWinnableLines[i] = currentMatrix[i][currentMatrix.length - i - 1];
      }
      winnableBingoLinesList.add(antiDiagonalWinnableLines);
    }

    for (int i = 0; i < currentMatrix.length; i++) {
      if (i == row) {
        winnableBingoLinesList.add(currentMatrix[i]);
      }
    }

    for (int j = 0; j < currentMatrix[0].length; j++) {
      if (j == column) {
        int[] verticalWinnableLines = new int[currentMatrix.length];
        for (int i = 0; i < currentMatrix.length; i++) {
          verticalWinnableLines[i] = currentMatrix[i][j];
        }
        winnableBingoLinesList.add(verticalWinnableLines);
      }
    }

    return winnableBingoLinesList;
  }
}
