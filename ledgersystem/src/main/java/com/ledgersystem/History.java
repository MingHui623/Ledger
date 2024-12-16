package com.ledgersystem;
import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History {
    public static void showHistory(Scanner scanner, String userId) {
        System.out.println("== Transaction History ==");
        System.out.printf("%-15s %-20s %-10s %-10s %-10s%n", "Date", "Description", "Debit", "Credit", "Balance");

        double balance = 0.0;
        try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\Transactions.csv"))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equals(userId)) {
                    String date = values[5];
                    String description = values[4];
                    String debit = "";
                    String credit = "";
                    double amount = Double.parseDouble(values[3]);
                    if (values[2].equals("Debit")) {
                        debit = String.format("%.2f", amount);
                        balance += amount;
                    } else if (values[2].equals("Credit")) {
                        credit = String.format("%.2f", amount);
                        balance -= amount;
                    }
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date parsedDate = inputFormat.parse(date);
                    String formattedDate = outputFormat.format(parsedDate);
                    System.out.printf("%-15s %-20s %-10s %-10s %-10.2f%n", formattedDate, description, debit, credit, balance);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the transaction history: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while parsing the date: " + e.getMessage());
        }
    }
}
