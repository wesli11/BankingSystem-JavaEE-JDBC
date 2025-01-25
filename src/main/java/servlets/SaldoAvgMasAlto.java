package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Client;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import repository.EmployeeRepository;
import repository.EmployeeRepositoryImpl;
import service.EmployeeService;
import service.EmployeeServiceImpl;

/**
 * Servlet implementation class SaldoAvgMasAlto
 */
@WebServlet("/SaldoAvgMasAlto")
public class SaldoAvgMasAlto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeService service;

    @Override
    public void init() throws ServletException {
    	EmployeeRepository repository = new EmployeeRepositoryImpl(); // Iniciamos el repositorio
        ClientRepository cRepository = new ClientRepositoryImpl(); // Iniciamos el repositorio
    service = new EmployeeServiceImpl(repository,cRepository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            stringBuilder.append(line);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringBuilder.toString());
        String anio = jsonNode.get("anio").asText();  // Obtener la fecha1
       List<Client> clients=  service.obtenerClientesConSaldoPromedioMasAlto(Integer.parseInt(anio));
       response.setContentType("application/json");
       response.setCharacterEncoding("UTF-8");//  int anio=Integer.parseInt(request.getParameter("anio"));
          //request.setAttribute("saldoavg", service.obtenerClientesConSaldoPromedioMasAlto(anio));
       objectMapper.registerModule(new JavaTimeModule()); // Registrar el módulo JSR310
       PrintWriter out = response.getWriter();
       out.println(objectMapper.writeValueAsString(clients));
}

}
