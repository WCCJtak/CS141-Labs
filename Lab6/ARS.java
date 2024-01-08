import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/** 
 * ARS.class
 * @Author: JeongGyu Tak
 * @Date: 11.20.2023
 * @Class: CS&141A
 * @Assignment: LAB#6
 */
public class ARS {
    // COMMANDS LIST
    final static char CMD_VIEW = 'V';
    final static char CMD_ADD = 'A';
    final static char CMD_REMOVE = 'R';
    final static char CMD_MODIFY = 'M';
    final static char CMD_QUIT = 'Q';
    
    // SEAT CATEGORY
    final static char CLS_FIRST = 'F';
    final static char CLS_BUSINESS = 'B';
    final static char CLS_ECONOMY = 'E';

    final static int MAX_PASSENGERS = 50;
    
    public static void main(String[] args) throws FileNotFoundException {
        File file;
        Scanner fileReader;
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[MAX_PASSENGERS];
        int[] seatNos = new int[MAX_PASSENGERS];
        char[] seatClasses = new char[MAX_PASSENGERS];

        help();

        // Read passneger information
        while (true) {
            // Get the file with passengers
            file = inputFile(scanner);
            try {
                fileReader = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Try again: " +  file.getName());
                continue;
            }
            
            // Get information of each passengers
            int index = 0;
            while (fileReader.haxsNextLine()) {
                if (index >= MAX_PASSENGERS) {
                    System.out.printf("Exceeded the max number of passengers: %d\n", 
                                    MAX_PASSENGERS);
                    break;
                }
                String row = fileReader.nextLine();
                names[index] = parseName(row);
                seatNos[index] = parseSeatNo(row);
                seatClasses[index] = parseSeatClass(row);
                index++;
            }
            fileReader.close(); // close the file to avoid conflict
            break;
        }

        // Process command
        while(true) {
            char command = inputCommand(scanner);
            if (command == Character.toUpperCase(CMD_VIEW) ||
                command == Character.toLowerCase(CMD_VIEW)) {
                view(file);
            } else if (command == Character.toUpperCase(CMD_ADD) ||
                    command == Character.toLowerCase(CMD_ADD)) {
                add(scanner, file, names, seatNos, seatClasses);
            } else if (command == Character.toUpperCase(CMD_REMOVE) ||
                    command == Character.toLowerCase(CMD_REMOVE)) {
                remove(scanner, file, names, seatNos, seatClasses);
            } else if (command == Character.toUpperCase(CMD_MODIFY) ||
                    command == Character.toLowerCase(CMD_MODIFY)) {
                modify(scanner, file, names, seatNos, seatClasses);
            } else if (command == Character.toUpperCase(CMD_QUIT) ||
                    command == Character.toLowerCase(CMD_QUIT)) {
                break;
            } else {
                System.out.println("Please input a valid command.");
                continue;
            }
        }
        scanner.close();
    }

    /**
     * Modify a passenger info from the file
     * 
     * @param scanner
     * @param file
     * @param names
     * @param seatNos
     * @param seatClasses
    */
    public static void modify(Scanner scanner, File file, String[] names, int[] seatNos, char[] seatClasses) {
        String name = getName(scanner);
        int index = 0;
        while(true) {
            if (names[index] == null) {
                System.out.printf("No such passenger named %s. Please try it again.\n", name);
                modify(scanner, file, names, seatNos, seatClasses); // recursive
            } else if (names[index].equals(name)) {
                break;
            } 
            index++;
        }
        System.out.printf("Passenger Info\n%-8s%s\n%-8s%d\n%-8s%c\n", 
                        "Name:",
                        names[index],
                        "SeatNo:",
                        seatNos[index],
                        "Class:",
                        seatClasses[index]);
        names[index] = getName(scanner);
        seatNos[index] = getSeatNo(scanner);
        seatClasses[index] = getSeatClass(scanner);
        overwrite(file, names, seatNos, seatClasses);
    }

    /**
     * Remove a new passenger from the file
     * 
     * @param scanner
     * @param file
     * @param names
     * @param seatNos
     * @param seatClasses
     */
    public static void remove(Scanner scanner, File file, String[] names, int[] seatNos, char[] seatClasses) {
        String name = getName(scanner);
        int index = 0;
        while(true) {
            if (names[index].equals(name)) {
                break;
            } else if (names[index] == null) {
                System.out.printf("No such passenger named %s. Please try it again.\n", name);
                remove(scanner, file, names, seatNos, seatClasses); // recursive
            }
            index++;
        }
        // terminate data
        names[index] = null;
        seatNos[index] = 0;
        seatClasses[index] = 0;
        overwrite(file, names, seatNos, seatClasses);
    }

    /**
     * Add a new passenger into the file
     * 
     * @param Scanner scanner
     * @param File file
     * @param String[] names
     * @param int[] seatNos
     * @param char[] seatClasses
     */
    public static void add(Scanner scanner, File file, String[] names, int[] seatNos, char[] seatClasses) {
        int index = 0;
        while(true) {
            if (names[index] == null) {
                break;
            }
            index++;
        }
        if (index >= MAX_PASSENGERS) {
            System.out.println("Max passengers reached. Please remove a passenger first");
            return;
        }
        // get a new data
        names[index] = getName(scanner);
        seatNos[index] = getSeatNo(scanner);
        seatClasses[index] = getSeatClass(scanner);
        overwrite(file, names, seatNos, seatClasses);
    }
    
    /**
     * overwrites the passenger information into the file
     * 
     * @param file
     * @param names
     * @param seatNos
     * @param seatClasses
     */
    public static void overwrite(File file, String[] names, int[] seatNos, char[] seatClasses) {
        String output = "";
        for(int i=0; i<MAX_PASSENGERS; i++) {
            if (names[i] == null) {
                // has to be continue in case we have null in the middle of a row
                // happens in remove()
                continue;
            }
            output += names[i];
            output += " ";
            output += seatNos[i];
            output += " ";
            output += seatClasses[i];
            output += "\n";
        }
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(output);
            fileWriter.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    /**
     * Get name from input 
     * 
     * @param Scanner scanner
     * @return
     */
    public static String getName(Scanner scanner) {
        System.out.println("Please input the passenger name.");
        String name = scanner.nextLine();
        if (name.equals("")) { // if there is no value
            return getName(scanner); // recursive
        }
        return name;
    }

    /**
     * Get seat number from input
     * 
     * @param scanner
     * @return
     */
    public static int getSeatNo(Scanner scanner) {
        System.out.println("Please input the seat number.");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please input valid number");
            return getSeatNo(scanner); // recursive
        }
    }

    /**
     * Get class of a seat from input
     * 
     * @param scanner
     * @return
     */
    public static char getSeatClass(Scanner scanner) {
        System.out.printf("Please input the class of the seat. (%c)irst, (%c)usiness, (%c)conomy",
                        CLS_FIRST,
                        CLS_BUSINESS,
                        CLS_ECONOMY);
        char seatClass = scanner.nextLine().charAt(0);
        switch (seatClass) {
            case CLS_FIRST:
            case CLS_BUSINESS:
            case CLS_ECONOMY:
                return seatClass;
            default:
                System.out.println("Please input the valid class.");
                return getSeatClass(scanner); //recursive
        }
    }

    /**
     * Reveals functionality of this application
     */
    public static void help() {
        System.out.println("This program will allow you to view the file, "
                            + "add passengers, and remove passengers, " 
                            + "and modifying the passenger information.");
    }

    /**
     * Requests an input from the user and returns first character of what they've input.
     * 
     * @param Scanner scanner
     * @return char
     */
    public static char inputCommand(Scanner scanner) {
        System.out.printf("(%c)view all passengers, \n", CMD_VIEW);
        System.out.printf("(%c)dd a passenger, \n", CMD_ADD);
        System.out.printf("(%c)emove a passenger, \n", CMD_REMOVE);
        System.out.printf("(%c)odify a passenger, \n", CMD_MODIFY);
        System.out.printf("(%c)uit\n", CMD_QUIT);
        return scanner.nextLine().charAt(0);
    }
    
    /**
     * Requests a file name from a user and returns its file
     * 
     * @param Scanner scanner
     * @return File
     */
    public static File inputFile(Scanner scanner) {
        System.out.print("Input file name: ");
        String file = scanner.nextLine();
        return new File(file);
    }

    /**
     * Get the name of a passenger from the row
     * First column is the name
     * 
     * @param String row
     * @return
     */
    public static String parseName(String row) {
        return row.split(" ")[0];
    }

    /**
     * Get the seat number of a passenger from the row
     * Second column is the seat number
     * 
     * @param String row
     * @return
     */
    public static int parseSeatNo(String row) {
        return Integer.parseInt(row.split(" ")[1]);

    }

    /**
     * Get the class of a seat of a passenger from the row
     * Third column is the class of the seat
     * 
     * @param String row
     * @return
     */
    public static char parseSeatClass(String row) {
        char seatClass = row.split(" ")[2].charAt(0);
        switch (seatClass) {
            case CLS_FIRST:
            case CLS_BUSINESS:
            case CLS_ECONOMY:
                return seatClass;
            default:
                System.out.printf("Warning: Seat class '%c' is not a valid class\n", seatClass);
                System.out.printf("Please check the row with following infomation: [%s]\n", row);
                return seatClass;
        }
    }

    /**
     * Read only
     * 
     * @param File file
     * @throws FileNotFoundException
     */
    public static void view(File file) throws FileNotFoundException {
        Scanner fileReader;
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            System.out.println(fileReader.nextLine());
        }
        fileReader.close();
    }

}
