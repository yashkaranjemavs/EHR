<?php
require "conn.php";

$visitid = $_GET["visitid"];

header("Content-Type: application/json");

$sql_query = "SELECT DISTINCT
	V.vdate,
    V.vtime,
    V.status as 'appointmentstatus',
    V.patientnotes,
    V.providernotes,
    V.symptoms,
    V.diagnosis,
    AC.chargeid,
    AC.charges,
    AC.patientpayment,
    AC.insurancecoverage,
    AC.balance,AC.status as 'paymentstatus',
    I.`name` as 'insurancename',
    PR.firstname as 'providerfirstname',
    PR.lastname as 'providerlastname',
    P.firstname as 'patientfirstname',
    P.lastname as 'patientlastname'
FROM visits V 
JOIN patient P
ON V.patientid = P.patientid
LEFT OUTER JOIN patientinsurance PI 
ON P.patientid=PI.patientid AND PI.status='Active'
LEFT OUTER JOIN insurancecompany I
ON PI.insurerid=I.insurerid
LEFT OUTER JOIN provider PR
ON V.providerid = PR.providerid
LEFT OUTER JOIN accountcharges AC
ON V.visitid=AC.visitid
WHERE V.visitid='$visitid';";

$results = mysqli_query($conn, $sql_query);

if (mysqli_num_rows($results) > 0) {
    while ($data = mysqli_fetch_assoc($results)) {
        $visit = [
            "vdate" => $data["vdate"],
            "vtime" => $data["vtime"],
            "appointmentstatus" => $data["appointmentstatus"],
            "patientnotes" => $data["patientnotes"],
            "providernotes" => $data["providernotes"],
            "symptoms" => $data["symptoms"],
            "diagnosis" => $data["diagnosis"],
            "chargeid" => $data["chargeid"],
            "charges" => $data["charges"],
            "patientpayment" => $data["patientpayment"],
            "insurancecoverage" => $data["insurancecoverage"],
            "balance" => $data["balance"],
            "paymentstatus" => $data["paymentstatus"],
            "insurancename" => $data["insurancename"],
            "providerfirstname" => $data["providerfirstname"],
            "providerlastname" => $data["providerlastname"],
            "patientfirstname" => $data["patientfirstname"],
            "patientlastname" => $data["patientlastname"],
        ];
        
        echo json_encode($visit);
    }
} else {
    $error = ["error" => "Appointment not found"];
    echo json_encode($error);
}
?>