@echo off
rem parameters: 
rem 1: .g4 / .in / GrammarName filename

rem Add the antlr.jar to the environment classpath
call set_classpaths.bat >nul
rem compile the antlr grammar rules
call antlr4 %1.g4
rem compile the java files
call compile *.java
rem run the lexer on the input file
call grun %1 start -gui < %1.in
call grun %1 start -tree < %1.in
