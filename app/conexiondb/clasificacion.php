<?php
require "conn.php";
$mysql_query="SELECT * FROM EQUIPO ORDER BY EQU_POSICION DESC";
$clasificacion=array();
$result=mysqli_query($conn,$mysql_query);
if (mysqli_num_rows($result)>0) {
	while($fila=mysqli_fetch_array($result)) {
		$id=$fila['equ_id'];
		$nombre=$fila['equ_nombre'];
		$posicion=$fila['equ_posicion'];
		$puntos=$fila['equ_puntos'];
		$parganado=$fila['equ_par_ganados'];
		$parempatado=$fila['equ_par_empatados'];
		$parperdido=$fila['equ_par_perdidos'];
		$golfavor=$fila['equ_goles_favor'];
		$golcontra=$fila['equ_goles_contra'];

		$clasificaciones[]=array('equ_id'=>$id, 'equ_nombre'=>$nombre, 'equ_posicion'=>$posicion, 'equ_puntos'=>$puntos, 'equ_par_ganados'=>$parganado, 'equ_par_empatado'=>$parempatado, 'equ_par_perdidos'=>$parperdido, 'equ_goles_favor'=>$golfavor, 'equ_goles_contra'=>$golcontra);
	}
	$json_string=json_encode($clasificaciones);
	echo $json_string;
}else{
	echo "CLASIFICACION FAIL";
}
?>