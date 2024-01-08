import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

/**
* MyCalendar3.class
* @Author: JeongGyu Tak
* @Date: 12.04.2023
* @Class: CS&141A
* @Assignment: Assignment 3
*/
public class MyCalendar3 {
   // DO NOT CHANGE THIS VALUE
   final static int DAYS_PER_WEEK = 7;

   // Modify values below to modify of a calendar
   final static int CURRENT_YEAR = 2023;
   final static int BLOCK_LENGTH = 19;
   final static int BLOCK_HEIGHT = 5;
   final static int ROW_LENGTH = (BLOCK_LENGTH+1) * DAYS_PER_WEEK; // each column has a seperator
   final static char DAY_HIGHLIGHTER = '*';

   final static String EVENT_FILE = "calendarEvents.txt";
   static String[][] EVENT_ARRAY = initializeEventArray();

   // Commands for this program
   final static String COMMAND_ENTER = "e";
   final static String COMMAND_TODAY = "t";
   final static String COMMAND_NEXT_MONTH = "n";
   final static String COMMAND_PREVIOUS_MONTH = "p";
   final static String COMMAND_QUIT = "q";
   final static String COMMAND_EVENT = "ev";
   final static String COMMAND_FILE_PRINT = "fp";


   /**
    * about instructions, refer help()
    * @param args
    */
   public static void main(String[] args) {
      boolean running = true;
      Scanner scanner = new Scanner(System.in);
      while(running) {
         help();
         switch (scanner.next().toLowerCase()) {
            case COMMAND_ENTER:
               showUserMonth(scanner);
               break;
            case COMMAND_TODAY:
               showThisMonth();
               break;
            case COMMAND_NEXT_MONTH:
               showNextMonth();
               break;
            case COMMAND_PREVIOUS_MONTH:
               showPreviousMonth();
               break;
            case COMMAND_QUIT:
               running = false;
               break;
            case COMMAND_EVENT:
               addEvent(scanner);
               break;
            case COMMAND_FILE_PRINT:
               saveOutput(scanner);
               break;
            default:
               System.out.println("Please enter a valid command");
         }
      }
      scanner.close(); // close scanner
   }

   /**
    * Saves output of the given month into given file name from the user input
    * @param scanner
    */
   public static void saveOutput(Scanner scanner) {
      int targetMonth;
      System.out.println("What month would you like to save?");
      targetMonth = scanner.nextInt();
      System.out.println("Please provide the file name to be saved");
      File file = new File(scanner.next());
      try {
         FileWriter fileWriter = new FileWriter(file, false);
         Calendar calendar = Calendar.getInstance();
         calendar.set(Calendar.MONTH, targetMonth-1);
         fileWriter.write(drawMonth(calendar));
         fileWriter.close();
      } catch (IOException e) {
         e.printStackTrace();
      }    

   }

   /**
    * adds the event in to EVENT_ARRAY
    * @param scanner
    */
   public static void addEvent(Scanner scanner) {
      int eventMonth;
      int eventDay;
      String eventDate;
      String eventName;
      System.out.println("What date would you like add an event? (mm/dd)");
      eventDate = scanner.next();
      try {
         eventMonth = monthFromDate(eventDate);
         eventDay = dayFromDate(eventDate);
      } catch (Exception e) {
         System.out.println("Invalid format please try again.");
         return;
      }
      System.out.println("What is the event name?");
      eventName = scanner.next();
      EVENT_ARRAY[eventMonth-1][eventDay-1] = eventName;
   }

   /**
    * initialize the EVENT_ARRAY. Adds default events from calendarEvents.txt as well
    * @return eventArray
    */
   public static String[][] initializeEventArray() {
      // initialize the array
      String[][] eventArray = new String[12][];
      for (int monthIndex=0; monthIndex<=Calendar.DECEMBER; monthIndex++) {
         eventArray[monthIndex] = new String[getMonthDays(CURRENT_YEAR, monthIndex+1)];
      }
      
      // add event from the file calendarEvents.txt
      File eventFile = new File(EVENT_FILE);
      Scanner fileReader;
      try {
         fileReader = new Scanner(eventFile);
      } catch (FileNotFoundException e) {
         System.out.println("Failed to load the event file. Please check calendarEvents.txt");
         return eventArray; // just end here
      }
      while (fileReader.hasNextLine()) {
         String[] event = fileReader.nextLine().split(" ");
         String eventDate = event[0];
         String eventTitle = event[1];
         int eventMonth = monthFromDate(eventDate);
         int eventDay = dayFromDate(eventDate);
         eventArray[eventMonth-1][eventDay-1] = eventTitle;
      }
      fileReader.close();
      return eventArray;
   }

   /**
    * returns true if there is an event for given day of the month
    */
   public boolean isEventDay(int month, int day) {
      if (EVENT_ARRAY[month-1][day-1] != null) {
         return true;
      }
      return false;
   }

   /**
    * shows the calendar of the month of user input and highlights the date
    * 
    * @param scanner
    */
   public static void showUserMonth(Scanner scanner) {
      int userMonth;
      int userDay;
      String userDate;
      Calendar calendar = Calendar.getInstance();
      System.out.println("What date would you like to look at? (mm/dd)");
      userDate = scanner.next();
      try {
         userMonth = monthFromDate(userDate);
         userDay = dayFromDate(userDate);
      } catch (Exception e) {
         System.out.println("Invalid format please try again.");
         return;
      }
      calendar.set(Calendar.MONTH, userMonth-1);
      calendar.set(Calendar.DAY_OF_MONTH, userDay);
      System.out.print(drawMonth(calendar, userDay));
   }

   /**
    * shows the calendar of this month and highlights today's date
    */
   public static void showThisMonth() {
      Calendar calendar = Calendar.getInstance();
      System.out.print(drawMonth(calendar, calendar.get(Calendar.DATE)));
   }

   /**
    * shows the next month
    */
   public static void showNextMonth() {
      Calendar calendar = Calendar.getInstance();
      // if next month is january just reveal the past january
      int next_month = calendar.get(Calendar.MONTH) + 1;
      if (next_month > Calendar.DECEMBER) {
         calendar.set(Calendar.MONTH, Calendar.JANUARY);
         System.out.print(drawMonth(calendar));
      } else {
         calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
         System.out.print(drawMonth(calendar));
      }
   }

   /**
    * shows the previous month
    */
   public static void showPreviousMonth() {
      Calendar calendar = Calendar.getInstance();
      // if last month is december just reveal the coming december
      int previous_month = calendar.get(Calendar.MONTH) - 1;
      if (previous_month < Calendar.JANUARY) {
         calendar.set(Calendar.MONTH, Calendar.DECEMBER);
         System.out.print(drawMonth(calendar));
      } else {
         calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
         System.out.print(drawMonth(calendar));
      }
   }

   /**
    * Shows the manual of this program
    */
   public static void help() {
      System.out.println("Please type a command");
      System.out.printf("\t\"%s\" to add an event to the calendar\n", COMMAND_EVENT);
      System.out.printf("\t\"%s\" to save the calendar as a file\n", COMMAND_FILE_PRINT);
      System.out.printf("\t\"%s\" to enter a date and display the corresponding calendar\n", COMMAND_ENTER);
      System.out.printf("\t\"%s\" to get todays date and display the today's calendar\n", COMMAND_TODAY);
      System.out.printf("\t\"%s\" to display the next month\n", COMMAND_NEXT_MONTH);
      System.out.printf("\t\"%s\" to display the previous month\n", COMMAND_PREVIOUS_MONTH);
      System.out.printf("\t\"%s\" to quit the program\n", COMMAND_QUIT);
   }

   /**
    * Draw a calendar
    * 
    * @param calendar
    */
   public static String drawMonth(Calendar calendar) {
      String output = "";
      // Show the title for a calendar of given month
      int month = calendar.get(Calendar.MONTH);
      String stringMonth = Integer.toString(month+1);
      for (int spaceCount=0; spaceCount<=ROW_LENGTH/2-stringMonth.length(); spaceCount++) {
         output += " ";
      }
      output += stringMonth;
      output += "\n";
      output += dateIndicator();
      output += "\n";
      calendar.set(Calendar.DAY_OF_MONTH, 1); //Set the day of month to 1
      int firstDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
      int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      
      for (int i=1-firstDayOfTheWeek; i<=daysInMonth; i+=7) {
         if (i+7 > daysInMonth) {
            output += drawRow(month, i+1, daysInMonth);
         } else {
            output += drawRow(month, i+1, i+7);
         }
      }
      return output;
   }

   /**
    * Draw a calendar with a day highlighted with *
    * 
    * @param calendar
    * @param highlightedDay
    */
   public static String drawMonth(Calendar calendar, int highlightedDay) {
      String output = "";
      // Show the title for a calendar of given month
      int month = calendar.get(Calendar.MONTH);
      String stringMonth = Integer.toString(month+1);
      for (int spaceCount=0; spaceCount<=ROW_LENGTH/2-stringMonth.length(); spaceCount++) {
         output += " ";
      }
      output += stringMonth;
      output += "\n";
      output += dateIndicator();
      output += "\n";
      calendar.set(Calendar.DAY_OF_MONTH, 1); //Set the day of month to 1
      int firstDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK); //get day of week for 1st of month
      int daysInMonth = getMonthDays(CURRENT_YEAR, month+1);
      
      for (int i=1-firstDayOfTheWeek; i<=daysInMonth; i+=DAYS_PER_WEEK) { // day index starts from 1
         if (i+DAYS_PER_WEEK > daysInMonth) {
            output += drawRow(month, i+1, daysInMonth, highlightedDay);
         } else {
            output += drawRow(month, i+1, i+DAYS_PER_WEEK, highlightedDay);
         }
      }
      return output;
   }

   /**
    * Returns integer value calculating how many days in a month of a certain year.
    *
    * @param year
    * @param month
    * @return days
    */
   public static int getMonthDays(int year, int month) {
      int daysInMonth ;
      if (month == 4 || month == 6 || month == 9 || month == 11) {
          daysInMonth = 30;
      }
      else {
          if (month == 2) {
              daysInMonth = (year % 4 == 0) ? 29 : 28;
          } else {
              daysInMonth = 31;
          }
      }
      return daysInMonth;
  }

   /**
    * Returns a String to seperate rows for calendar.
    * 
    * @return String
    */
   public static String rowSeperator() {
      String output = "";
      for (int i=0; i<=ROW_LENGTH; i++) {
         output += "-";
      }
      output += "\n"; // close a row seperater line
      return output;
   }

   /**
    * Returns a string to determine read the dates on calendar
    * 
    * @return String
    */
   public static String dateIndicator() {
      String output = "";
      for (int i=0; i<=BLOCK_LENGTH/2; i++) {
         output += " ";
      }
      output += "S";
      for (int i=0; i<=BLOCK_LENGTH-1; i++) {
         output += " ";
      }
      output += "M";
      for (int i=0; i<=BLOCK_LENGTH-1; i++) {
         output += " ";
      }
      output += "T";
      for (int i=0; i<=BLOCK_LENGTH-1; i++) {
         output += " ";
      }
      output += "W";
      for (int i=0; i<=BLOCK_LENGTH-1; i++) {
         output += " ";
      }
      output += "T";
      for (int i=0; i<=BLOCK_LENGTH-1; i++) {
         output += " ";
      }
      output += "F";
      for (int i=0; i<=BLOCK_LENGTH-1; i++) {
         output += " ";
      }
      output += "S";
      return output;
   }


   /**
    * Draws the row of a calendar
    *
    * @param firstDay
    * @param lastDay
    */
   public static String drawRow(int month, int firstDay, int lastDay) {
      String output = "";

      // if its the first row, add an additional row seperator to start a calendar.
      if (firstDay <= 1) {
         output += rowSeperator();
      }

      // draw each lines to form the block
      for (int line=0; line<BLOCK_HEIGHT; line++) {
         // fill a line with blocks
         for (int day=firstDay; day<firstDay+DAYS_PER_WEEK; day++) {
            output += "|"; // start a block
            if (line == 0) { // every first line shows the day
               String stringDay = "";
               if (day >= 1 && day <= lastDay) {
                  stringDay += String.valueOf(day);
                  output += stringDay;
               }
               // fill spaces for the rest of the first line of a block
               for (int spaceCount=0; spaceCount<BLOCK_LENGTH-stringDay.length(); spaceCount++) {
                  output += " "; // add a space
               }
            // in line 1 if its not an empty day but also event day
            } else if (line==1 && day>0 && day<=lastDay && EVENT_ARRAY[month][day-1] != null) {
               if (EVENT_ARRAY[month][day-1] != null) {
                  if (EVENT_ARRAY[month][day-1].length() > BLOCK_LENGTH) {
                     // if the event name is too long shorten it and leave ...
                     output += EVENT_ARRAY[month][day-1].substring(0, BLOCK_LENGTH-3).replace("_", " ");
                     output += "...";
                  } else {
                     output += EVENT_ARRAY[month][day-1].replace("_", " ");
                     // fill spaces for the rest of the first line of a block
                     for (int spaceCount=0; spaceCount<BLOCK_LENGTH-EVENT_ARRAY[month][day-1].length(); spaceCount++) {
                        output += " "; // add a space
                     }
                  }
               }
            } else {
               // fill spaces for a line
               for (int spaceCount=0; spaceCount<BLOCK_LENGTH; spaceCount++) {
                  output += " "; // add a space
               }
            }
         }
         output += "|\n"; // close a line
      }
      // each row ends with a row seperator
      output += rowSeperator();
      return output;
   }

   /**
    * Draws a row of a calendar, if the day to be highlighted is included in a row, highlight it.
    * 
    * @param firstDay
    * @param lastDay
    * @param highlightedDay
    */
   public static String drawRow(int month, int firstDay, int lastDay, int highlightedDay) {
      String output = "";

      // if its the first row, add an additional row seperator to start a calendar.
      if (firstDay <= 1) {
         output += rowSeperator();
      }

      // draw each lines to form the block
      for (int line=0; line<BLOCK_HEIGHT; line++) {
         // fill a line with blocks
         for (int day=firstDay; day<firstDay+DAYS_PER_WEEK; day++) {
            output += "|"; // start a block
            if (line == 0) { // every first line shows the day
               String stringDay = "";
               if (day >= 1 && day <= lastDay) {
                  stringDay += String.valueOf(day);
                  if (day == highlightedDay) {
                     stringDay += DAY_HIGHLIGHTER;
                  }
                  output += stringDay;
               } 
               // fill spaces for the rest of the first line of a block
               for (int spaceCount=0; spaceCount<BLOCK_LENGTH-stringDay.length(); spaceCount++) {
                  output += " "; // add a space
               }
            // in line 1 if its not an empty day but also event day
            } else if (line==1 && day>0 && day<=lastDay && EVENT_ARRAY[month][day-1] != null) {
               if (EVENT_ARRAY[month][day-1] != null) {
                  if (EVENT_ARRAY[month][day-1].length() > BLOCK_LENGTH) {
                     // if the event name is too long shorten it and leave ...
                     output += EVENT_ARRAY[month][day-1].substring(0, BLOCK_LENGTH-3).replace("_", " ");
                     output += "...";
                  } else {
                     output += EVENT_ARRAY[month][day-1].replace("_", " ");
                     // fill spaces for the rest of the first line of a block
                     for (int spaceCount=0; spaceCount<BLOCK_LENGTH-EVENT_ARRAY[month][day-1].length(); spaceCount++) {
                        output += " "; // add a space
                     }
                  }
               }
            } else {
               // fill spaces for a line
               for (int spaceCount=0; spaceCount<BLOCK_LENGTH; spaceCount++) {
                  output += " "; // add a space
               }
            }
         }
         output += "|\n"; // close a line
      }
      // each row ends with a row seperator
      output += rowSeperator();
      return output;
   }

   /** 
    * Displays the date information.
    * 
    * @param month An integer representing the month
    * @param day   An integer representing the day
    * @return None
    */
   public static void displayDate(int month, int day) {
      System.out.printf("Month: %d\n", month);
      System.out.printf("Day:   %d\n", day);
   }
   
   /** 
    * Extracts an integer value for the month and returns given date as a String.
    *
    * @param date A String format of mm/dd or m/d
    * @return int
    */
   public static int monthFromDate(String date) throws NumberFormatException{
      return Integer.valueOf(date.split("/", 2)[0]); // format: mm/dd or m/d
   }
   
   /** 
    * Extracts an integer value for the day and return given date as a String.
    *
    * @param date A String format of mm/dd or m/d
    * @return int
    */
   public static int dayFromDate(String date) throws NumberFormatException{
      return Integer.valueOf(date.split("/", 2)[1]); // format: mm/dd or m/d
   }
} // end class MyCalendar
