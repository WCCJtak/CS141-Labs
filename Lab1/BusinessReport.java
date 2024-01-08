/* Name: Nathan Lipinski, Sadie Raihl, JeongGyu Tak
 * Date: 09.28.2023
 * Class: CS&141A
 * Assignment: LAB#1
 * Purpose: To print out the menu of McDonalds in format.
 */

public class BusinessReport {
   // main method begins execution of Java application
   public static void main(String[] args) {
    // REF: https://www.hotmenuprice.com/mcdonalds-menu-price-usa/
    // McDonald’s Menu 2023 USA [UPDATED]
    // FEATURED FAVOURITES
    // Spicy Crispy Chicken Sandwich	$5.17
    // Iced Coffee	$2.16
    // Egg McMuffin®	$4.48
    // Chicken McNuggets®	$2.49
    // Sausage Burrito	$2.16
    // Big Mac®	$5.53
    // World Famous Fries®	$2.71
    // Quarter Pounder®* with Cheese	$5.65
    String spicyCrispyChickenSandwich = "Spicy Crispy Chicken Sandwich";
    double spicyCrispyChickenSandwichPrice	= 5.17;

    String icedCoffee = "Iced Coffee";
    double icedCoffeePrice = 2.16;

    String eggMcMuffin = "Egg McMuffin®";
    double eggMcMuffinPrice = 4.48;

    String chickenMcNuggests = "Chicken McNuggets®";
    double chickenMcNuggestsPrice = 2.49;

    String sausageBurrito = "Sausage Burrito";
    double sausageBurritoPrice = 2.16;

    String bigMac = "Big Mac®";
    double bigMacPrice = 5.53;

    String worldFamousFries = "World Famous Fries®";
    double worldFamousFriesPrice = 2.71;

    String quarterPounderWithCheese = "Quarter Pounder®* with Cheese";
    double quarterPounderWithCheesePrice = 5.65;

    System.out.println("We would like to present you feature favourite menus of McDonalds.");
    System.out.printf("The price of an %s is $%.2f\n", spicyCrispyChickenSandwich, spicyCrispyChickenSandwichPrice);
    System.out.printf("The price of an %s is $%.2f\n", icedCoffee, icedCoffeePrice);
    System.out.printf("The price of an %s is $%.2f\n", eggMcMuffin, eggMcMuffinPrice);
    System.out.printf("The price of an %s is $%.2f\n", chickenMcNuggests, chickenMcNuggestsPrice);
    System.out.printf("The price of an %s is $%.2f\n", sausageBurrito, sausageBurritoPrice);
    System.out.printf("The price of an %s is $%.2f\n", bigMac, bigMacPrice);
    System.out.printf("The price of an %s is $%.2f\n", worldFamousFries, worldFamousFriesPrice);
    System.out.printf("The price of an %s is $%.2f", quarterPounderWithCheese, quarterPounderWithCheesePrice);
   } // end method main
} // end class Welcome1
