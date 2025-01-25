package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import service.ClienteService;
import service.ClienteServiceImpl;

/**
 * Servlet implementation class CuentasController
 */
@WebServlet("/ValidarController")
public class ValidarController extends HttpServlet {
	  private ClienteService service;

	    @Override
	    public void init() throws ServletException {
	        ClientRepository repository = new ClientRepositoryImpl(); // Iniciamos el repositorio
	        service = new ClienteServiceImpl(repository); // Inicializamos el servicio
	    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int numeroCuenta=Integer.parseInt(request.getParameter("numeroCuenta"));
		HttpSession sesion=request.getSession();
		Account cuenta=service.validarCuenta(numeroCuenta);
		if(cuenta!=null) {
			sesion.setAttribute("numeroCuenta", cuenta.getIdAccount());
		}
	}

}
