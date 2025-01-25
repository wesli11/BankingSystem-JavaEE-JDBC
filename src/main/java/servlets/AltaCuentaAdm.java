package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Client;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import repository.EmployeeRepository;
import repository.EmployeeRepositoryImpl;
import service.EmployeeService;
import service.EmployeeServiceImpl;

/**
 * Servlet implementation class AltaCuentaAdm
 */
@WebServlet("/AltaCuentaAdm")
public class AltaCuentaAdm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService service;

    @Override
    public void init() throws ServletException {
    	EmployeeRepository repository = new EmployeeRepositoryImpl(); // Iniciamos el repositorio
        ClientRepository cRepository = new ClientRepositoryImpl(); // Iniciamos el repositorio
  service = new EmployeeServiceImpl(repository,cRepository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      int numeroCuenta=Integer.parseInt(request.getParameter("numeroCuenta"));
	      double saldo=Double.parseDouble(request.getParameter("saldo"));
	      String tipocuenta=request.getParameter("tipocuenta");
	      int dni=Integer.parseInt(request.getParameter("dni"));

	      service.registrarCuenta(dni,new Account(numeroCuenta, saldo, tipocuenta));
	}


}
