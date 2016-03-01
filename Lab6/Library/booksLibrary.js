// ---------------------------------------------------------------------------
// Classes

// -------------
// Library Class
function Library(){
	this.shelves = {};
	this.shelfcount = 0;
	this.showLib = false;
	this.addShelf = function(shelf) {this.shelves[this.shelfcount++] = shelf;};
	this.getShelf = function(index) {return this.shelves[index]};

	// returns which shelf this book is on
	this.isBookAvailable = function(name) {

		for (i = 0; i < this.shelfcount; i++) {
			for (j = 0; j < this.shelves[i].bookcount; j++) {
				if ( this.shelves[i].books[j].name == name ) {
					console.log( "The book " + name + " is available on " + this.shelves[i].name );
					return;
				}
			}
		}
		console.log("The book is not available");
	};
	// returns how many of this book are in the library
	this.getDetails = function(name) {
		total = 0;
		
		for (i = 0; i < this.shelfcount; i++) {
			total += this.shelves[i].bookcountArr[name];
		}
		console.log("The Library has " + total + " copies of " + name); 
	};
};

// -------------
// Shelf Class
function Shelf() {
	this.books = {};
	this.name = "";
	this.bookcount = 0;
	this.bookcountArr = {};
	this.addBook = function(book) {this.books[this.bookcount++] = book;};
}

// -------------
// Book Class
function Book(bookName) {
  this.name = bookName;
}


// ---------------------------------------------------------------------------
// Initialize the table

var lib = new Library();

var shelfArr = {};
for (var i = 0; i < 3; i++) {
	s = new Shelf();
	s.name = "Shelf " + i;
	lib.addShelf(s);
}

for (i = 0; i < 3; i++) {
	for (j = 0; j < 11; j++) {
		lib.getShelf(i).addBook(new Book("Book" + j));
		
		if ( lib.getShelf(i).bookcountArr["Book" + j] == undefined ) {
			lib.getShelf(i).bookcountArr["Book" + j] = 0;
		}
		lib.getShelf(i).bookcountArr["Book" + j]++;
	}
}

// ---------------------------------------------------------------------------
// handlers

$(document).ready(function() {
    $("#isAvailable").click( function() {
		text = $("#isAvailText").val();
		lib.isBookAvailable(text);
    });
	
	$("#listShelves").click( function() {
		for ( i = 0; i < lib.shelfcount; i++ ) {
			console.log(lib.shelves[i]);
		}
    });
	
	$("#showLibrary").click( function() {
		if (!lib.showLib) {
			lib.showLib = true;
			showLibrary();
			$("#showLibrary").attr("value","Hide the Library!");
		}
		else {
			lib.showLib = false;
			hideLibrary();
			$("#showLibrary").attr("value","Show the Library!");
		}
		
    });
	
}); // end of ready


// ---------------------------------------------------------------------------
// Create the Table content

function showLibrary() {
	mytable = $("<table border='2'></table>"); // creates DOM elements
	mytablebody = $('<tbody></tbody>'); 

	curr_row = $('<tr style="background-color:green"></tr>');
	for(col = 0; col < 3; col++) {
		curr_cell = $('<td></td>');
		curr_text = lib.getShelf(col).name;
		curr_cell.append(curr_text);
		curr_row.append(curr_cell);
	}
	mytablebody.append(curr_row); // appends arg to mytablebody

	for(row = 0; row < 11; row++) {
		curr_row = $('<tr></tr>');
		
		for(col = 0; col < 3; col++) {
			curr_cell = $('<td></td>');
			curr_text = '';
			if ( lib.getShelf(col).books[row] != undefined ) {
				curr_text = lib.getShelf(col).books[row].name;
			}
			curr_cell.append(curr_text);
			curr_row.append(curr_cell);
		  }
		  mytablebody.append(curr_row); // appends arg to mytablebody
	}
	mytable.append(mytablebody);
	mytable.insertBefore($('#tableEnd')); // real dom from document!
	
	$("td").click( function() {
		lib.getDetails($(this).text());
    });
}

function hideLibrary() {
	$("table").remove();
}