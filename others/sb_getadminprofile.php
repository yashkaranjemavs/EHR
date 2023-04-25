<?php
	require "conn.php";
    $emailid = $_POST["emailid"];
	header("Content-Type: application/json");
        $sql_query = "SELECT * FROM admin WHERE emailid='$emailid' limit 1;";
	    $results = mysqli_query($conn, $sql_query);
	
		if (mysqli_num_rows($results) > 0) 
		{
			if($data = mysqli_fetch_assoc($results))
			{
				$admindetails = array("emailid" => $data['emailid'], "password" => $data['password']);
				print(json_encode($admindetails));
			}
		} 
		else 
		{
			$error = array("error" => "something went wrong");
			echo json_encode($error);
		} 
?>