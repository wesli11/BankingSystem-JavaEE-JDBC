<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
     <center> 		<legend>cuenta con saldo minimo</legend><br>

		<form action="FrontController?option=doCuentasSaldoMinimo" method="post">
			Introduce nota mínima:<input type="text" name="numeroCuenta"/><br/><br/>
			<input type="submit" value="mostrar"/>
		</form>
	</center>

</body>
</html>