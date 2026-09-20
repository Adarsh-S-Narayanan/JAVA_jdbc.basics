@echo off
echo Building and Running Java JDBC Demo...
echo.

if not exist bin (
    mkdir bin
)

echo Compiling Java source files...
javac -d bin -cp "lib/*" src/com/example/jdbc/*.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Running Main Application...
echo.
java -cp "bin;lib/*" com.example.jdbc.Main

echo.
pause
