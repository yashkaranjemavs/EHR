<?php
include "connection.php";
header("Content-Type: application/json");

$emailid = $_GET["emailid"];
$query = "SELECT * FROM pharmacy WHERE emailid = '$emailid';";
$result = mysqli_query($conn, $query);

if(mysqli_num_rows($result)>0){   
   while($row = mysqli_fetch_assoc($result)){
	   $pharmacy = array("emailid" => $row['emailid'], "name" => $row['name'], "contact" => $row['contact'], "addressline1" => $row['addressline1'], "addressline2" => $row['addressline2'], "city" => $row['city'], "state" => $row['state'], "zip" => $row['zip']);
	   echo json_encode($pharmacy);
   }	
}else{
	$error = array("error" => "No Pharmacy Found");
	echo json_encode($error);
}
?>