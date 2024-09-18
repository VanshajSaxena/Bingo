import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Matrix
 */
public class Matrix {
  private int[][] matrix;
  private int size;

  public int[][] getMatrix() {
    return matrix;
  }

  public int getSize() {
    return size * size;
  }

  Matrix() {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Enter the size of your matrix: ");
      this.size = sc.nextInt();

      this.matrix = new int[size][size];
    }
  }

  Matrix(int size) {
    this.matrix = new int[size][size];
    this.size = size;
  }

  public void printMatrix() {
    for (int i = 0; i < matrix.length; i++) {
      System.out.println();
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] == -1) {
          System.out.print("❌ ");
        } else {
          System.out.printf("%02d ", matrix[i][j]);
        }
      }
    }
    System.out.println();
  }

  // The below method uses a List because using a
  // Set to generate and populate random elements was
  // causing the elements to be populated sequentially.
  public void populateRandomElements() {
    List<Integer> list = getMatrixElementsList();

    Collections.shuffle(list);
    Iterator<Integer> iterator = list.iterator();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        matrix[i][j] = iterator.next();
      }
    }
  }

  public List<Integer> getMatrixElementsList() {
    List<Integer> list = new ArrayList<>();
    int listLength = size * size;

    for (int i = 1; i <= listLength; i++) {
      list.add(i);
    }
    return list;
  }

}
