<?php
require "conn.php";

$patientid = $_GET["patientid"];

header("Content-Type: application/json");

$sql_query = "SELECT patientid, visitid, vdate, vtime, patientnotes, providernotes, symptoms, diagnosis, status FROM visits WHERE patientid = '$patientid' ORDER BY vdate DESC;";

$result = mysqli_query($conn, $sql_query);

if (mysqli_num_rows($result) > 0) {
    $visits = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $visit = [
            "patientid" => $row["patientid"],
            "visitid" => $row["visitid"],
            "vdate" => $row["vdate"],
            "vtime" => $row["vtime"],
            "patientnotes" => $row["patientnotes"],
            "providernotes" => $row["providernotes"],
            "symptoms" => $row["symptoms"],
            "diagnosis" => $row["diagnosis"],
            "status" => $row["status"],
        ];
        array_push($visits, $visit);
    }
    echo json_encode($visits);
} else {
    $error = ["error" => "Not Found"];
    echo json_encode($error);
}

?>