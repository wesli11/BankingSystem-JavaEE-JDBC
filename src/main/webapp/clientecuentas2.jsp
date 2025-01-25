<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*,model.*"%>
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
<title>Movimientos</title>
</head>
<body>
	<h1>Banca</h1>
	
	<br>
		<legend>Cliente Cuentas</legend>
			
				<table>
					 <tr>
						<th>Dni</th>
						<th>Nombre</th>
						<th>Phone</th>
						<th>Nº Cuenta </th>
					</tr>
			<%Client avgs=(Client)request.getAttribute("clientecuentas"); %>
						<tr>
							<td><%=avgs.getDni() %></td>
							<td><%=avgs.getName() %></td>
							<td><%=avgs.getPhone() %></td>
							<td><%=avgs.getAccounts() %></td>
						</tr>
					
	    		</table>	
			
			
		
		
		
		
			
		
	</fieldset>
	
<p align="right"><a href="FrontController?op=toMenu">Volver</a></p>
</body>
</html>