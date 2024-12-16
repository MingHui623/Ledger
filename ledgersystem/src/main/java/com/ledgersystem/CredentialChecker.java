package com.ledgersystem;
import java.util.Scanner;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CredentialChecker {

    public static void login(Scanner scanner) {
        System.out.println("== Please enter your email and password ==");
        while (true) {
            String email;
            while (true) {
                System.out.println("Email:");
                email = scanner.nextLine();
                if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println("Invalid email format. Please try again.");
                }
            }
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            String hashedPassword = hashPassword(password);

            try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\User.csv"))) {
                String line;
                boolean loginSuccessful = false;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values[2].equals(email) && values[3].equals(hashedPassword)) {
                        loginSuccessful = true;
                        break;
                    }
                }
                if (loginSuccessful) {
                    System.out.println("Login successful for user: " + email);
                    break;
                } else {
                    System.out.println("Invalid email or password. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the user data.");
            }
        }
    }

    public static void register(Scanner scanner) {
        System.out.println("== Please enter your name, email and password ==");
        System.out.println("Name:");
        String name = scanner.nextLine();
        String email;
        while (true) {
            System.out.println("Email:");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }
        System.out.println("Password:");
        String password = scanner.nextLine();

        String hashedPassword = hashPassword(password);

        try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\User.csv"))) {
            long userId = br.lines().count(); // Count the number of lines to determine the next user ID
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("ledgersystem\\src\\main\\resources\\User.csv", true))) {
                bw.write(userId + "," + name + "," + email + "," + hashedPassword);
                bw.newLine();
                System.out.println("Registration successful for user: " + email);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the user data.");
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
