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
			    $diagnosis=$data['diagnosis'];
			    $symptoms=$data['symptoms'];
			    $providernotes= $data['providernotes'];
			    $patientnotes= $data['patientnotes'];
			    $vdate=$data['vdate'];
			    $vtime=$data['vtime'];
				//print(json_encode($visitdetails));
			}
		} 
		else 
		{
		    $diagnosis="Not Assigned";
			$symptoms="Not Assigned";
			$providernotes = $data['providernotes'];
			$patientnotes = $data['patientnotes'];
		}
		$sql_query = "select * from labtests where visitid=$visitid";
	    $results = mysqli_query($conn, $sql_query);
		if (mysqli_num_rows($results) > 0) 
		{
			if($data2 = mysqli_fetch_assoc($results))
			{
			    $testname=$data2['testname'];
			    $testreport=$data2['testreport'];
			}
		} 
		else 
		{
			$testname="Not Assigned";
			$testreport="Not Assigned";
		}
		$sql_query = "select * from medications where visitid=$visitid";
	    $results = mysqli_query($conn, $sql_query);
		if (mysqli_num_rows($results) > 0) 
		{
			if($data3 = mysqli_fetch_assoc($results))
			{
			    $medications=$data3['medications'];
			}
		} 
		else 
		{
			$medications="Not Assigned";
		}
		$sql_query = "select firstname, lastname from patient where patientid=(select patientid from visits where visitid=$visitid)";
	    $results = mysqli_query($conn, $sql_query);
		if (mysqli_num_rows($results) > 0) 
		{
			if($data4 = mysqli_fetch_assoc($results))
			{
			    $firstname=$data4['firstname'];
			    $lastname=$data4['lastname'];
			}
		}
		$sql_query = "select * from accountcharges where visitid=$visitid";
	    $results = mysqli_query($conn, $sql_query);
		if (mysqli_num_rows($results) > 0) 
		{
			if($data5 = mysqli_fetch_assoc($results))
			{
			    $charges=$data5['charges'];
			}
		} 
		else 
		{
			$charges="Not Assigned";
		}
		$visitdetails = array("visitid" => $visitid, "firstname" => $firstname, "lastname" => $lastname, "symptoms" => $symptoms, "diagnosis" => $diagnosis, "patientnotes" => $patientnotes, "providernotes" => $providernotes, "testname" => $testname, "testreport" => $testreport, "medications" => $medications, "charges" => $charges, "vdate" =>$vdate, "vtime"=>$vtime);
        print(json_encode($visitdetails));
?>