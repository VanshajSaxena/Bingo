/**
 * NewGame
 */
public class NewGame {

  private Matrix matrix;

  private int currentMatrixSize;
  private int[][] currentMatrix;

  NewGame(int matrixSize) {
    this.matrix = new Matrix(matrixSize);
    this.currentMatrixSize = matrix.getSize();
    this.currentMatrix = matrix.getMatrix();
    this.matrix.populateRandomElements();

    this.matrix.printMatrix();
  }

  public void printMatrix() {
    matrix.printMatrix();
  }

  public void markANumber(int number) {
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

  public void announceANumber() {

  }

  public void receiveANumber() {

  }

  public static void main(String[] args) {

    NewGame newGame = new NewGame(5);

    newGame.markANumber(18);

    newGame.printMatrix();

  }
}
