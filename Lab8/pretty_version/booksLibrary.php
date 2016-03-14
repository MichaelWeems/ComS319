<?php

class Test {

	private shelves = array();
	private shelfcount =  0;
	private showLib = false;
	
	function __construct() {
		self::$shelfcount += 1;
	}

	public function addShelf($shelf) {
		array_push($this->shelves, $shelf);
		self::shelfcount += 1;
	}
	
	this.isBookAvailable = function(name) {

		for (i = 0; i < this.shelfcount; i++) {
			for (j = 0; j < this.shelves[i].bookcount; j++) {
				if ( this.shelves[i].books[j].name == name ) {
					//console.log( "The book " + name + " is available on " + this.shelves[i].name );
					return "The book " + name + " is available on " + this.shelves[i].name;
				}
			}
		}
		//console.log("The book, " + name + ", is not available");
		return "The book, " + name + ", is not available";
	}
	
	public function hideLibrary() {
		echo "Hide Library";
	}
	
	public function showLibrary() {
		echo "Hide Library";
	}

}


?>