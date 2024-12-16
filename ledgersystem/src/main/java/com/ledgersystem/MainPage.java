package com.ledgersystem;
import java.util.Scanner;

public class MainPage {
    public static void showMainPage(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== Welcome, " + username + " ==");
        System.out.println("Balance:");
        System.out.println("Savings:");
        System.out.println("Loan:");

        while (true) {
            System.out.println("== Transactions ==");
            System.out.println("1. Debit");
            System.out.println("2. Credit");
            System.out.println("3. History");
            System.out.println("4. Savings");
            System.out.println("5. Credit Loan");
            System.out.println("6. Deposit Interest Predictor");
            System.out.println("7. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Handle Debit
                    System.out.println("Debit selected");
                    break;
                case 2:
                    // Handle Credit
                    System.out.println("Credit selected");
                    break;
                case 3:
                    // Handle History
                    System.out.println("History selected");
                    break;
                case 4:
                    // Handle Savings
                    System.out.println("Savings selected");
                    break;
                case 5:
                    // Handle Credit Loan
                    System.out.println("Credit Loan selected");
                    break;
                case 6:
                    // Handle Deposit Interest Predictor
                    System.out.println("Deposit Interest Predictor selected");
                    break;
                case 7:
                    // Handle Logout
                    System.out.println("Thank you for using Ledger System.");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}