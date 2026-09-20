package com.example.jdbc;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class GuiMain extends Application {

    private final StudentDAO dao = new StudentDAO();
    private final TableView<Student> table = new TableView<>();
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    private final TextField idField = new TextField();
    private final TextField nameField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField gpaField = new TextField();

    private final Label statusLabel = new Label("Ready");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System - JavaFX");

        // --- TOP HEADER ---
        Label headerLabel = new Label("Student Management System");
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label subHeaderLabel = new Label("Connected via MySQL JDBC");
        subHeaderLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");

        VBox headerBox = new VBox(4, headerLabel, subHeaderLabel);
        headerBox.setPadding(new Insets(15, 20, 15, 20));
        headerBox.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");

        // --- TABLE VIEW ---
        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(220);

        TableColumn<Student, Double> gpaCol = new TableColumn<>("GPA");
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        gpaCol.setPrefWidth(90);

        table.getColumns().addAll(idCol, nameCol, emailCol, gpaCol);
        table.setItems(studentList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Populate table when selecting row
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                idField.setText(String.valueOf(newVal.getId()));
                nameField.setText(newVal.getName());
                emailField.setText(newVal.getEmail());
                gpaField.setText(String.valueOf(newVal.getGpa()));
            }
        });

        // --- FORM INPUTS ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(15));
        formGrid.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-radius: 6; -fx-background-radius: 6;");

        idField.setEditable(false);
        idField.setPromptText("Auto");
        idField.setStyle("-fx-background-color: #f1f5f9;");

        formGrid.add(new Label("ID:"), 0, 0);
        formGrid.add(idField, 1, 0);

        formGrid.add(new Label("Name:"), 0, 1);
        formGrid.add(nameField, 1, 1);

        formGrid.add(new Label("Email:"), 0, 2);
        formGrid.add(emailField, 1, 2);

        formGrid.add(new Label("GPA:"), 0, 3);
        formGrid.add(gpaField, 1, 3);

        // Make textfields take full width
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(60);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(col1, col2);

        // --- ACTION BUTTONS ---
        Button addBtn = new Button("Add Student");
        addBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        addBtn.setOnAction(e -> handleAdd());

        Button updateBtn = new Button("Update GPA");
        updateBtn.setStyle("-fx-background-color: #0d9488; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        updateBtn.setOnAction(e -> handleUpdate());

        Button deleteBtn = new Button("Delete Student");
        deleteBtn.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        deleteBtn.setOnAction(e -> handleDelete());

        Button clearBtn = new Button("Clear Form");
        clearBtn.setStyle("-fx-background-color: #64748b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        clearBtn.setOnAction(e -> clearInputs());

        HBox buttonBox = new HBox(8, addBtn, updateBtn, deleteBtn, clearBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        VBox rightPane = new VBox(15, formGrid, buttonBox);
        rightPane.setPadding(new Insets(15));
        rightPane.setPrefWidth(320);

        // --- CENTER CONTAINER ---
        SplitPane splitPane = new SplitPane(table, rightPane);
        splitPane.setDividerPositions(0.65);

        // --- STATUS BAR ---
        statusLabel.setStyle("-fx-text-fill: #475569; -fx-font-size: 12px;");
        HBox statusBar = new HBox(statusLabel);
        statusBar.setPadding(new Insets(8, 15, 8, 15));
        statusBar.setStyle("-fx-background-color: #f1f5f9; -fx-border-color: #cbd5e1; -fx-border-width: 1 0 0 0;");

        // --- MAIN LAYOUT ---
        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(splitPane);
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 900, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial data
        loadStudentData();
    }

    // =========================================================================
    //  DATABASE OPERATIONS (CRUD) - Interacting with StudentDAO
    // =========================================================================

    /**
     * READ / SELECT OPERATION:
     * Fetches all student records from MySQL via StudentDAO.getAllStudents()
     * and populates the TableView.
     */
    private void loadStudentData() {
        studentList.clear();
        // --- READ: Fetch list from MySQL database ---
        List<Student> students = dao.getAllStudents();
        studentList.addAll(students);
        setStatus("Loaded " + students.size() + " student records from database.");
    }

    /**
     * CREATE / INSERT OPERATION:
     * Reads form fields, instantiates a new Student object, and calls
     * StudentDAO.addStudent() to insert a new row into the database.
     */
    private void handleAdd() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String gpaStr = gpaField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || gpaStr.isEmpty()) {
            setStatus("[ERROR] Please fill in Name, Email, and GPA.", true);
            return;
        }

        try {
            double gpa = Double.parseDouble(gpaStr);
            // --- CREATE: Insert student into MySQL database ---
            dao.addStudent(new Student(name, email, gpa));
            
            // Refresh table and clear form
            loadStudentData();
            clearInputs();
            setStatus("[SUCCESS] Student '" + name + "' added successfully!");
        } catch (NumberFormatException e) {
            setStatus("[ERROR] GPA must be a valid number (e.g. 3.8).", true);
        } catch (Exception e) {
            setStatus("[ERROR] Failed to add student: " + e.getMessage(), true);
        }
    }

    /**
     * UPDATE OPERATION:
     * Takes the selected Student ID and new GPA, then calls
     * StudentDAO.updateStudentGpa() to modify the database record.
     */
    private void handleUpdate() {
        String idStr = idField.getText().trim();
        String gpaStr = gpaField.getText().trim();

        if (idStr.isEmpty() || gpaStr.isEmpty()) {
            setStatus("[ERROR] Select a student from table or enter ID and new GPA.", true);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double gpa = Double.parseDouble(gpaStr);
            // --- UPDATE: Modify student GPA in MySQL database ---
            dao.updateStudentGpa(id, gpa);
            
            // Refresh table and clear form
            loadStudentData();
            clearInputs();
            setStatus("[SUCCESS] Student ID " + id + " GPA updated to " + gpa);
        } catch (NumberFormatException e) {
            setStatus("[ERROR] ID and GPA must be valid numbers.", true);
        } catch (Exception e) {
            setStatus("[ERROR] Failed to update student: " + e.getMessage(), true);
        }
    }

    /**
     * DELETE OPERATION:
     * Takes the selected Student ID and calls StudentDAO.deleteStudent()
     * to remove the record from the database.
     */
    private void handleDelete() {
        String idStr = idField.getText().trim();

        if (idStr.isEmpty()) {
            setStatus("[ERROR] Select a student from table to delete.", true);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            // --- DELETE: Remove student row from MySQL database ---
            dao.deleteStudent(id);
            
            // Refresh table and clear form
            loadStudentData();
            clearInputs();
            setStatus("[SUCCESS] Student ID " + id + " deleted successfully!");
        } catch (NumberFormatException e) {
            setStatus("[ERROR] ID must be a valid number.", true);
        } catch (Exception e) {
            setStatus("[ERROR] Failed to delete student: " + e.getMessage(), true);
        }
    }

    private void clearInputs() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        gpaField.clear();
        table.getSelectionModel().clearSelection();
    }

    private void setStatus(String message) {
        setStatus(message, false);
    }

    private void setStatus(String message, boolean isError) {
        statusLabel.setText(message);
        if (isError) {
            statusLabel.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
        } else {
            statusLabel.setStyle("-fx-text-fill: #166534; -fx-font-weight: bold;");
        }
    }
}
