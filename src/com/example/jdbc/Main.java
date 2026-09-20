package com.example.jdbc;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==================================================");
        System.out.println("      JAVA JDBC STUDENT MANAGEMENT SYSTEM        ");
        System.out.println("==================================================");

        while (running) {
            System.out.println("\n---------------- MENU ----------------");
            System.out.println("1. Insert Student");
            System.out.println("2. Update Student GPA");
            System.out.println("3. Delete Student");
            System.out.println("4. View All Students");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[INVALID] Please enter a valid number (1-5).");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\n--- Insert New Student ---");
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine().trim();

                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine().trim();

                    System.out.print("Enter GPA: ");
                    double gpa = Double.parseDouble(scanner.nextLine().trim());

                    dao.addStudent(new Student(name, email, gpa));
                }

                case 2 -> {
                    System.out.println("\n--- Update Student GPA ---");
                    System.out.print("Enter Student ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());

                    System.out.print("Enter New GPA: ");
                    double newGpa = Double.parseDouble(scanner.nextLine().trim());

                    dao.updateStudentGpa(id, newGpa);
                }

                case 3 -> {
                    System.out.println("\n--- Delete Student ---");
                    System.out.print("Enter Student ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());

                    dao.deleteStudent(id);
                }

                case 4 -> {
                    System.out.println("\n--- All Student Records ---");
                    List<Student> students = dao.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No records found.");
                    } else {
                        for (Student s : students) {
                            System.out.println(s);
                        }
                    }
                }

                case 5 -> {
                    System.out.println("\nExiting application. Goodbye!");
                    running = false;
                }

                default -> System.out.println("[INVALID] Choice out of range.");
            }
        }

        scanner.close();
    }
}
