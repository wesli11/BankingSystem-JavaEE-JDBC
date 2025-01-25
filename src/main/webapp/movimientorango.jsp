<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Banca</h1>
	<fieldset>
		<legend>Movimientos En Rango de fechas</legend><br>
		<form action="FrontController?op=doMovimientoRango" method="post">
			<label for="dni">Dni</label>
			<input type="number" name="dni">
			<label for="fecha">Fecha Ini</label>
			<input type="date" name="fecha1">
			<label for="fecha">Fecha Fin</label>
			<input type="date" name="fecha2">
			<input type="submit" value="Consultar">
			<br><br>
		</form>
	</fieldset>
	<p align="right"><a href="FrontController?op=toMenu">Regresar</a></p>

</body>
</html>