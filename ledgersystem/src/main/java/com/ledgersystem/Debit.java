package com.ledgersystem;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;

public class Debit {
    public static void handleDebit(Scanner scanner, String userId) {
        System.out.println("== Debit Transaction ==");
        System.out.print("Enter amount to debit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Perform debit calculation and update balance
        // For demonstration, we will just print the amount
        System.out.println("Debited amount: " + amount);

        // Write transaction details to Transaction.csv
        try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\Transactions.csv"))) {
            long transactionId = br.lines().skip(1).count(); // Skip the header line and count the number of lines to determine the next transaction ID
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("ledgersystem\\src\\main\\resources\\Transactions.csv", true))) {
                if (transactionId == 0) {
                    bw.newLine(); // Skip one line if it's the first transaction
                }
                bw.write((transactionId + 1) + "," + userId + "," + "Debit" + "," + amount + "," + description + "," + LocalDate.now());
                bw.newLine();
                System.out.println("Transaction recorded successfully.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the transaction data: " + e.getMessage());
        }
    }
}
