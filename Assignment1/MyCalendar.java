import java.util.Calendar;
import java.util.Scanner;

/**
* MyCalendar.class
* @Author: JeongGyu Tak
* @Date: 10.16.2023
* @Class: CS&141A
* @Assignment: Assignment 1
* @Purpose: Using loop and methods to write an efficient code
*/
public class MyCalendar {
   // modify values below to fix the size of a calendar
   final static int DAYS_PER_WEEK = 7;
   final static int BLOCK_LENGTH = 5;
   final static int BLOCK_HEIGHT = 3;
   final static int ROW_LENGTH = (BLOCK_LENGTH+1) * DAYS_PER_WEEK; // each column has a seperator

   /**
    * This program reveals a calendar of given date by user,
    * then it reveals a next calendar which is the calendar of this month.
    * Here is an example of an output of this program :
    * What date would you like to look at? (mm/dd)
    * 07/16
    *                      7
    * -------------------------------------------
    * |1    |2    |3    |4    |5    |6    |7    |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |8    |9    |10   |11   |12   |13   |14   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |15   |16   |17   |18   |19   |20   |21   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |22   |23   |24   |25   |26   |27   |28   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |29   |30   |31   |32   |33   |34   |35   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * Month: 7
    * Day:   16
    *
    * This month: 
    *                      9
    * -------------------------------------------
    * |1    |2    |3    |4    |5    |6    |7    |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |8    |9    |10   |11   |12   |13   |14   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |15   |16   |17   |18   |19   |20   |21   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |22   |23   |24   |25   |26   |27   |28   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * |29   |30   |31   |32   |33   |34   |35   |
    * |     |     |     |     |     |     |     |
    * |     |     |     |     |     |     |     |
    * -------------------------------------------
    * Month: 9
    * Day:   16
    */
   public static void main(String[] args) {
      int currentMonth;
      int currentDay;
      int userMonth;
      int userDay;
      String userDate;

      // User input
      Scanner scanner = new Scanner(System.in);
      System.out.println("What date would you like to look at? (mm/dd)");
      userDate = scanner.next();
      userMonth = monthFromDate(userDate);
      userDay = dayFromDate(userDate);

      // Draw first Calendar
      drawMonth(userMonth);

      // Display the given date
      displayDate(userMonth, userDay);

      // Draw second Calender
      Calendar calendar = Calendar.getInstance(); 
      System.out.print("\nThis month: \n"); // Extra newline to seperate from the first calendar
      currentMonth = calendar.get(Calendar.MONTH + 1); // add one because the enumeration starts from 0
      currentDay = calendar.get(Calendar.DATE);
      drawMonth(currentMonth);

      // Display the current date
      displayDate(currentMonth, currentDay);
      scanner.close(); // close scanner
   }

   /** 
    * Displays the month and a graphical representation of the calendar.
    * 
    * @param month An integer representing the month
    * @return None
    */
   public static void drawMonth(int month) {
      // Show the title for a calendar of given month
      String stringMonth = Integer.toString(month);
      for (int spaceCount=0; spaceCount<=ROW_LENGTH/2-stringMonth.length(); spaceCount++) {
         System.out.print(" ");
      }
      System.out.println(String.valueOf(month));

      // Draw rows
      for (int row=1; row<=5; row++) {
         drawRow(row);
      }
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
    * Displays a week on the calendar.
    * 
    * @param row An integer representing which row it is displaying.
    * @return None
    */
   public static void drawRow(int row) {
      int lastDay = row * DAYS_PER_WEEK;
      int firstDay = lastDay - DAYS_PER_WEEK + 1;
      String output = "";

      // if its the first row, add an additional row seperator to start a calendar.
      if (row == 1) {
         output += rowSeperator();
      }

      // draw each lines to form the block
      for (int line=0; line<BLOCK_HEIGHT; line++){
         // fill a line with blocks
         for (int day=firstDay; day<=lastDay; day++) {
            output += "|"; // start a block
            if (line == 0) { // every first line shows the day
               String stringDay = String.valueOf(day);
               output += stringDay;
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
