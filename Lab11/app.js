
var app = angular.module("libApp", ['ngRoute'])

.config(["$routeProvider", function($routeProvider,$locationProvider){
    $routeProvider.when('/library', {templateUrl:"template_library.html", controller:"libController"})
    .when('/book', {templateUrl:"template_book.html", controller:"bookController"})
    .otherwise({redirectTo:'/library'});
}])

.controller("startController", function($scope, $location){
    var lib = new Library();
    lib.fillShelves();
    $scope.shelves = [];
    $scope.book = new Book();
    for(i = 0; i < 10; i++){
        $scope.shelves.push({shelf1:lib.getBooks(0)[i], shelf2:lib.getBooks(1)[i], shelf3:lib.getBooks(2)[i]})
    }
})

.controller("libController", function($scope, $location){  
    $scope.load = function(novel){
        $scope.book = novel;
        console.log($scope.book.name+" : "+$scope.book.copies+" Copies lib controller");
        $location.path("/book");
    }
})

.controller("bookController", function($scope, $location){
//    console.log($scope.book.name+" : "+$scope.book.copies+" Copies book controller");
    $scope.back = function(){
        $location.path("/library");
    }
});

//Library
function Library(){
    this.name = "Library";
    this.shelves = [new Shelf(), new Shelf(), new Shelf()];
    this.books = [];
    
    this.getBooks = function(shelf){
      return this.shelves[shelf].books;  
    };
		
    this.fillShelves = function(){
		for(i = 0; i < 10; i++){
            this.books.push(new Book());
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
//Shelf
var s = 0;
function Shelf(){
    ++s;
	this.name = "Shelf "+s; 
    this.books = [];
	};
//Book
var b = 0;
function Book(){
	this.copies = 0;
	this.name = "Book "+ (++b);
    
    this.rent = function() {--this.copies};
    this.rtrn = function() {++this.copies};
};
