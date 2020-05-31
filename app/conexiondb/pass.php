<?php
require "conn.php";
$user_name= $_POST['username'];
$mysql_query="SELECT usu_email, usu_password FROM USUARIO WHERE usu_email='$user_name'";
$usuario=array();
$result=mysqli_query($conn,$mysql_query);

if (mysqli_num_rows($result)==1){
	while($fila=mysqli_fetch_array($result)) {
		$username=$fila['usu_email'];
		$password=$fila['usu_password'];

		$usuarios[]=array('usu_email'=>$username, 'usu_password'=>$password);
	}
	$json_string=json_encode($usuarios);
	echo $json_string;
}else{
	echo "EMAIL FAIL";
}
mysqli_close($conn);
?>