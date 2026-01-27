@echo off
echo ================================
echo Starting a build with Maven...
echo ================================

call mvn clean install

if errorlevel 1 (
    echo Maven build error. Aborting service initialization.
    pause
    exit /b 1
)
echo ================================
echo Build completed successfully!
echo Starting services...
echo ================================

start "service-client" java -jar service-client/target/service-client-0.0.1-SNAPSHOT.jar
start "service-transaction" java -jar service-transaction/target/service-transaction-0.0.1-SNAPSHOT.jar
start "service-transaction-processor" java -jar service-transaction-processor/target/service-transaction-processor-0.0.1-SNAPSHOT.jar
echo ================================
echo All services have been initiated.
echo ================================
pause
