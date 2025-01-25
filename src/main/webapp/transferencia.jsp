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
		<legend>Transferencia</legend><br>
		<form action="FrontController?op=doTransferencia" method="post">
			<label for="fcuenta">N° cuenta destino</label>
			<input type="number" name="numeroCuentaDestino">
			<label for="fmonto">Cantidad</label>
			<input type="number" name="cantidad">
			<input type="submit" value="Transferir">
			<br><br>
		</form>
	</fieldset>
	<p align="right"><a href="FrontController?op=toMenu">Regresar</a></p>
</body>
</html>