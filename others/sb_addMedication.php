<?php
header("Content-Type: application/json");
	require "conn.php";
    $medication = $_POST["medication"];
	$pharmacy = $_POST["pharmacy"];
	$visitid = $_POST["visitid"];
	$status="Pending";
	$sql_query2="select pharmacyid from pharmacy where name='$pharmacy'";
	$results2 = mysqli_query($conn, $sql_query2);
	if(mysqli_num_rows($results2) > 0)
	{
		if($data = mysqli_fetch_assoc($results2))
		{
			$pharmacyid = $data['pharmacyid'];
			$sql_query = "insert into medications(pharmacyid,visitid,medications,status) values($pharmacyid,$visitid,'$medication','$status')";
			if(mysqli_query($conn, $sql_query))
			{
				$sql_query1 = "SELECT * FROM medications WHERE visitid=$visitid limit 1;";
				$results1 = mysqli_query($conn, $sql_query1);
				if (mysqli_num_rows($results1) > 0) 
				{
					if($data = mysqli_fetch_assoc($results1))
					{
						$labtestdetails = array("visitid" => $data['visitid'], "medication" => $data['medications'],"status" => $data['status']);
						print(json_encode($labtestdetails));
					}
				}
				else 
				{
					$error = array("error" => "something went wrong");
					echo json_encode($error);
				} 
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
		$error = array("error" => "something went wrong");
		echo json_encode($error);
	}
?>