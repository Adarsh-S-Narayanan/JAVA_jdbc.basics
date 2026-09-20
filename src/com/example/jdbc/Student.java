package com.example.jdbc;

public class Student {
    private int id;
    private String name;
    private String email;
    private double gpa;

    public Student(int id, String name, String email, double gpa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gpa = gpa;
    }

    public Student(String name, String email, double gpa) {
        this.name = name;
        this.email = email;
        this.gpa = gpa;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    @Override
    public String toString() {
        return String.format("Student [ID=%2d | Name=%-15s | Email=%-22s | GPA=%.2f]", id, name, email, gpa);
    }
}
