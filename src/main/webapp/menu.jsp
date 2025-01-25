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
<title>Menu cliente</title>
</head>
<body>
	<h1>Banca</h1>
	<fieldset>
	<legend>Menu cliente</legend>
		<p>- <a href="FrontController?op=toIngresar">Ingresar</a></p>
		<p>- <a href="toExtraer">Extraer</a></p>
		<p>- <a href="FrontController?op=toMovimiento">Consultar saldo y movimientos</a></p>
		<p>- <a href="FrontController?op=toTransferencia">Transferencia</a></p><br><br>
	<legend>Menu Admin</legend>
		<p>- <a href="FrontController?op=toAltaCliente">Alta Cliente</a></p>
		<p>- <a href="FrontController?op=toAltaCuenta">Alta Cuenta</a></p>
		<p>- <a href="FrontController?op=toSaldoAvgMasAlto">Saldo Promedio Anual Max</a></p>
		<p>- <a href="FrontController?op=toClienteCuentas">Cliente cuentas</a></p>
		<p>- <a href="FrontController?op=toCuentasSaldoMinimo">Cuentas con saldo minimo</a></p>
		<p>- <a href="FrontController?op=toMovimientoRango">Obtener Mov. Rango</a></p>
		<p>- <a href="FrontController?op=toSinMovsDesde">Sin Movimiento desde</a></p>
		<p>- <a href="FrontController?op=toBloquearCuenta">Bloquear cuenta</a></p>
		<p>- <a href="FrontController?op=toDesBloquearCuenta">Desbloquear cuenta</a></p>
		<p>- <a href="FrontController?op=toAnularTransferencia">Anular Transferencia</a></p>
		
	</fieldset>
	<br>
	
</body>
</html>