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
		<legend>Desbloquear cuenta</legend><br>
		<form action="FrontController?op=doDesBloquearCuenta" method="post">
		
			<label for="dni">Fecha Ini</label>
			<input type="number" name="dni">
			
			<input type="submit" value="Guardar">
			<br><br>
		</form>
	</fieldset>
	<p align="right"><a href="FrontController?op=toMenu">Regresar</a></p>

</body>
</html>