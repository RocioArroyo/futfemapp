<?php
require "conn.php";
$user_name= $_REQUEST['username'];
$user_pass= $_REQUEST['password'];
$mysql_query="INSERT INTO USUARIO (usu_email, usu_password, usu_admin) VALUES ('$user_name', '$user_pass', 0)";
$result=mysqli_query($conn,$mysql_query);
if ($result) {
      echo "REGISTRO OK";
} else {
      echo "REGISTRO FAIL";
}
mysqli_close($conn);
?>