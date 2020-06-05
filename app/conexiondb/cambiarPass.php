<?php
require "conn.php";
$user_name= $_REQUEST['username'];
$user_pass= $_REQUEST['password'];
$consulta="SELECT * FROM USUARIO WHERE usu_email='$user_name'";
$comprobar=mysqli_query($conn, $consulta);
if (mysqli_num_rows($comprobar)==1) {
	$mysql_query="UPDATE USUARIO SET usu_password='$user_pass' WHERE usu_email='$user_name'";
	$result=mysqli_query($conn,$mysql_query);
	if ($result) {
	      echo "CAMBIO OK";
	} else {
	      echo "CAMBIO FAIL";
	}
} else {
	echo "CAMBIO FAIL";
}
mysqli_close($conn);
?>