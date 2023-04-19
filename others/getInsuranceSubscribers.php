<?php
require "conn.php";

$id = $_GET["id"];

header("Content-Type: application/json");

$sql_query = "SELECT 
    p.patientid, 
    p.firstname,
    p.lastname, 
    p.gender,
    p.dob,
    p.emailid,
    p.contact,
    p.addressline1,
    p.addressline2,
    p.city,
    p.state,
    p.zip,
    pi.expiry
FROM patient p, patientinsurance pi 
WHERE p.patientid=pi.patientid 
    AND pi.status='Active' 
    AND pi.insurerid='$id';";

try {
    $results = mysqli_query($conn, $sql_query);

    $coverages = [];
    if (mysqli_num_rows($results) > 0) {
        while ($data = mysqli_fetch_assoc($results)) {
            $coverage = [
                "patientId" => $data["patientid"],
                "firstName" => $data["firstname"],
                "lastName" => $data["lastname"],
                "gender" => $data["gender"],
                "dob" => $data["dob"],
                "emailId" => $data["emailid"],
                "contact" => $data["contact"],
                "address1" => $data["addressline1"],
                "address2" => $data["addressline2"],
                "city" => $data["city"],
                "state" => $data["state"],
                "zip" => $data["zip"],
                "expiry" => $data["expiry"],
            ];

            array_push($coverages, $coverage);
        }

        echo json_encode($coverages);
    } else {
        echo json_encode($coverages);
    }
} catch (Exception $e) {
    $error = ["error" => $e->getMessage()];
    echo json_encode($error);
}
?>
