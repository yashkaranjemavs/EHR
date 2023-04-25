<?php
require "conn.php";

header("Content-Type: application/json");

$sql_query = "SELECT insurerid, name FROM insurancecompany ORDER BY name";

$result = mysqli_query($conn, $sql_query);

if (mysqli_num_rows($result) > 0) {
    $insurances = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $insurance = [
            "insurerid" => $row["insurerid"],
            "name" => $row["name"],
        ];
        array_push($insurances, $insurance);
    }
    echo json_encode($insurances);
} else {
    $error = ["error" => "Not Found"];
    echo json_encode($error);
}

?>