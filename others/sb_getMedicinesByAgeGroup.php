<?php
require "conn.php";
header("Content-Type: application/json");
$meddata = array();
$age1=$_POST['age1'];
$age2=$_POST['age2'];
$sql = "SELECT medications, COUNT(visitid) as total FROM medications WHERE visitid IN
                            (SELECT visitid FROM visits WHERE patientid IN 
                            (SELECT patientid FROM patient WHERE DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(),dob)), '%Y')+0 <=$age2
                            AND DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(),dob)), '%Y')+0 >=$age1))  
                            GROUP BY medications";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($medications, $total);
while($stmt->fetch())
{
 $temp = ['medications'=>$medications,'total'=>$total];
 array_push($meddata, $temp);
}
echo json_encode($meddata);
?>