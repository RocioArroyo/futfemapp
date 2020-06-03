<?php
require "conn.php";
mysqli_set_charset($conn, 'utf8');
$user_name= $_POST['username'];
$mysql_query="SELECT * FROM EQUIPO ORDER BY EQU_PUNTOS DESC";
$fav="SELECT fav_equ FROM FAVORITOS WHERE FAV_USU='$user_name'";
$result=mysqli_query($conn,$mysql_query);
$json_array=array();
$favoritos=array();
if (mysqli_num_rows($result)>0) {
	$rfav=mysqli_query($conn, $fav);
	if (mysqli_num_rows($rfav)>0) {
		while ($ffav=mysqli_fetch_assoc($rfav)) {
			$rowArray['fav_equ']=$ffav['fav_equ'];
			array_push($favoritos, $rowArray);
		}
	}
	$cfav=count($favoritos);
	while($fila=mysqli_fetch_assoc($result)) {
		$rowArray['equ_id']=$fila['equ_id'];
		$rowArray['equ_nombre']=$fila['equ_nombre'];
		$rowArray['equ_puntos']=$fila['equ_puntos'];
		$rowArray['equ_par_ganados']=$fila['equ_par_ganados'];
		$rowArray['equ_par_empatados']=$fila['equ_par_empatados'];
		$rowArray['equ_par_perdidos']=$fila['equ_par_perdidos'];
		$rowArray['equ_goles_favor']=$fila['equ_goles_favor'];
		$rowArray['equ_goles_contra']=$fila['equ_goles_contra'];

		if ($cfav==0) {
			$rowArray['fav']="0";
		} else {
			foreach ($favoritos as $favorito) {
				if ($fila['equ_id']==$favorito['fav_equ']) {
					$rowArray['fav']="1";
					break;
				} else {
					$rowArray['fav']="0";
				}
			}
		}

		array_push($json_array, $rowArray);
	}
	echo json_encode($json_array);
}else{
	echo "CLASIFICACION FAIL";
}
mysqli_close($conn);
?>

