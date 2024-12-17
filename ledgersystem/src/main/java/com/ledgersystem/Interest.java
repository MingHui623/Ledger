package com.ledgersystem;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interest {
    public static void handleInterestPrediction(Scanner scanner, double balance) {
        System.out.println("== Deposit Interest Predictor ==");
        System.out.println("Choose a bank:");
        System.out.println("1. RHB");
        System.out.println("2. Maybank");
        System.out.println("3. Hong Leong");
        System.out.println("4. Alliance");
        System.out.println("5. AmBank");
        System.out.println("6. Standard Chartered");
        int bankChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        double annualInterestRate = getInterestRate(bankChoice);
        if (annualInterestRate == -1) {
            System.out.println("Invalid bank choice.");
            return;
        }

        double monthlyInterest = (balance * (annualInterestRate / 100)) / 12;
        System.out.printf("The monthly interest for your balance is: RM %.2f\n", monthlyInterest);
    }

    private static double getInterestRate(int bankChoice) {
        try (BufferedReader reader = new BufferedReader(new FileReader("d:/FOP WIX1002/CLIledgerSystem/ClIledgerSystem/bank.csv"))) {
            String line = reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && Integer.parseInt(parts[0]) == bankChoice) {
                    return Double.parseDouble(parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the bank data.");
            e.printStackTrace();
        }
        return -1;
    }
}