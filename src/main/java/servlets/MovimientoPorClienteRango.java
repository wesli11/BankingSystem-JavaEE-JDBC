package servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import repository.EmployeeRepository;
import repository.EmployeeRepositoryImpl;
import service.ClienteService;
import service.ClienteServiceImpl;
import service.EmployeeService;
import service.EmployeeServiceImpl;

/**
 * Servlet implementation class MovimientoPorClienteRango
 */
@WebServlet("/MovimientoPorClienteRango")
public class MovimientoPorClienteRango extends HttpServlet {
	private EmployeeService service;

    @Override
    public void init() throws ServletException {
    	EmployeeRepository repository = new EmployeeRepositoryImpl(); // Iniciamos el repositorio
        ClientRepository cRepository = new ClientRepositoryImpl(); // Iniciamos el repositorio
    service = new EmployeeServiceImpl(repository,cRepository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int dni=Integer.parseInt(request.getParameter("dni"));
	      String fecha1=request.getParameter("fecha1");
	      String fecha2=request.getParameter("fecha2");
	      
 request.setAttribute("movimientorango", service.obtenerMovimientosPorClienteEnRango(dni,LocalDate.parse(fecha1),LocalDate.parse(fecha2)));
 }

}
