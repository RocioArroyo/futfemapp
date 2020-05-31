<?php
require "conn.php";
$user_name= $_REQUEST['username'];
$user_pass= $_REQUEST['password'];
$mysql_query="SELECT usu_email, usu_password FROM USUARIO WHERE usu_email='$user_name' AND usu_password='$user_pass'";
$result=mysqli_query($conn,$mysql_query);
if (mysqli_num_rows($result)==1){
	echo "LOGIN OK";
}else{
	echo "LOGIN FAIL";
}
mysqli_close($conn);
?>