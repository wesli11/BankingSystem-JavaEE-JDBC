package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Client;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import repository.EmployeeRepository;
import repository.EmployeeRepositoryImpl;
import service.ClienteService;
import service.ClienteServiceImpl;
import service.EmployeeService;
import service.EmployeeServiceImpl;

/**
 * Servlet implementation class AltaClienteAdm
 */
@WebServlet("/AltaClienteAdm")
public class AltaClienteAdm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService service;

    @Override
    public void init() throws ServletException {
    	EmployeeRepository repository = new EmployeeRepositoryImpl(); // Iniciamos el repositorio
        ClientRepository cRepository = new ClientRepositoryImpl(); // Iniciamos el repositorio
  service = new EmployeeServiceImpl(repository,cRepository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      int dni=Integer.parseInt(request.getParameter("dni"));
	      String nombre=request.getParameter("nombre");
	      String direccion=request.getParameter("direccion");
	      int telefono=Integer.parseInt(request.getParameter("telefono"));
	      service.registrarCliente(new Client(dni, nombre, direccion, telefono));
	}

}
