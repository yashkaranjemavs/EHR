<?php

    require "conn.php";
    $firstname = $_POST["firstname"];
    $lastname = $_POST["lastname"];
    $gender = $_POST["gender"];
    $dob = $_POST["dob"];
    $emailid = $_POST["emailid"];
    $password = $_POST["password"];
    $contact = $_POST["contact"];
    $addressline1 = $_POST["addressline1"];
    $addressline2 = $_POST["addressline2"];
    $city = $_POST["city"];
    $state = $_POST["state"];
    $zip = $_POST["zip"];

    header("Content-Type: application/json");

    $sql_query = "INSERT INTO patient (firstname, lastname, gender, dob, emailid, password, contact, addressline1, addressline2, city, state, zip) VALUES ('$firstname', '$lastname', '$gender', '$dob', '$emailid', '$password', '$contact', '$addressline1', '$addressline2', '$city', '$state', '$zip');";

    $results = mysqli_query($conn, $sql_query);


    if ($results > 0) {
        $success = array("success"=>"Registration Successful");
        echo json_encode($success);
    } else {
        $error = array("error"=>"Registration Unsuccessful");
        echo json_encode($error);
    }

?>