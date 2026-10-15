import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 7;[cite: 16, 17]
        int roundsWon = 0;
        boolean playAgain = true;

        System.out.println("=== Welcome to the Number Guessing Game ===");

        while (playAgain) {
            int targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attemptsLeft = maxAttempts;
            boolean hasGuessedCorrectly = false;

            System.out.println("\nI have generated a random number between " + minRange + " and " + maxRange + ".");
            System.out.println("You have " + maxAttempts + " attempts to guess it. Good luck!");

            while (attemptsLeft > 0) {
                System.out.print("Enter your guess (Attempts left: " + attemptsLeft + "): ");
                
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid number!");
                    scanner.next(); // clear invalid input
                    continue;
                }

                int userGuess = scanner.nextInt();
                
                if (userGuess == targetNumber) {
                    System.out.println("Correct! You guessed the number.");
                    hasGuessedCorrectly = true;
                    roundsWon++;
                    break;
                } else if (userGuess > targetNumber) {
                    System.out.println("Too High!");[cite: 17]
                } else {
                    System.out.println("Too Low!");[cite: 17]
                }
                
                attemptsLeft--;
            }

            if (!hasGuessedCorrectly) {
                System.out.println("You Lost! The correct number was: " + targetNumber);[cite: 16, 17]
            }

            System.out.println("Total rounds won: " + roundsWon);
            System.out.print("Do you want to play again? (yes/no): ");
            String choice = scanner.next();
            playAgain = choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y");
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }
}