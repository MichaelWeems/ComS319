@echo off
rem Add the antlr.jar to the environment classpath
call setup.bat >nul
rem compile the antlr grammar rules
call antlr4 %1.g4
rem compile the java files
javac %1.java
rem run the lexer on the input file
call grun %1 tokens -tokens < %1.in
