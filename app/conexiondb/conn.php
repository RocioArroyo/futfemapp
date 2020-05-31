<?php
$db_name="futfemapp";
$mysql_username="futfemADMIN";
$mysql_password="FUTFEMAPP";
$server_name="futfemapp.com";
$conn= mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name);
if(!$conn){
echo "CONNECT FAIL";
}
?>