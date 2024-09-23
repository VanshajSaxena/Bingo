import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Bingo
 */
public class Bingo {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    boolean input = false;
    do {
      System.out.println("What is of the matrix you want to play with?");
      System.out.print("Enter Size: ");
      int size = scanner.nextInt();
      NewGame newGame = new NewGame(size);

      while (!newGame.hasWonGame()) {
        newGame.goOneTurn();
      }

      newGame.printMatrix();
      System.out.println("\nThe game is over, try again?");
      System.out.print("Type y/n: ");
      char charInput;
      try {
        charInput = scanner.next().charAt(0);
      } catch (NoSuchElementException e) {
        System.out.println("Error");
        return;
      }
      if (charInput != 'y') {
        System.out.println("\nNew Game");
        input = true;
        scanner.close();
      }
    } while (!input);

  }
}
