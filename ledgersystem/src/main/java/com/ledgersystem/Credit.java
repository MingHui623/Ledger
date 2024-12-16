package com.ledgersystem;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;

public class Credit {
    public static void handleCredit(Scanner scanner, String userId) {
        System.out.println("== Credit Transaction ==");
        System.out.print("Enter amount to credit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Perform credit calculation and update balance
        double newBalance = updateBalance(userId, amount);

        // Write transaction details to Transaction.csv
        try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\Transactions.csv"))) {
            long transactionId = br.lines().skip(1).count(); // Skip the header line and count the number of lines to determine the next transaction ID
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("ledgersystem\\src\\main\\resources\\Transactions.csv", true))) {
                if (transactionId == 0) {
                    bw.newLine(); // Skip one line if it's the first transaction
                }
                bw.write((transactionId + 1) + "," + userId + "," + "Credit" + "," + amount + "," + description + "," + LocalDate.now());
                bw.newLine();
                System.out.println("Transaction recorded successfully. New balance: " + newBalance);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the transaction data: " + e.getMessage());
        }
    }

    private static double updateBalance(String userId, double amount) {
        double balance = 0.0;
        File balanceFile = new File("ledgersystem\\src\\main\\resources\\Balance.csv");
        File tempFile = new File("ledgersystem\\src\\main\\resources\\Balance_temp.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(balanceFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean userFound = false;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(userId)) {
                    balance = Double.parseDouble(values[1]) - amount;
                    bw.write(userId + "," + balance);
                    bw.newLine();
                    userFound = true;
                } else {
                    bw.write(line);
                    bw.newLine();
                }
            }
            if (!userFound) {
                balance = -amount;
                bw.write(userId + "," + balance);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating the balance: " + e.getMessage());
        }

        // Replace the old balance file with the updated one
        if (!balanceFile.delete() || !tempFile.renameTo(balanceFile)) {
            System.out.println("An error occurred while updating the balance file.");
        }

        return balance;
    }
}
