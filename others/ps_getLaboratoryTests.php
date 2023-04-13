<?php
require "conn.php";

$laboratoryid = $_POST["laboratoryid"];

header("Content-Type: application/json");

$sql_query = "select LT.laboratoryid,V.visitid,LT.tdate,LT.testreport,LT.testname, LT.testid, P.patientid,P.firstname,P.lastname,P.gender,P.dob,P.city,P.state,P.zip,P.contact,P.emailid,P.addressline1,P.addressline2 FROM labtests LT JOIN visits V on LT.visitid = V.visitid JOIN patient P ON P.patientid=V.patientid WHERE laboratoryid = $laboratoryid and LT.status ='Pending';";


$results = mysqli_query($conn, $sql_query);

$testsList = [];
if (mysqli_num_rows($results) > 0) {
while ($data = mysqli_fetch_assoc($results)) {$test = ["testname" => $data["testname"],	
            "testid" => $data["testid"],
            "laboratoryid" => $data["laboratoryid"],
             "visitid" => $data["visitid"],
              "tdate" => $data["tdate"],
              "testreport" => $data["testreport"],
            "patientid" => $data["patientid"],
			"firstname" => $data["firstname"],
			"lastname" => $data["lastname"],
			"gender" => $data["gender"],
			"dob" => $data["dob"],
			"city" => $data["city"],
			"state" => $data["state"],
			"zip" => $data["zip"],
			"contact" => $data["contact"],
			"emailid" => $data["emailid"],
			"address1" => $data["addressline1"],
			"address2" => $data["addressline2"]];
array_push($testsList, $test);
}
echo json_encode($testsList);
} else {
echo json_encode($testsList);
}
?>