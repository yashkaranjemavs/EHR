<?php
	require "conn.php";
	header("Content-Type: application/json");
	$visitid=$_POST["visitid"];
        $sql_query = "select * from visits where visitid=$visitid";
	    $results = mysqli_query($conn, $sql_query);
		if (mysqli_num_rows($results) > 0) 
		{
			if($data = mysqli_fetch_assoc($results))
			{
				$visitdetails = array("visitid" => $data['visitid'], "diagnosis" => $data['diagnosis'], "symptoms" => $data['symptoms'],"providernotes" => $data['providernotes']);
				print(json_encode($visitdetails));
			}
		} 
		else 
		{
			$error = array("error" => "something went wrong");
			echo json_encode($error);
		} 
?>