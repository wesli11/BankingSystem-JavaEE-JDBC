package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import repository.EmployeeRepository;
import repository.EmployeeRepositoryImpl;
import service.EmployeeService;
import service.EmployeeServiceImpl;

/**
 * Servlet implementation class DesbloquerCuentaAdm
 */
@WebServlet("/DesbloquerCuentaAdm")
public class DesbloquerCuentaAdm extends HttpServlet {
	private EmployeeService service;

    @Override
    public void init() throws ServletException {
    	EmployeeRepository repository = new EmployeeRepositoryImpl(); // Iniciamos el repositorio
        ClientRepository cRepository = new ClientRepositoryImpl(); // Iniciamos el repositorio
    service = new EmployeeServiceImpl(repository,cRepository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int dni=Integer.parseInt(request.getParameter("dni"));
	service.desbloquearCuenta(dni);
	}

}
