<?php
require "conn.php";
header("Content-Type: application/json");
$providerdata = array(); 
$sql = "select providerid,firstname,lastname,dob,gender,addressline1,addressline2,city,state,zip,emailid,contact,speciality from provider order by firstname";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($providerid,$firstname,$lastname,$dob,$gender,$addressline1,$addressline2,$city,$state,$zip,$emailid,$contact,$speciality);
while($stmt->fetch()){
 $temp = [
 'providerid'=>$providerid,
 'firstname'=>$firstname,
 'lastname'=>$lastname,
 'dob'=>$dob,
 'gender'=>$gender,
 'addressline1'=>$addressline1,
 'addressline2'=>$addressline2,
 'city'=>$city,
 'state'=>$state,
 'zip'=>$zip,
 'emailid'=>$emailid,
 'contact'=>$contact,
 'speciality'=>$speciality
 ];
 array_push($providerdata, $temp);
}
$stmt->close();
echo json_encode($providerdata);
?>