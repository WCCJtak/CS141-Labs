/* Name: Nathan Lipinski, Sadie Raihl, JeongGyu Tak
 * Date: 10.05.2023
 * Class: CS&141A
 * Assignment: LAB#2
 * Purpose: Interactive payroll program.
 */
import java.util.Scanner;

public class PayRoll {
   // Contract Type Constants
   final static int SALARIED = 1;
   final static int HOURLY = 2;
   // Overtime Constants
   // Most employees who work more than 40 hours in a 7-day workweek must be paid overtime. 
   // Overtime pay must be at least 1.5 times the employee’s regular hourly rate.
   // REF: https://www.lni.wa.gov/workers-rights/wages/overtime/#:~:text=Overtime%20pay%20must%20be%20at,their%20right%20to%20overtime%20pay.
   final static int MAX_WORKING_HOUR_PER_WEEK = 40;
   final static double OVERTIME_PAY_RATE = 1.5;
   
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

      System.out.println("Welcome to IPP (Interactive Payroll Program\n");
      System.out.print("Please input the number of employee: ");
      workersCount = scanner.nextInt();

      while (workersCount > 0) {
         String workerFirstName = "";
         String workerLastName = "";
         int contractType = 0;
         double workerGross = 0.0;
         double workerNetPay = 0.0;

         // Ask Name
         // Splitting the name because nextLine() detects a newline after the nextInt()
         // when the user press enter after input
         System.out.print("\nPlease input your first name: ");
         workerFirstName = scanner.next();
         System.out.print("Please input your last name: ");
         workerLastName = scanner.next();

         // Ask contract type
         System.out.printf("Hello, %s %s\n", workerFirstName, workerLastName);
         System.out.print("How are you getting paid?\n"
                        + "1. Salaried\n"
                        + "2. Hourly\n"
                        + "Your answer: ");
         contractType = scanner.nextInt();

         // Enter extra data for each contract type
         if (contractType == SALARIED) { // if its salaried contract
            System.out.print("Pleasae input your salary: ");
            workerGross = scanner.nextDouble();
         } else if (contractType == HOURLY) { // if its hourly contract
            double payrate = 0.0;
            double workHours = 0.0;
            System.out.print("Please input your hourly payrate: ");
            payrate = scanner.nextDouble();
            System.out.print("Please input how many hours you have worked: ");
            workHours = scanner.nextDouble();
            if (workHours > MAX_WORKING_HOUR_PER_WEEK) { // if its overtime
               // Refer to L:14
               double overtime = workHours - MAX_WORKING_HOUR_PER_WEEK;
               workerGross = payrate * (MAX_WORKING_HOUR_PER_WEEK + overtime * OVERTIME_PAY_RATE);
            } else { // if its not overtime.
               workerGross = payrate * workHours;
            }
         } else { // if the contract type is not defined, retry.
            System.out.printf("Your value %d is not defined." 
                            + "You should input either 1(Salaried) or 2(Hourly),"
                            + "please try it again.\n", contractType);
            continue; // skip workerCount--, to let user to try again.
         }

         // net pay is after tax, and we assume the tax rate is 20%
         workerNetPay = 0.8 * workerGross;
         System.out.printf("%s %s, your workerGross is $%.2f and net pay is $%.2f\n", 
                           workerFirstName, workerLastName, workerGross, workerNetPay);

         workersCount--; // Done, continue the loop
      } // end of loop
      scanner.close();
   } // end method main
} // end class PayRoll1
