<?php

class App {

	private $name;
	private $description;
	private $location;
    
    private $html;
    private $html_location;

	function __construct($name) {
        $this->name = $name;
        $this->setvars_fromDB();
	}
    
    public function setvars_fromDB(){
        include 'connection.php';
		
        $sql = "select description, location, html_location from Group8_apps where name = '".$this->name."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
            $this->description = $row["description"];
            $this->location = $row["location"];
            //$this->html_location = $row["html_location"];
            $this->html_location = '..\\apps\\SightReader\\pages\\sightreader_app.html';
        }
        include 'connection_close.php';
        
        $this->html = file_get_contents($this->html_location);
    }
    
	public function set_name($name) {
		$this->name = $name;
	}	
	
	public function set_description($description) {
		$this->description = $description;
	}
	
	public function set_location($location) {
		$this->location = $location;
	}
    
    public function set_html($html) {
		$this->html = $html;
	}
    
    public function set_html_location($html_location) {
		$this->html_location = $html_location;
	}

    
	public function get_name() {
		return $this->name;
	}  
	
	public function get_description() {
		return $this->description;
	}

	public function get_location() {
		return $this->location;
	}
    
    public function get_html() {
		return $this->html;
	}
    
    public function get_html_location() {
		return $this->html_location;
	}
}
?>