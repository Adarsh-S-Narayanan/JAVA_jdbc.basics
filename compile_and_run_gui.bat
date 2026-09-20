@echo off
echo Building and Running JavaFX GUI Application...
echo.

if not exist bin (
    mkdir bin
)

echo Compiling Java source files with JavaFX...
javac --module-path lib --add-modules javafx.controls -d bin -cp "lib/*" src/com/example/jdbc/*.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Launching JavaFX GUI...
echo.
java --module-path lib --add-modules javafx.controls -cp "bin;lib/*" com.example.jdbc.GuiMain

echo.
pause
