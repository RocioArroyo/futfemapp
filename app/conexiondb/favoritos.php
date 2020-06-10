<?php
require "conn.php";
$user_name= $_REQUEST['user_name'];
$equ_id= $_REQUEST['equ_id'];
$consulta="SELECT * FROM FAVORITOS WHERE FAV_USU='$user_name' AND FAV_EQU='$equ_id'";
$result=mysqli_query($conn,$consulta);
if (mysqli_num_rows($result)==1){
	$borrar="DELETE FROM FAVORITOS WHERE FAV_USU='$user_name' AND FAV_EQU='$equ_id'";
	$resb=mysqli_query($conn,$borrar);
	if($borrar) {
		echo "FAVORITO ELIMINADO OK";
	} else {
		echo "FAVORITO ELIMINADO FAIL";
	}
}else if (mysqli_num_rows($result)==0) {
	$insertar="INSERT INTO FAVORITOS (FAV_EQU, FAV_USU) VALUES('$equ_id', '$user_name')";
	$resi=mysqli_query($conn,$insertar);
	if ($resi) {
		echo "FAVORITO INSERTADO OK";
	} else {
		echo "FAVORITO INSERTADO FAIL";
	}
} else {
	echo "FAVORITOS FAIL";
}
mysqli_close($conn);
?>