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
		<legend>Movimientos Rango de fechas</legend>
			
				<table>
						<tr>
						<th>Fecha</th>
						<th>Cantidad</th>
						<th>Tipo de operacion</th>
					</tr>
			<%List<Movements>movimientos=(List<Movements>)request.getAttribute("movimientorango"); %>
					<%for(Movements m:movimientos){ %>
						<tr>
							<td><%=m.getDateMovement() %></td>
							<td><%=m.getQuantity() %></td>
							<td><%=m.getOperation() %></td>
						</tr>
					
					<%} %>
						
				</table>	
			
			
		
		
		
		
			
		
	</fieldset>
	
<p align="right"><a href="toMenu">Volver</a></p>
</body>
</html>