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
      System.out.println("What size of the matrix you want to play with?");
      boolean isValidSize = false;
      int size = 0;
      while (!isValidSize) {
        String stringSize;
        System.out.print("Enter Size: ");
        try {
          stringSize = scanner.next().trim();
        } catch (NoSuchFieldError e) {
          return;
        }
        if (stringSize.isBlank()) {
          System.out.println("No input provided. Please enter a valid number.");
          continue;
        }
        try {
          size = Integer.valueOf(stringSize);
          isValidSize = true;
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a valid number.");
          continue;
        }
      }
      NewGame newGame = new NewGame(size);

      while (!newGame.hasWonGame()) {
        newGame.goOneTurn();
      }

      newGame.printMatrix();
      System.out.println("\nThe game is over, try again?");
      System.out.print("Type y/n: ");
      char charInput;
      try {
        charInput = scanner.next().toLowerCase().charAt(0);
      } catch (NoSuchElementException e) {
        return;
      }
      if (charInput != 'y') {
        input = true;
        scanner.close();
      }
    } while (!input);
  }
}
