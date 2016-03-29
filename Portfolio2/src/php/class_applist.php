<?php
include 'class_app.php';

class AppList {

	private $applist;

	function __construct() {
        $this->applist = array();
        $this->setvars_fromDB();
	}
    
    public function setvars_fromDB(){
        include 'connection.php';
		
        $sql = "select name from Group8_apps;";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
            $this->applist[$row["name"]] = new App($row["name"]);
        }
        
        include 'connection_close.php';
    }
    
	public function get_applist() {
		return $this->applist;
	}  
}
?>