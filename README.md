# Java JDBC & JavaFX Student Management System

A Java application demonstrating **JDBC (Java Database Connectivity)** with a MySQL backend (XAMPP). It includes both a **Command Line Interface (CLI)** and a **JavaFX Graphical User Interface (GUI)** to perform full CRUD (Create, Read, Update, Delete) operations on a `students` database.

---

## 📁 Project Structure

```
d:\code\class\jdbc\
├── lib/
│   ├── mysql-connector-j-9.2.0.jar   # MySQL JDBC Driver
│   └── (JavaFX SDK JARs if separate)  # JavaFX Libraries
├── src/
│   └── com/example/jdbc/
│       ├── DatabaseConnection.java   # JDBC Connection Manager (MySQL)
│       ├── Student.java              # Student Model / Entity Class
│       ├── StudentDAO.java           # Data Access Object (CRUD SQL Logic)
│       ├── Main.java                 # CLI Application Entry Point
│       └── GuiMain.java              # JavaFX GUI Application Entry Point
├── compile_and_run.bat              # Batch script to compile & run CLI
├── compile_and_run_gui.bat          # Batch script to compile & run GUI
├── JDBC_Masterclass_Guide.pdf       # Masterclass Reference Guide
└── README.md                        # Project documentation
```

---

## 🛠️ Prerequisites & Setup

### 1. Java Development Kit (JDK)
Ensure **JDK 17 or higher** (e.g., OpenJDK 25) is installed and configured in your system environment path (`JAVA_HOME` and `PATH`).

Check installation in command prompt:
```bash
java -version
javac -version
```

### 2. MySQL Database (XAMPP)
1. Install and open **XAMPP Control Panel**.
2. Start the **MySQL** module (and optionally **Apache** if using phpMyAdmin).
3. Database connection configuration in `DatabaseConnection.java`:
   - **URL**: `jdbc:mysql://localhost:3306/jdbcdemo`
   - **Username**: `root`
   - **Password**: `""` *(empty password by default)*
4. Ensure the database `jdbcdemo` exists. The application will automatically create the database and the `students` table if they do not exist when launched.

---

## 🚀 How to Run

### Option 1: Command Line Interface (CLI)

#### Quick Run (Batch Script - Windows)
Double-click `compile_and_run.bat` or run it from PowerShell / CMD:
```powershell
.\compile_and_run.bat
```

#### Manual Compilation & Execution
```powershell
# 1. Compile source files into bin directory
javac -d bin -cp "lib/*" src/com/example/jdbc/*.java

# 2. Run the CLI Main class
java -cp "bin;lib/*" com.example.jdbc.Main
```

---

### Option 2: Graphical User Interface (GUI)

#### Quick Run (Batch Script - Windows)
Double-click `compile_and_run_gui.bat` or run it from PowerShell / CMD:
```powershell
.\compile_and_run_gui.bat
```

#### Manual Compilation & Execution
```powershell
# 1. Compile Java source files with JavaFX modules
javac --module-path lib --add-modules javafx.controls -d bin -cp "lib/*" src/com/example/jdbc/*.java

# 2. Launch the JavaFX GUI Application
java --module-path lib --add-modules javafx.controls -cp "bin;lib/*" com.example.jdbc.GuiMain
```

---

## 🗄️ Database Inspection & Management

You can inspect the database records created by either the CLI or GUI using:
- **phpMyAdmin**: Start Apache in XAMPP and go to `http://localhost/phpmyadmin/`.
- **MySQL Workbench**: Connect to `localhost:3306` with user `root`.
