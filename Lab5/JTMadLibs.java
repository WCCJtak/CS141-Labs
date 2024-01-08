import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/** 
 * MadLibs.class
 * @Author: JeongGyu Tak, Sadie Raihl
 * @Date: 11.07.2023
 * @Class: CS&141A
 * @Assignment: LAB#5
 * @Purpose: To read and write files and handle exceptions
 */
public class JTMadLibs {
    // COMMANDS LIST
    final static char COMMAND_CREATE = 'C';
    final static char COMMAND_VIEW = 'V';
    final static char COMMAND_QUIT = 'Q';
    
    // PLACEHOLDER INDICATORS
    final static char PLACEHOLDER_STARTS = '<';
    final static char PLACEHOLDER_ENDS = '>';
    final static char PLACEHOLDER_SPACE = '-';

    /**
     * Refer to help() @L:46
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        help();
        while (true){
            char command = inputCommand(scanner);
            if (command == Character.toUpperCase(COMMAND_CREATE) ||
                command == Character.toLowerCase(COMMAND_CREATE)) {
                create(scanner);
            } else if (command == Character.toUpperCase(COMMAND_VIEW) ||
                    command == Character.toLowerCase(COMMAND_VIEW)) {
                view(scanner);
            } else if (command == Character.toUpperCase(COMMAND_QUIT) ||
                    command == Character.toLowerCase(COMMAND_QUIT)) {
                break;
            } else {
                continue;
            }
        }
        scanner.close();
    }

    /**
     * Reveals functionality of this application
     */
    public static void help() {
        System.out.println("Welcome to the game of Mad Libs.");
        System.out.println("I will ask you to provide various words");
        System.out.println("and phrases to fill in a story.");
        System.out.println("The result will be written to an output file.");
    }

    /**
     * Requests an input from the user and returns first character of what they've input.
     * 
     * @param scanner
     * @return char
     */
    public static char inputCommand(Scanner scanner) {
        System.out.printf("\n(%c)reate mad-lib, (%c)iew mad-lib, (%c)uit?.", 
                        COMMAND_CREATE, 
                        COMMAND_VIEW, 
                        COMMAND_QUIT);
        return scanner.nextLine().charAt(0);
    }
    
    /**
     * Requests a file name from a user and returns its file
     * 
     * @param scanner
     * @return File
     */
    public static File inputFile(Scanner scanner) {
        System.out.print("Input file name: ");
        String file = scanner.nextLine();
        return new File(file);
    }

    /**
     * create MadLibs
     * 
     * @param scanner
     */
    public static void create(Scanner scanner) {
        Scanner fileReader;
        File file = inputFile(scanner);
        String output = "";
        try {
            fileReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Try again: " +  file.getName());
            create(scanner); // recursive
            return;
        }

        // Replace the place holders and store in output.
        while (fileReader.hasNextLine()) {
            output += replacePlaceholders(fileReader.nextLine(), scanner);
        }
        fileReader.close();

        // Overwrite the file with text placeholders are replaced
        try {
            FileWriter f2 = new FileWriter(file, false);
            f2.write(output);
            f2.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }    
        System.out.println("Your mad-lib has been created!");
    }

    /**
     * Replace placeholders from a line.
     * 
     * @param line
     * @param scanner
     * @return
     */
    public static String replacePlaceholders(String line, Scanner scanner) {
        String replacedLine = "";
        for (String word : line.split(" ")) {
            if (isPlaceholder(word)) {
                replacedLine += replacePlaceholder(word, scanner);
            } else {
                replacedLine += word;
            }
            replacedLine += " ";
        }
        return replacedLine;
    }

    /**
     * Replace the given placeholder into user input value
     * 
     * @param placeholder
     * @param scanner
     * @return String
     */
    public static String replacePlaceholder(String placeholder, Scanner scanner) {
        placeholder = placeholder.substring(placeholder.indexOf(PLACEHOLDER_STARTS) + 1);
        placeholder = placeholder.substring(0, placeholder.indexOf(PLACEHOLDER_ENDS));
        System.out.printf("Please type an %s: ", placeholder.replace(PLACEHOLDER_SPACE, ' '));
        return scanner.nextLine();
    }

    /**
     * Check if the word is a place holder.
     * 
     * @param word
     * @return boolean
     */
    public static boolean isPlaceholder(String word) {
        if (word.charAt(0) == PLACEHOLDER_STARTS && word.charAt(word.length()-1) == PLACEHOLDER_ENDS){
            return true;
        }
        return false;
    }

    /**
     * View a text file.
     * 
     * @param scanner
     */
    public static void view(Scanner scanner) {
        Scanner fileReader;
        File file = inputFile(scanner);
        try {
            fileReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Try again: " +  file.getName());
            view(scanner); // recursive
            return;
        }
        while (fileReader.hasNextLine()) {
            System.out.println(fileReader.nextLine());
        }
        fileReader.close();
    }
}
