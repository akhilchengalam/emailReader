@echo off

CALL docker-compose down -v
CALL mvn clean install -DskipTests
CALL docker-compose up --build

rem echo
rem echo
rem echo Welcome to Email Reader!
rem echo
rem echo

rem :while
rem echo
rem echo
rem echo This Batch File can do follwing this for you: 
rem echo 0. Exit this window
rem echo 1. Create, Build and Start Maven Project / Application as Docker Container
rem echo 2. Stop all running Containers
rem echo 2. Build Maven Project app_version.jar
rem echo 3. Re-build and Start container
rem echo 4. Delete All
rem echo 6. Stop Specific Container


rem set /p userChoice="What do you want to do? Enter Choice Number: "

rem if %userChoice% LEQ 0 (
rem 	goto :exitPrompt 
rem	)
rem if %userChoice% GEQ 2 (
rem 	goto :exitPrompt
rem 	)
rem if %userChoice% EQU 1 (
rem 	set /p userChoice="Do You Want to delete previously created networks"
rem 	goto :createBuildStart
rem 	)
rem if %userChoice% EQU 2 (
rem 	goto :buildJar
rem 	)
rem if %userChoice% EQU 2 (
rem 	goto :stopContainers
rem 	)

rem :createBuildStart
rem :buildJar
rem set /p unitTests="Do you want to perform unit tests? (y/n) :"
rem echo %unitTests%=="y" OR %unitTests%=="Y" 
rem if /I "%unitTests%" EQU "Y" (
rem 	echo Building Maven Project Jar please wait...
rem 	CALL mvn clean install
rem 	if %userChoice% EQU 3 goto :while
rem ) else (
rem 	echo Skipping Tests and Building Maven Project Jar please wait...
rem 	CALL mvn clean install -DskipTests
rem 	if %userChoice% EQU 3 goto :while
rem )

rem :stopContainers

rem :exitPrompt
rem echo Thank You!
rem timeout 3




rem rem docker-compose down --rmi local|all
rem rem docker-compose stop