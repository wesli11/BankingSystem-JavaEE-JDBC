<!DOCTYPE html>
<html>
<head>
<style>
table, td {
	font-family: serif;
	border: 1px solid #dddddd;
	border-collapse: collapse;
	text-align: left;
}

th {
	font-family: serif;
	border: 1px solid #dddddd;
	border-collapse: collapse;
	background-color: #dddddd;
	text-align: center;
}

a {
	color:LIGHTSLATEGRAY;
}

h1 {
	color:DARKSALMON;
}

legend {
	color:INDIANRED;
}
</style>
<meta charset="ISO-8859-1">
<title>Ingresar</title>
</head>
<body>
	<h1>Banca</h1>
	<fieldset>
		<legend>Alta Cliente</legend><br>
		<form action="FrontController?op=doAltaCliente" method="post">
			<label for="fcuenta">DNI</label>
			<input type="number" name="dni">
			<label for="fmonto">Nombre</label>
			<input type="text" name="nombre">
		    <label for="fmonto">Direccion</label>
			<input type="text" name="direccion">
			<label for="fmonto">Telefono</label>
			<input type="text" name="telefono">
				<input type="submit" value="Guardar">
			<br><br>
		</form>
	</fieldset>
	<p align="right"><a href="toMenu">Regresar</a></p>
</body>
</html>