Lab 8:

Setting up the sql database:
  Before you can run this application, you must first
  set up your sql database to accomodate it. In the
  folder titled 'sql' you will find a sql script named
  '319_lab8.sql'. Run this script on the schema you
  wish to use, and it will create all of the tables
  needed for this application, prepopulated.
  
  If you have any issues, we have also included a 
  drop tables script to drop all of the tables
  in our application.

Connection parameters:
  To allow our php scripts to interact with the database,
  you will need to configure the php script titled
  'connection_parameters.php'. This file simply contains
  the database, username, password, and schema you wish
  to connect to as variables.
  This php file is located under the folder titled 'php'.
  
Logging in to the Application:
  Once everything is set up, you may begin testing our 
  application. The login is prepopulated with a 
  Librarian's login info for ease of access.
  
  The login page consists of a signup form and a login
  form, both independant even though they are adjacent.
  When you sign up, it will automatically log you in.
  
  For reference, here are some login credentials:
    user: student
    pass: a
    librarian?: no
    
    user: librarian
    pass: a
    librarian?: yes
    
Interfacing with the application:
  
  There is a logout button in the top right corner of the page
  that will destroy the current server session.

  Student: 
    Borrow book section: type in the title of the book, and you
                         will borrow it if it is available
                         
    Return book section: click the text labeled 'Return' at the bottom
                         right of a book row and the book will be returned
                         to the library
                         
    Display shelves section: click the 'show shelves' button to display all
                             shelves. click the text 'get details' next
                             to any book to have the details displayed in
                             a separate section below the shelves. You may
                             have to scroll down to see this.
  
  
  Librarian:
    Add book section: type in the name of a book and its author, then click
                      'add book'. The book will be added to the list of
                      books below the button.
                      In the section 'add copy of an existing book', click the
                      text 'add new copy' after a book to add a new copy of that
                      book to the library.
                      
   Display shelves section:  click the 'show shelves' button to display all
                             shelves. click the text 'get details' next
                             to any book to have the details displayed in
                             a separate section below the shelves. You may
                             have to scroll down to see this. Also, if you click
                             the text 'delete' next to a book in the display section,
                             it will delete that copy of the book from the library.
                            
   View loans section: type in the name of a student and click 'view loans' to
                       see a list of books the student has checked out, as well
                       as the books they have previously returned.
  