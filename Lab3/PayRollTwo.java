import java.util.Scanner;

/** 
 * PayRollTwo.class
 * @Author: JeongGyu Tak
 * @Date: 10.24.2023
 * @Class: CS&141A
 * @Assignment: LAB#3
 * @Purpose: Interactive payroll program using for loops and switch cases
 */
public class PayRollTwo {
   // Contract Type Constants
   final static int SALARIED = 1;
   final static int HOURLY = 2;
   // Overtime Constants
   // Most employees who work more than 40 hours in a 7-day workweek must be paid overtime. 
   // Overtime pay must be at least 1.5 times the employee’s regular hourly rate.
   // REF: https://www.lni.wa.gov/workers-rights/wages/overtime/#:~:text=Overtime%20pay%20must%20be%20at,their%20right%20to%20overtime%20pay.
   final static int MAX_WORKING_HOUR_PER_WEEK = 40;
   final static double OVERTIME_PAY_RATE = 1.5;
   final static double TAX_RATE = 0.2;
   
   /*
    * The program will prompt the user to input the number of employees. 
    * It will then ask for the employee's name and how they are paid, 
    * whether they receive a salary or are paid hourly.
    * If the worker is paid a salary, they will need to input the amount.
    * If the worker is paid hourly, they will need to input their pay rate
    * and the number of hours they worked. The program will then calculate
    * and display the gross and net pay. This process will be repeated for
    * each employee until sufficient number of employees has done the process.
    */
   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      int workersCount = 0;

      // Ask number of workers
      System.out.println("Welcome to IPP (Interactive Payroll Program\n");
      System.out.print("Please input the number of employee: ");
      workersCount = scanner.nextInt();

      for (int index=0; index<workersCount; index++) {
         String workerName = "";
         int contractType = 0;
         double workerGross = 0.0;
         double workerNetPay = 0.0;

         workerName = inputName(scanner);
         contractType = inputContractType(scanner);

         // Enter extra data for each contract type
         switch(contractType) {
            case SALARIED: // if its salaried contract
               double salary = 0.0;
               System.out.print("Pleasae input your salary: ");
               salary = scanner.nextDouble();
               workerGross = mathGross(salary);
               break;
            case HOURLY: // if its hourly contract
               double payrate = 0.0;
               double workHours = 0.0;
               System.out.print("Please input your hourly payrate: ");
               payrate = scanner.nextDouble();
               System.out.print("Please input how many hours you have worked: ");
               workHours = scanner.nextDouble();
               workerGross = mathGross(workHours, payrate);
               break;
            default:// if the contract type is not defined, retry.
               System.out.printf("Your value %d is not defined." 
                              + "You should input either 1(Salaried) or 2(Hourly),"
                              + "please try it again.\n", contractType);
               index--; // countdown the index for retry and continue the loop
               continue;
            }
         workerNetPay = mathNetpay(workerGross);
         System.out.printf("%s, your workerGross is $%.2f and net pay is $%.2f\n", 
                           workerName, workerGross, workerNetPay);
      } // end of loop
      scanner.close();
   } // end method main

   /**
    * Get worker name from system input
    * 
    * @param java.util.Scanner scanner 
    * @return String name
    */
   public static String inputName(Scanner scanner){
      // Ask Name
      // Splitting the name because nextLine() detects a newline after the nextInt()
      // when the user press enter after input
      System.out.print("\nPlease input your first name: ");
      String workerFirstName = scanner.next();
      System.out.print("Please input your last name: ");
      String workerLastName = scanner.next();
      return workerFirstName + " " + workerLastName;
   }

   /**
    * Get the contract type from system input
    * 
    * @param scanner java.util.Scanner
    * @return int contractType
    */
   public static int inputContractType(Scanner scanner){
      // Ask contract type
      System.out.print("How are you getting paid?\n"
                     + "1. Salaried\n"
                     + "2. Hourly\n"
                     + "Your answer: ");
      return scanner.nextInt();

   }

   /**
    * Math gross from given work hours it's payrate
    * 
    * @param workHours
    * @param payrate
    * @return double gross
    */
   public static double mathGross(double workHours, double payrate) {
      if (workHours > MAX_WORKING_HOUR_PER_WEEK) { // if its overtime
         double overtime = workHours - MAX_WORKING_HOUR_PER_WEEK;
         return payrate * (MAX_WORKING_HOUR_PER_WEEK + overtime * OVERTIME_PAY_RATE);
      } else { // if its not overtime.
         return payrate * workHours;
      }
   }

   /**
    * Math gross from salary
    * 
    * @param salary
    * @return double gross
    */
   public static double mathGross(double salary) {
      return salary;
   }

   /**
    * Math netpay after tax
    *
    * @param gross
    * @return double netpay
    */
   public static double mathNetpay(double gross) {
      return (1 - TAX_RATE) * gross;
   }

} // end class PayRoll1
