Portfolio 2:

Installing Dependencies: (NOTE: simply coying the node_modules folder
                                into the root directory of the project
                                should be sufficient)
                                
  To run this application, you must first install Node.js.
  You can download here: https://nodejs.org/en/
  
  After installing Node.js on your system, you must use it
  to install the packages necessary to run this application.
  From a terminal or command prompt, navigate to the root
  directory of this project. Next, enter the command:
    
    $ npm install
    
  Running this command will download the dependancies for
  this application into the folder nodes_modules. If this
  folder does not exist, it will create itself.
  
Setting up the Database:
  This application utilizes a sql database, so you will need
  to set up a database using the sql script found at this location:
      src/sql/CreateTables_Portfolio2.sql
  Once you have created the database, you will need to add your
  login connection parameters for the database into the php script
  called connection_parameters.php, located here:
      src/php/connection_parameters.php
  
Running Application:
  As this app utilizes php, we will need to run it through
  an apache server. If you have uWamp, simply place the
  contents of the root directory (after installing the
  dependencies) into the 'www' folder. You are now set
  to run the application.
  
Please let us know if you have any issues running this application
Group 8:
  Michael Weems
  Zach Wild