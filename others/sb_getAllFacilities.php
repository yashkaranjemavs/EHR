<?php
require "conn.php";
header("Content-Type: application/json");
$type=$_POST['type'];
$facilitydata = array();
if($type=='Laboratory')
{
	$sql = "select name,addressline1,addressline2,city,state,zip,emailid,contact from laboratory order by name";
	$stmt = $conn->prepare($sql);
	$stmt->execute();
	$stmt->bind_result($name,$addressline1,$addressline2,$city,$state,$zip,$emailid,$contact);
	while($stmt->fetch())
	{
		$temp = [
		'name'=>$name,
		'addressline1'=>$addressline1,
		'addressline2'=>$addressline2,
		'city'=>$city,
		'state'=>$state,
		'zip'=>$zip,
		'emailid'=>$emailid,
		'contact'=>$contact
	];
		array_push($facilitydata, $temp);
	}
	$stmt->close();
	echo json_encode($facilitydata);
}
else if($type=='Pharmacy')
{
	$sql = "select name,addressline1,addressline2,city,state,zip,emailid,contact from pharmacy order by name";
	$stmt = $conn->prepare($sql);
	$stmt->execute();
	$stmt->bind_result($name,$addressline1,$addressline2,$city,$state,$zip,$emailid,$contact);
	while($stmt->fetch())
	{
		$temp = [
		'name'=>$name,
		'addressline1'=>$addressline1,
		'addressline2'=>$addressline2,
		'city'=>$city,
		'state'=>$state,
		'zip'=>$zip,
		'emailid'=>$emailid,
		'contact'=>$contact
	];
		array_push($facilitydata, $temp);
	}
	$stmt->close();
	echo json_encode($facilitydata);
}
else if($type=='Insurance')
{
	$sql = "select name,addressline1,addressline2,city,state,zip,emailid,contact from insurancecompany order by name";
	$stmt = $conn->prepare($sql);
	$stmt->execute();
	$stmt->bind_result($name,$addressline1,$addressline2,$city,$state,$zip,$emailid,$contact);
	while($stmt->fetch())
	{
		$temp = [
		'name'=>$name,
		'addressline1'=>$addressline1,
		'addressline2'=>$addressline2,
		'city'=>$city,
		'state'=>$state,
		'zip'=>$zip,
		'emailid'=>$emailid,
		'contact'=>$contact
	];
		array_push($facilitydata, $temp);
	}
	$stmt->close();
	echo json_encode($facilitydata);
}

?>