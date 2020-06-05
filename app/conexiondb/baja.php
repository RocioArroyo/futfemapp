<?php
require "conn.php";
$user_name= $_REQUEST['username'];
$consulta="SELECT * FROM USUARIO WHERE usu_email='$user_name'";
$comprobar=mysqli_query($conn, $comprobar);
if (mysqli_num_rows($comprobar)==1) {
	$mysql_query="DELETE FROM USUARIO WHERE usu_email='$user_name";
	$result=mysqli_query($conn,$mysql_query);
	if ($result) {
	      echo "BAJA OK";
	} else {
	      echo "BAJA FAIL";
	}
} else {
	echo "BAJA FAIL";
}
mysqli_close($conn);
?>