package com.ledgersystem;
import java.util.Scanner;
import java.io.*;
import java.util.Comparator;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class History {
    public static void showHistory(Scanner scanner, String userId) {
        System.out.println("== Transaction History ==");

        // Read user's transactions into an array
        Transaction[] transactions;
        int transactionCount = 0;

        // First, count the number of transactions for the user
        try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\Transactions.csv"))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equals(userId)) {
                    transactionCount++;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while counting transactions: " + e.getMessage());
            return;
        }

        // Initialize the array with the correct size
        transactions = new Transaction[transactionCount];

        // Read the user's transactions into the array
        try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\Transactions.csv"))) {
            String line;
            br.readLine(); // Skip the header line
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equals(userId)) {
                    Transaction transaction = new Transaction(values);
                    transactions[index++] = transaction;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading transactions: " + e.getMessage());
            return;
        }

        // Display the transactions
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.printf("%-15s %-20s %-10s %-10s %-10s%n", "Date", "Description", "Debit", "Credit", "Balance");
        double balance = 0.0;
        for (Transaction t : transactions) {
            String debit = "";
            String credit = "";
            if (t.getType().equalsIgnoreCase("Debit")) {
                debit = String.format("%.2f", t.getAmount());
                balance += t.getAmount();
            } else {
                credit = String.format("%.2f", t.getAmount());
                balance -= t.getAmount();
            }
            String formattedDate = t.getDate().format(dateFormat);
            System.out.printf("%-15s %-20s %-10s %-10s %-10.2f%n",
                              formattedDate, t.getDescription(), debit, credit, balance);
        }
    }
}
// Add a Transaction class to represent transaction data
class Transaction {
    private String userId;
    private LocalDate date;
    private String description;
    private String type;
    private double amount;

    public Transaction(String[] values) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.userId = values[1];
        this.type = values[2];
        this.amount = Double.parseDouble(values[3]);
        this.description = values[4];
        this.date = LocalDate.parse(values[5], dateFormat);
    }

    public String getUserId() {
        return userId;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public double getAmount() {
        return amount;
    }
}
