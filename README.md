# Java JDBC & JavaFX Student Management System

A Java application demonstrating **JDBC (Java Database Connectivity)** with a MySQL backend (XAMPP). It includes both a **Command Line Interface (CLI)** and a **JavaFX Graphical User Interface (GUI)** to perform full CRUD (Create, Read, Update, Delete) operations on a `students` database.

---

## 📁 Project Structure

```
d:\code\class\jdbc\
├── database/                         # Raw XAMPP MySQL database files (jdbcdemo)
│   ├── db.opt
│   ├── students.frm
│   └── students.ibd
├── schema.sql                        # SQL setup script for MySQL
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

## 🛠️ Complete XAMPP & MySQL Database Setup Guide

### 1. Installing & Starting XAMPP
1. Download and install **XAMPP** from [Apache Friends](https://www.apachefriends.org/). (Installed at `D:\Rdbms` or `C:\xampp`).
2. Open the **XAMPP Control Panel**.
3. Next to **Apache** and **MySQL**, click **Start**.
   - Ensure the MySQL status turns green (running on port `3306`).

---

### 2. Setting Up the Database

#### Option A: Creating a New Database via phpMyAdmin (Web UI)
1. Open your browser and go to `http://localhost/phpmyadmin/`.
2. **Where to write SQL queries / create table:**
   - Click on the **SQL** tab at the top menu bar.
   - Open the [`schema.sql`](file:///d:/code/class/jdbc/schema.sql) file from this repository, copy its contents, and paste them into the SQL query box.
   - Click **Go** (bottom right).
3. Alternatively, you can click the **Import** tab at the top, choose the [`schema.sql`](file:///d:/code/class/jdbc/schema.sql) file from your folder, and click **Import**.

#### Option B: Creating Database via MySQL Command Line / Terminal
If you prefer using the command line:
1. Open Command Prompt or PowerShell in XAMPP's MySQL bin directory (`D:\Rdbms\mysql\bin` or `C:\xampp\mysql\bin`):
   ```cmd
   cd D:\Rdbms\mysql\bin
   mysql -u root
   ```
2. Execute the script directly:
   ```sql
   SOURCE d:/code/class/jdbc/schema.sql;
   ```

#### Option C: Using the Pre-packaged `database/` Folder in XAMPP
This repository includes the raw MySQL database folder [`database/`](file:///d:/code/class/jdbc/database).
1. Stop the MySQL service in XAMPP Control Panel.
2. Copy the [`database/`](file:///d:/code/class/jdbc/database) folder from this repository.
3. Paste it into your XAMPP MySQL data directory:
   - For XAMPP installed at `D:\Rdbms`: `D:\Rdbms\mysql\data\` (rename folder to `jdbcdemo` if needed, so path becomes `D:\Rdbms\mysql\data\jdbcdemo`).
   - For standard XAMPP at `C:\xampp`: `C:\xampp\mysql\data\jdbcdemo`.
4. Restart the MySQL service in XAMPP Control Panel.

---

### 3. Application Configuration

The database credentials in [`DatabaseConnection.java`](file:///d:/code/class/jdbc/src/com/example/jdbc/DatabaseConnection.java) are set to default XAMPP credentials:
- **URL**: `jdbc:mysql://localhost:3306/jdbcdemo`
- **Username**: `root`
- **Password**: `""` *(empty string by default)*

---

## 🚀 How to Run the Application

### Prerequisites: Java Development Kit (JDK)
Ensure **JDK 17 or higher** is installed and configured in your environment (`JAVA_HOME` and `PATH`). Verify using:
```bash
java -version
javac -version
```

### Option 1: Command Line Interface (CLI)

#### Quick Run (Batch Script - Windows)
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

You can inspect and manage the database records created by either the CLI or GUI using:
- **phpMyAdmin**: Start Apache & MySQL in XAMPP and open `http://localhost/phpmyadmin/`.
- **MySQL Workbench / DBeaver**: Connect to `localhost:3306` with user `root` (no password).

