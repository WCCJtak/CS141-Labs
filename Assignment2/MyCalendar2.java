import java.util.Calendar;
import java.util.Scanner;

/**
* MyCalendar2.class
* @Author: JeongGyu Tak
* @Date: 11.06.2023
* @Class: CS&141A
* @Assignment: Assignment 2
*/
public class MyCalendar2 {
   // DO NOT CHANGE THIS VALUE
   final static int DAYS_PER_WEEK = 7;

   // Modify values below to modify of a calendar
   final static int CURRENT_YEAR = 2023;
   final static int BLOCK_LENGTH = 5;
   final static int BLOCK_HEIGHT = 3;
   final static int ROW_LENGTH = (BLOCK_LENGTH+1) * DAYS_PER_WEEK; // each column has a seperator
   final static char DAY_HIGHLIGHTER = '*';

   // Commands for this program
   final static char COMMAND_ENTER = 'e';
   final static char COMMAND_TODAY = 't';
   final static char COMMAND_NEXT_MONTH = 'n';
   final static char COMMAND_PREVIOUS_MONTH = 'p';
   final static char COMMAND_QUIT = 'q';

   public static void main(String[] args) {
      int userMonth;
      int userDay;
      String userDate;
      Scanner scanner = new Scanner(System.in);
      while(true) {
         help();
         Calendar calendar = Calendar.getInstance();
         char userInput = scanner.next().charAt(0);
         if (userInput == COMMAND_ENTER) {            
            System.out.println("What date would you like to look at? (mm/dd)");
            userDate = scanner.next();
            try {
               userMonth = monthFromDate(userDate);
               userDay = dayFromDate(userDate);
            } catch (Exception e) {
               System.out.println("Please input the date in correct format");
               continue;
            }
            calendar.set(Calendar.MONTH, userMonth-1);
            calendar.set(Calendar.DAY_OF_MONTH, userDay);
            drawMonth(calendar, userDay);
         } else if (userInput == COMMAND_TODAY) {
            drawMonth(calendar, calendar.get(Calendar.DATE));
         } else if (userInput == COMMAND_NEXT_MONTH) {
            int next_month = calendar.get(Calendar.MONTH) + 1;
            // if next month is january just reveal the past january
            if (next_month > Calendar.DECEMBER) {
               calendar.set(Calendar.MONTH, Calendar.JANUARY);
               drawMonth(calendar);
            } else {
               calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
               drawMonth(calendar);
            }
         } else if (userInput == COMMAND_PREVIOUS_MONTH) {
            int previous_month = calendar.get(Calendar.MONTH) - 1;
            // if last month is december just reveal the coming december
            if (previous_month < Calendar.JANUARY) {
               calendar.set(Calendar.MONTH, Calendar.DECEMBER);
               drawMonth(calendar);
            } else {
               calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
               drawMonth(calendar);
            }
         } else if (userInput == COMMAND_QUIT) {
            break;
         } else {
            System.out.println("Please enter a valid command");
            continue;
         }
      }
      scanner.close(); // close scanner
   }

   /**
    * Shows the manual of this program
    */
   public static void help() {
      System.out.println("Please type a command");
      System.out.printf("\t\"%c\" to enter a date and display the corresponding calendar\n", COMMAND_ENTER);
      System.out.printf("\t\"%c\" to get todays date and display the today's calendar\n", COMMAND_TODAY);
      System.out.printf("\t\"%c\" to display the next month\n", COMMAND_NEXT_MONTH);
      System.out.printf("\t\"%c\" to display the previous month\n", COMMAND_PREVIOUS_MONTH);
      System.out.printf("\t\"%c\" to quit the program\n", COMMAND_QUIT);
   }

   /**
    * Draw a calendar
    * 
    * @param calendar
    */
   public static void drawMonth(Calendar calendar) {
      // Show the title for a calendar of given month
      String stringMonth = Integer.toString(calendar.get(Calendar.MONTH)+1);
      for (int spaceCount=0; spaceCount<=ROW_LENGTH/2-stringMonth.length(); spaceCount++) {
         System.out.print(" ");
      }
      System.out.println(stringMonth);

      System.out.println(dateIndicator());

      calendar.set(Calendar.DAY_OF_MONTH, 1); //Set the day of month to 1
      int firstDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
      int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      
      for (int i=1-firstDayOfTheWeek; i<=daysInMonth; i+=7){
         if (i+7 > daysInMonth) {
            drawRow(i+1, daysInMonth);
         } else {
            drawRow(i+1, i+7);
         }
      }
   }

   /**
    * Draw a calendar with a day highlighted with *
    * 
    * @param calendar
    * @param highlightedDay
    */
   public static void drawMonth(Calendar calendar, int highlightedDay) {
      // Show the title for a calendar of given month
      String stringMonth = Integer.toString(calendar.get(Calendar.MONTH)+1);
      for (int spaceCount=0; spaceCount<=ROW_LENGTH/2-stringMonth.length(); spaceCount++) {
         System.out.print(" ");
      }
      System.out.println(stringMonth);
      System.out.println(dateIndicator());
      calendar.set(Calendar.DAY_OF_MONTH, 1); //Set the day of month to 1
      int firstDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK); //get day of week for 1st of month
      int month = calendar.get(Calendar.MONTH);
      int daysInMonth = getMonthDays(CURRENT_YEAR, month);
      
      for (int i=1-firstDayOfTheWeek; i<=daysInMonth; i+=DAYS_PER_WEEK){ // day index starts from 1
         if (i+DAYS_PER_WEEK > daysInMonth) {
            drawRow(i+1, daysInMonth, highlightedDay);
         } else {
            drawRow(i+1, i+DAYS_PER_WEEK, highlightedDay);
         }
      }
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
      month ++; // Calendar.MONTH starts from 0
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
   public static void drawRow(int firstDay, int lastDay) {
      String output = "";

      // if its the first row, add an additional row seperator to start a calendar.
      if (firstDay <= 1) {
         output += rowSeperator();
      }

      // draw each lines to form the block
      for (int line=0; line<BLOCK_HEIGHT; line++){
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
      System.out.print(output);
   }

   /**
    * Draws a row of a calendar, if the day to be highlighted is included in a row, highlight it.
    * 
    * @param firstDay
    * @param lastDay
    * @param highlightedDay
    */
   public static void drawRow(int firstDay, int lastDay, int highlightedDay) {
      String output = "";

      // if its the first row, add an additional row seperator to start a calendar.
      if (firstDay <= 1) {
         output += rowSeperator();
      }

      // draw each lines to form the block
      for (int line=0; line<BLOCK_HEIGHT; line++){
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
      System.out.print(output);
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
   public static int monthFromDate(String date) {
      return Integer.valueOf(date.split("/", 2)[0]); // format: mm/dd or m/d
   }
   
   /** 
    * Extracts an integer value for the day and return given date as a String.
    *
    * @param date A String format of mm/dd or m/d
    * @return int
    */
   public static int dayFromDate(String date) {
      return Integer.valueOf(date.split("/", 2)[1]); // format: mm/dd or m/d
   }
} // end class MyCalendar
