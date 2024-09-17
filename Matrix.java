import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Matrix
 */
public class Matrix {
  int[][] matrix;
  int size;

  Matrix() {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Enter the size of your matrix: ");
      this.size = sc.nextInt();

      this.matrix = new int[size][size];
    }
  }

  Matrix(int size) {
    this.matrix = new int[size][size];
  }

  private void printMatrix() {
    for (int i = 0; i < matrix.length; i++) {
      System.out.println();
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.printf("%02d ", matrix[i][j]);
      }
    }
  }

  private void populateRandomElements() {
    List<Integer> list = new ArrayList<>();
    int listLength = size * size;

    for (int i = 1; i <= listLength; i++) {
      list.add(i);
    }

    Collections.shuffle(list);
    Iterator<Integer> iterator = list.iterator();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        matrix[i][j] = iterator.next();
      }
    }
  }

  public static void main(String[] args) {
    Matrix newMatrix = new Matrix();

    newMatrix.populateRandomElements();

    newMatrix.printMatrix();
  }
}
