<?php
header("Content-Type: application/json");
	require "conn.php";
    $testname = $_POST["testname"];
	$labname = $_POST["labname"];
	$visitid = $_POST["visitid"];
	$status="Pending";
	$report="Pending";
	$tdate=date("Y-m-d");
	$sql_query2="select laboratoryid from laboratory where name='$labname'";
	$results2 = mysqli_query($conn, $sql_query2);
	if(mysqli_num_rows($results2) > 0)
	{
		if($data = mysqli_fetch_assoc($results2))
		{
			$labid = $data['laboratoryid'];
			$sql_query = "insert into labtests(laboratoryid,visitid,testname,testreport,tdate,status) values($labid,$visitid,'$testname','$report','$tdate','$status')";
			if(mysqli_query($conn, $sql_query))
			{
				$sql_query1 = "SELECT * FROM labtests WHERE visitid=$visitid limit 1;";
				$results1 = mysqli_query($conn, $sql_query1);
				if (mysqli_num_rows($results1) > 0) 
				{
					if($data = mysqli_fetch_assoc($results1))
					{
						$labtestdetails = array("visitid" => $data['visitid'], "testname" => $data['testname'],"testreport" => $data['testreport']);
						print(json_encode($labtestdetails));
					}
				}
				else 
				{
					$error = array("error" => "didnt insert");
					echo json_encode($error);
				} 
			}
		}
		else
		{
			$error = array("error" => "didnt find the lab");
			echo json_encode($error);
		}
	}
	else
	{
		$error = array("error" => "didnt find the lab");
		echo json_encode($error);
	}
?>