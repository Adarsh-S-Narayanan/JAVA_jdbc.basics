package com.example.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    /* =========================================================================
     *  If the database or table does not exist, you can create them using
     * =========================================================================
     * 
     * String createDbSQL = "CREATE DATABASE IF NOT EXISTS jdbcdemo;";
     * String createTableSQL = """
     *     CREATE TABLE IF NOT EXISTS students (
     *         id INT AUTO_INCREMENT PRIMARY KEY,
     *         name VARCHAR(100) NOT NULL,
     *         email VARCHAR(100) UNIQUE NOT NULL,
     *         gpa DOUBLE NOT NULL
     *     );
     * """;
     * 
     * Example method to execute creation:
     * public void initializeDatabase() {
     *     try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
     *          Statement stmt = conn.createStatement()) {
     *         stmt.executeUpdate(createDbSQL);
     *         stmt.executeUpdate("USE jdbcdemo;");
     *         stmt.executeUpdate(createTableSQL);
     *     } catch (SQLException e) {
     *         e.printStackTrace();
     *     }
     * }
     * ========================================================================= */

    // INSERT 
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, gpa) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setDouble(3, student.getGpa());
            pstmt.executeUpdate();
            System.out.println("[SUCCESS] Student inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateStudentGpa(int id, double newGpa) {
        String sql = "UPDATE students SET gpa = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newGpa);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("[SUCCESS] Student ID " + id + " updated successfully!");
            } else {
                System.out.println("[NOT FOUND] No student found with ID " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("[SUCCESS] Student ID " + id + " deleted successfully!");
            } else {
                System.out.println("[NOT FOUND] No student found with ID " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT (READ)
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, name, email, gpa FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getDouble("gpa")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
