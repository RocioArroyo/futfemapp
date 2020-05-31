<?php
require "conn.php";
mysqli_set_charset($conn, 'utf8');
$mysql_query="SELECT * FROM EQUIPO ORDER BY EQU_PUNTOS DESC";
$result=mysqli_query($conn,$mysql_query);
$json_array=array();
if (mysqli_num_rows($result)>0) {
	while($fila=mysqli_fetch_assoc($result)) {
		$rowArray['equ_id']=$fila['equ_id'];
		$rowArray['equ_nombre']=$fila['equ_nombre'];
		$rowArray['equ_puntos']=$fila['equ_puntos'];
		$rowArray['equ_par_ganados']=$fila['equ_par_ganados'];
		$rowArray['equ_par_empatados']=$fila['equ_par_empatados'];
		$rowArray['equ_par_perdidos']=$fila['equ_par_perdidos'];
		$rowArray['equ_goles_favor']=$fila['equ_goles_favor'];
		$rowArray['equ_goles_contra']=$fila['equ_goles_contra'];

		array_push($json_array, $rowArray);
	}
	echo json_encode($json_array);
}else{
	echo "CLASIFICACION FAIL";
}
mysqli_close($conn);
?>
