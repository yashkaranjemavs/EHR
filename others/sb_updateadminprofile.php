<?php
header("Content-Type: application/json");
	require "conn.php";
	$emailid = $_POST["emailid"];
	$password = $_POST["password"];
        $sql_query = "update admin set PASSWORD='$password' WHERE emailid='$emailid' limit 1;";
	    if(mysqli_query($conn, $sql_query))
		{
			$sql_query1 = "SELECT * FROM admin WHERE emailid='$emailid' limit 1;";
			$results1 = mysqli_query($conn, $sql_query1);
	
			if (mysqli_num_rows($results1) > 0) 
			{
				if($data = mysqli_fetch_assoc($results1))
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
		} 
		else
		{
			echo "ERROR: Could not able to execute $sql_query. " . mysqli_error($conn);
		}

?>