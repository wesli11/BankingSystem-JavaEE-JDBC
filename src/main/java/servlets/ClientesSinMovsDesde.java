package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Client;
import model.Movements;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import repository.EmployeeRepository;
import repository.EmployeeRepositoryImpl;
import service.EmployeeService;
import service.EmployeeServiceImpl;

/**
 * Servlet implementation class ClientesSinMovsDesde
 */
@WebServlet("/ClientesSinMovsDesde")
public class ClientesSinMovsDesde extends HttpServlet {
	private EmployeeService service;

    @Override
    public void init() throws ServletException {
    	EmployeeRepository repository = new EmployeeRepositoryImpl(); // Iniciamos el repositorio
        ClientRepository cRepository = new ClientRepositoryImpl(); // Iniciamos el repositorio
        service = new EmployeeServiceImpl(repository,cRepository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     // String fecha1=request.getParameter("fecha1");
	      
         // request.setAttribute("sinmovs",service.obtenerClientesSinMovimientosDesde(LocalDate.parse(fecha1)));

   StringBuilder stringBuilder = new StringBuilder();
   String line;
   while ((line = request.getReader().readLine()) != null) {
       stringBuilder.append(line);
   }

   // Convertir el JSON a un objeto
   ObjectMapper objectMapper = new ObjectMapper();
   // Convertir el cuerpo JSON en un JsonNode
   JsonNode jsonNode = objectMapper.readTree(stringBuilder.toString());
   String fecha1 = jsonNode.get("fecha1").asText();  // Obtener la fecha1

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta según tu formato
  LocalDate str1 = LocalDate.parse(fecha1, formatter);  
  Map<Integer,Client>movimientos= service.obtenerClientesSinMovimientosDesde(LocalDate.parse(fecha1));
response.setContentType("application/json");
response.setCharacterEncoding("UTF-8");

// Crear el ObjectMapper de Jackson y registrar el módulo para manejar LocalDate
objectMapper.registerModule(new JavaTimeModule()); // Registrar el módulo JSR310
objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Opcional: para evitar que las fechas se serialicen como timestamps

// Convertir la lista de movimientos a JSON y escribirla en la respuesta
PrintWriter out = response.getWriter();
out.println(objectMapper.writeValueAsString(movimientos));
	}

}
