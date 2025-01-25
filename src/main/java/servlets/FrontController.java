package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String op=request.getParameter("op");
	       String url="acceso.jsp";
	       
	       switch(op) {
	       case "doAlta":
			   request.getRequestDispatcher("alta.jsp").include(request, response);
	           request.getRequestDispatcher("AltaAction").include(request, response);
	           url="inicio.html";
	           break;
	       case "doEliminar":
	    	   request.getRequestDispatcher("EliminarAction").include(request, response);
	    	  // request.getRequestDispatcher("ListadoAction").include(request, response);
	           url="contactos.jsp";
	           break;  
	           
	       case "doListado":
	    	   request.getRequestDispatcher("ListadoAction").include(request, response);
	           url="contactos.jsp";
	           break;
	       case "toDatos":
				url="datos.html";
				break;
			case "doAcceso":
		    	request.getRequestDispatcher("ValidarController").include(request, response);
				url="menu.jsp";
				break;
			case "doIngresar":
		    	   request.getRequestDispatcher("IngresarAction").include(request, response);
				url="menu.jsp";
				break;
			case "toIngresar":
				url="ingreso.jsp";
				break;
			case "doMovimiento":
		    	request.getRequestDispatcher("MovimientosAction").forward(request, response);
				return;
		    	//url="movimientos.jsp";
				//break;
			case "toMovimiento":
				url="seleccionmovimientos.html";
				break;
			case "doTransferencia":
		    	   request.getRequestDispatcher("TransferenciaAction").include(request, response);
				url="menu.jsp";
				break;
			case "toTransferencia":
				url="transferencia.jsp";
				break;
            //FUN. ADMIN.
			case "doAltaCliente":
		    	   request.getRequestDispatcher("AltaClienteAdm").include(request, response);
				url="menu.jsp";
				break;
			case "toAltaCliente":
				url="altacliente.jsp";
				break;
			case "doAltaCuenta":
		    	   request.getRequestDispatcher("AltaCuentaAdm").include(request, response);
				url="menu.jsp";
				break;
			case "toAltaCuenta":
				url="altacuenta.jsp";
				break;
			case "doSaldoAvgMasAlto":
		    	   request.getRequestDispatcher("SaldoAvgMasAlto").forward(request, response);
				//url="saldoavgmasalto.jsp";
				return;
			case "toSaldoAvgMasAlto":
				url="saldoavgmasalto.html";
				break;
			case "doClienteCuentas":
		    	   request.getRequestDispatcher("ClienteCuentas").include(request, response);
				url="clientecuentas2.jsp";
				break;
			case "toClienteCuentas":
				url="clientecuentas.jsp";
				break;
			case "doCuentasSaldoMinimo":
		    	   request.getRequestDispatcher("SaldoAvgMasAlto").include(request, response);
				url="cuentasaldominimo.jsp";
				break;
			case "toCuentasSaldoMinimo":
				url="cuentasaldominimo2.jsp";
				break;
			case "doMovimientoRango":
		    	   request.getRequestDispatcher("MovimientoPorClienteRango").include(request, response);
				url="movimientorango2.jsp";
				break;
			case "toMovimientoRango":
				url="movimientorango.jsp";
				break;
			case "toSinMovsDesde":
				url="sinmovsdesde.html";
				break;
			case "doSinMovsDesde":
		    	   request.getRequestDispatcher("ClientesSinMovsDesde").forward(request, response);
                  return;
		    	   // url="sinmovsdesde2.jsp";
				//break;
			case "doBloquearCuenta":
		    	   request.getRequestDispatcher("BloquearCuentaAdm").include(request, response);
				url="menu.jsp";
				break;
			case "toBloquearCuenta":
				url="bloquear.jsp";
				break;
			case "doDesBloquearCuenta":
		    	   request.getRequestDispatcher("DesbloquerCuentaAdm").include(request, response);
				url="menu.jsp";
				break;
			case "toDesBloquearCuenta":
				url="desbloquear.jsp";
				break;
			case "doAnularTransferencia":
		    	   request.getRequestDispatcher("AnularTransferenciaAdm").include(request, response);
				url="menu.jsp";
				break;
			case "toAnularTransferencia":
				url="anulartransferencia.jsp";
				break;
	       }
	       request.getRequestDispatcher(url).forward(request, response);
	}

}
