<?php
header("Content-Type: application/json");
	require "conn.php";
    $charges = $_POST["charges"];
	$patientpay= $_POST["patientpay"];
	$visitid = $_POST["visitid"];
	if($charges==$patientpay)
	{
		$balance=0;
	}
	else
	{
		$balance=$charges-$patientpay;
	}
	$status="Pending";
        $sql_query = "insert into accountcharges(visitid,charges,patientpayment,insurancecoverage,status,balance)
		values($visitid,$charges,$patientpay,0,'$status',$balance)";
	    if(mysqli_query($conn, $sql_query))
		{
			$sql_query1 = "SELECT * FROM accountcharges WHERE visitid=$visitid limit 1;";
			$results1 = mysqli_query($conn, $sql_query1);
	
			if (mysqli_num_rows($results1) > 0) 
			{
				if($data = mysqli_fetch_assoc($results1))
				{
					$chargedetails = array("visitid" => $data['visitid'], "charges" => $data['charges'], "status" => $data['status']);
					print(json_encode($chargedetails));
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