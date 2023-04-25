<?php
require "conn.php";
header("Content-Type: application/json");
$labname = array(); 
$sql = "select name from laboratory";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($name);
while($stmt->fetch()){
 $temp = [
 'name'=>$name
 ];
 array_push($labname, $temp);
}
echo json_encode($labname);
?>