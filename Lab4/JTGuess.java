import java.util.Random;
import java.util.Scanner;

/** 
 * JTGuess.class
 * @Author: JeongGyu Tak
 * @Date: 10.29.2023
 * @Class: CS&141A
 * @Assignment: LAB#4
 * @Purpose: To generate a guessing game using java.util.Random.
 */
public class JTGuess {
    final static int MAX_NUMBER = 100; // max number of guessing game

    /**
     * This program allows you to play a guessing game.
     * I will think of a number between 1 and 100 and will allow you to guess until you get it.
     * For each guess, I will tell you whether the right answer is higher or lower than your guess.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gamesPlayed = 0;
        int sumResult = 0;
        int bestResult = Integer.MAX_VALUE; // initialize with max attempt
        showIntro();
        do { // post-condition - L:34.
            gamesPlayed++;
            int result = playGame(scanner);
            if (isBetterResult(bestResult, result)) { // The lower the better.
                bestResult = result; // Update the best result.
            }
            sumResult += result;
        } while (playAgain(scanner)); // break if user does not want to play again.
        showSummary(gamesPlayed, sumResult, bestResult);
        scanner.close();
    }

    /**
     * Play a single guessing game. Returns the result of the game.
     * 
     * @param scanner java.util.Scanner. To receive user input
     * @return int total guesses user made
     */
    public static int playGame(Scanner scanner) {
        Random random = new Random();
        int guessesCount = 0;
        int answer = random.nextInt(MAX_NUMBER);
        System.out.println("I'm thinking of a number between 1 and 100...");
        do {
            guessesCount++;
            System.out.println("Your guess?");
        } while(!isEqual(answer, scanner.nextInt())); // compare the guess with answer
        System.out.printf("You got it right in %d guesses.\n", guessesCount); // show result
        return guessesCount;
    }

    /**
     * Returns True if the value is equal.
     * Also prints out if the num2 is higher or lower than num1 if they are not equal.
     * 
     * @param num1
     * @param num2
     * @return boolean 
     */
    public static boolean isEqual(int num1, int num2) {
        if (num1 == num2){
            return true;
        } else if ((num1 > num2)) {
            System.out.println("It's higher.");
        } else { // num1 < num2
            System.out.println("It's lower.");
        }
        return false;
    }

    /**
     * Prints Intro of guessing game.
     */
    public static void showIntro() {
        System.out.println("This program allows you to play a guessing game.\n"
                        + "I will think of a number between 1 and\n"
                        + "100 and will allow you to guess until\n"
                        + "you get it. For each guess, I will tell you\n"
                        + "whether the right answer is higher or lower\n"
                        + "than your guess.");

    }

    /**
     * Prints the summary of the games from given info.
     * 
     * @param gamesPlayed number of games have played.
     * @param sumResult number of total guesses that user made
     * @param bestResult result of the game with lowest guess of all time.
     */
    public static void showSummary(int gamesPlayed, int sumResult, int bestResult) {
        double avgResult = sumResult/gamesPlayed;
        System.out.println("Overall results: ");
        System.out.printf("total games: %d\n", gamesPlayed);
        System.out.printf("total guesses: %d\n", sumResult);
        System.out.printf("guesses/game: %.2f\n", avgResult);
        System.out.printf("best game: %d", bestResult);
    }

    /**
     * Ask the user if he wants to continue the game.
     * 
     * @param scanner java.util.Scanner. To receive user input
     * @return
     */
    public static boolean playAgain(Scanner scanner) {
        System.out.println("Do you want to play again? (y/n)");
        String answer = scanner.next();
        if (answer.startsWith("y") || answer.startsWith("Y")) {
            return true;
        } else if (answer.startsWith("n") || answer.startsWith("N")) {
            return false;
        } else {
            System.out.println("Please input (y/n)");
            return playAgain(scanner); // Ask again
        }
    }

    /**
     * Compare the results of two games.
     * Returns true if the result2 is better than result1.
     * The lower value for result is better on guessing game.
     * 
     * @param result1
     * @param result2
     * @return boolean
     */
    public static boolean isBetterResult(int result1, int result2) {
        if (result1 > result2) {
            return true;
        } 
        return false;
    }
}
