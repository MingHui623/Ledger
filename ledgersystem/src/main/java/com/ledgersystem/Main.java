package com.ledgersystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Ledger System ==");
        System.out.println("Login or Register:");
        System.out.println("1. Login");
        System.out.println("2. Register");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                CredentialChecker.login(scanner);
                break;
            case 2:
                CredentialChecker.register(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please select 1 or 2.");
        }
    }
}