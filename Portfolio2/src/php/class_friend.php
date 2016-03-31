<?php

class Friend {

	private $username;
    private $pic;

	function __construct($name) {
		$this->username = $name;
        $this->set_pic();
	}
    
    
    
    public function set_pic(){
        include 'connection.php';
		
		$sql = "select picPath from Group8_users where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
        while($row = $res->fetch_assoc()){
			$this->pic = $row["picPath"];
		};
        
		include 'connection_close.php';
    }
    
    
    public function get_pic(){
        return $this->pic;
    }
   

	public function get_username() {
		return $this->username;
	}
}

?>