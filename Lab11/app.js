//
// Allows js to be in its own file and
// html to be in its own file
//
var app = angular.module('myApp', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider,$locationProvider) {
  $routeProvider
    .when("/", {templateUrl: "template_library.html", controller: "LibraryControl"})
    .when("/Book", {templateUrl: "template_book.html", controller: "BookControl"})
    .otherwise({redirectTo: "/"});
}]);

app.controller('LibraryControl', function($scope, $location, selectedbook) {
    
  $scope.shelves = [{1:"Book1",2:"Book3",3:"Book6",4:"Book5",5:"Book9",6:"Book7",7:"Book2"},
                    {1:"Book4",2:"Book5",3:"Book2",4:"Book6",5:"Book4",6:"Book8",7:"Book6"},
                    {1:"Book3",2:"Book5",3:"Book7",4:"Book4",5:"Book2",6:"Book7",7:"Book3"}];
    
  var library = [];

  /* Convert Shelves to rows */
  count = 0;
  for (var ele in $scope.shelves[0]){
      count++;
  }

  for (i=1;i<=count;i++){
      var row = {};
      for (j=0; j < $scope.shelves.length; j++){
          row[j+1] = $scope.shelves[j][i];
      }
      library.push(row);
  }
  /****************************/
    
  $scope.getLibrary = function(){
      return library;
  }
  
  $scope.bookdetails = function(addr, name){
      selectedbook.setName(name);
      $location.path(addr);
  }
  
});

app.controller('BookControl', function($scope, $location, selectedbook) {
  $scope.name = selectedbook.getName();
  $scope.checkedout = "Not Checked out";
  $scope.author = "author";
    
  $scope.seeLibrary = function(addr){
      selectedbook.setName("");
      $location.path(addr);
  }
});

app.factory('selectedbook', function(){
    var book = {};
    var name = "";
    book.setName = function(str){
        name = str;
    }
    book.getName = function(){
        return name;
    }
    return book;
});

