/**
 * 
 */
$(document).ready(function() {
	
	$('#txtfield').hide();
	
	createTable();
	
});



function createTable(){
	var lib = new Library();
	lib.fillShelves();
	
	myHeader = $("<table border='1'></table>");
	head = $('<thead></thead>');
	row = $('<tr></tr>');
	
	for(i = 0; i < 3; i++){
		var shelf = lib.shelves[i];
		cell = $('<th></th>');
		cell.css('width', '55px');
		cell.css('background-color', 'lightgreen')
		cell.append(shelf.name);
		row.append(cell);
	}
	head.append(row);
	myHeader.append(head);
	myHeader.insertAfter($('#header'));

	mytable = $("<table border='1'></table>");
	body = $('<tbody></tbody>');
	for(x = 0; x < 10; x++){
		row = $('<tr></tr>');
		for(y = 0; y < 3; y++){
			var shelf = lib.shelves[y];
			var book = shelf.books[x];
			cell = $('<td></td>');
			cell.css('width', '55px');
			cell.css('background-color','lightyellow');
			
			
			cell.append(book.name).hover(function(){
				$('legend').html($(this).html()+" Info");
				//
				update(book, shelf);
				$('#txtfield').show();
				console.log(book.name+": There are "+book.copies);
			}, function(){
				$('#txtfield').hide()}).click(function(){
				console.log("clicker: "+$(this).html());
				this.toggle = !this.toggle;
			    $(this).fadeTo('fast', this.toggle ? 0.4 : 1);
			    this.toggle ? book.rent() : book.rtrn();
			    update(book, shelf);
			});
			row.append(cell);
		}
		body.append(row);
	}
	
	mytable.append(body);
	mytable.insertBefore($('#books'));
	

	$('th').hover(function(event){
		$('legend').html($(this).html()+" Info");
//		$('#bookInfo').html($(this).html());//rework this
		$('#txtfield').show();
	}, function(){
		$('#txtfield').hide();
	});
	
//	$('td').hover(function(event){
//		$('legend').html($(this).html()+" Info");
//		$('#bookInfo').html($(this).copies);
//		$('#txtfield').show();
//	}, function(){
//		$('#txtfield').hide();
//	});

//	$('td').click(function(){
//		console.log("clicker: "+$(this).html());
//		this.toggle = !this.toggle;
//	    $(this).fadeTo('fast', this.toggle ? 0.4 : 1);
//	    
//	});
	function update(bk, sh){
		var mess = "";
		if(bk.copies == 0)
			mess = "This Book is not Available";
		$('#bookInfo').html(sh.name+":<br>There are "+bk.copies+" copies in the Library<br>"+mess);
	}
	
};

/**
 * Library
 */
function Library(){
	this.name = "Library";
	this.shelves = [new Shelf(), new Shelf(), new Shelf()];
	this.books = [];
	
	this.fillShelves = function(){
		for(i = 0; i < 10; i++){
			this.books.push(new Book);
		}
		
		for(j = 0; j < 3; j++){
			for(k = 0; k < 10; k++){
				var num = Math.floor(Math.random()* 10);
				this.shelves[j].books.push(this.books[num]);
				this.books[num].rtrn();
			};
		};
	};
};
/**
 * Shelf
 */
var s = 0;
function Shelf(){
	++s;
	this.name = "Shelf "+s; 
	this.books = [];
};

/**
 * Books
 */
var b = 0;
function Book(){
	this.copies = 0;
	this.name = "Book "+ (++b);
	
	this.rent = function() {--this.copies};
	
	this.rtrn = function() {++this.copies};
};








