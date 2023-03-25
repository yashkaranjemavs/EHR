<?php
require "conn.php";

$laboratoryid = $_POST["laboratoryid"];

header("Content-Type: application/json");

$sql_query = "select LT.testname,P.firstname,P.lastname,LT.testreport,LT.laboratoryid,LT.visitid FROM labtests LT JOIN visits V on LT.visitid = V.visitid JOIN patient P ON P.patientid=V.patientid WHERE laboratoryid = $laboratoryid and LT.status ='Pending';";

$results = mysqli_query($conn, $sql_query);

$testsList = [];
if (mysqli_num_rows($results) > 0) {
while ($data = mysqli_fetch_assoc($results)) {$test = ["testname" => $data["testname"],
			"firstname" => $data["firstname"],
			"lastname" => $data["lastname"],
			"testreport" => $data["testreport"],
			"laboratoryid" => $data["laboratoryid"],
			"visitid" => $data["visitid"]];
array_push($testsList, $test);
}
echo json_encode($testsList);
} else {
echo json_encode($testsList);
}
?>