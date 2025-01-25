<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<title>Acceder</title>
</head>
<body>
	<h1>Banca</h1>
	<fieldset>
		<legend>Acceder</legend><br>
		<form action="FrontController?op=doAcceso" method="post">
			<label for="ftema">Numero de cuenta:</label>
			<input type="number" name="numeroCuenta">
			<input type="submit" value="Entrar">
			<br><br>
		</form>
	</fieldset>
	
</body>
</html>