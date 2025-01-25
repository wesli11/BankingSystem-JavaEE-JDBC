<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center> 		<legend>Cliente cuentas</legend><br>

		<form action="FrontController?op=doClienteCuentas" method="post">
			Introduce Dni:<input type="number" name="dni"/><br/><br/>
			<input type="submit" value="mostrar"/>
		</form>
	</center>

</body>
</html>