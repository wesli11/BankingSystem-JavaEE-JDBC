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
		<legend>Anular Transferencia</legend><br>
		<form action="FrontController?op=doAnularTransferencia" method="post">
			<label for="idTransferencia">Dni</label>
			<input type="number" name="idTransferencia">
			<label for="fecha">Fecha Ini</label>
			<input type="date" name="fecha1">
			<input type="submit" value="Anular">
			<br><br>
		</form>
	</fieldset>
	<p align="right"><a href="FrontController?op=toMenu">Regresar</a></p>

</body>
</html>