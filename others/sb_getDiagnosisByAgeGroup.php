<?php
require "conn.php";
header("Content-Type: application/json");
$digdata = array();
$age1=$_POST['age1'];
$age2=$_POST['age2'];
$sql = "SELECT diagnosis, COUNT(patientid) FROM visits WHERE patientid IN 
                            (SELECT patientid FROM patient WHERE DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(),dob)), '%Y')+0 <=$age2
                            AND DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(),dob)), '%Y')+0 >=$age1) 
                            GROUP BY diagnosis ORDER BY COUNT(patientid)";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($diagnosis, $total);
while($stmt->fetch())
{
 $temp = ['diagnosis'=>$diagnosis,'total'=>$total];
 array_push($digdata, $temp);
}
echo json_encode($digdata);
?>