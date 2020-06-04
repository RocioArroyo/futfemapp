<?php
require "conn.php";
mysqli_set_charset($conn, 'utf8');
$mysql_query="SELECT DATE_FORMAT(P.PAR_FECHA_HORA, '%m-%d-%Y %H:%i') AS par_fecha_hora, P.PAR_GOLES_LOCAL AS par_goles_local, P.PAR_GOLES_VISITANTE AS par_goles_visitante, P.PAR_JORNADA AS par_jornada, EL.EQU_NOMBRE AS equ_nombre_loc, EV.EQU_NOMBRE AS equ_nombre_vis FROM PARTIDO P INNER JOIN EQUIPO EL ON (P.PAR_EQU_LOC=EL.EQU_ID) INNER JOIN EQUIPO EV ON (P.PAR_EQU_VIS=EV.EQU_ID) ORDER BY P.PAR_JORNADA ASC";
$result=mysqli_query($conn,$mysql_query);
$json_array=array();
if (mysqli_num_rows($result)>0) {
	while($fila=mysqli_fetch_assoc($result)) {
		$rowArray['par_fecha_hora']=$fila['par_fecha_hora'];
		$rowArray['par_goles_local']=$fila['par_goles_local'];
		$rowArray['par_goles_visitante']=$fila['par_goles_visitante'];
		$rowArray['par_jornada']=$fila['par_jornada'];
		$rowArray['equ_nombre_loc']=$fila['equ_nombre_loc'];
		$rowArray['equ_nombre_vis']=$fila['equ_nombre_vis'];

		array_push($json_array, $rowArray);
	}
	echo json_encode($json_array);
}else{
	echo "JORNADA FAIL";
}
mysqli_close($conn);
?>
