package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
 * Servlet implementation class AnularTransferenciaAdm
 */
@WebServlet("/AnularTransferenciaAdm")
public class AnularTransferenciaAdm extends HttpServlet {
	private EmployeeService service;

    @Override
    public void init() throws ServletException {
    	EmployeeRepository repository = new EmployeeRepositoryImpl(); // Iniciamos el repositorio
        ClientRepository cRepository = new ClientRepositoryImpl(); // Iniciamos el repositorio
    service = new EmployeeServiceImpl(repository,cRepository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idTransferencia=Integer.parseInt(request.getParameter("idTransferencia"));
	      String fechaStr = request.getParameter("fecha1");
	        if (fechaStr == null || fechaStr.trim().isEmpty()) {
	            throw new IllegalArgumentException("La fecha no puede estar vacía.");
	        }

	        // Analizar la fecha con el formato correcto
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta según tu formato
	        LocalDate fecha = LocalDate.parse(fechaStr, formatter);  
	        System.out.println(">>>>>> "+fecha);
	        service.anularTransferencia(idTransferencia, fecha);
}
}