<?php
require "conn.php";
$user_name= $_POST['username'];
$mysql_query="SELECT usu_email, usu_password FROM USUARIO WHERE usu_email='$user_name'";
$usuario=array();
$result=mysqli_query($conn,$mysql_query);

if (mysqli_num_rows($result)==1){
	while($fila=mysqli_fetch_array($result)) {
		$username=$fila['usr_email'];
		$password=$fila['usr_password'];

		$usuarios[]=array('usr_email'=>$username, 'usr_password'=>$password);
	}
	$json_string=json_encode($usuarios);
	echo $json_string;
}else{
	echo "EMAIL FAIL";
}
mysqli_close($conn);
?>