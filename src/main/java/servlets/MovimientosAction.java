package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Movements;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import service.ClienteService;
import service.ClienteServiceImpl;

/**
 * Servlet implementation class MovimientosAction
 */
@WebServlet("/MovimientosAction")
public class MovimientosAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteService service;

    @Override
    public void init() throws ServletException {
        ClientRepository repository = new ClientRepositoryImpl(); // Iniciamos el repositorio
        service = new ClienteServiceImpl(repository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int numCuentaOrigen=(Integer)request.getSession().getAttribute("numeroCuenta");
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
        String fecha2 = jsonNode.get("fecha2").asText();  // Obtener la fecha2
	   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta según tu formato
	   LocalDate str1 = LocalDate.parse(fecha1, formatter);  
	   LocalDate str2 = LocalDate.parse(fecha2, formatter);
	   List<Movements>movimientos= service.consultarMovimientos(numCuentaOrigen,str1,str2);
 //  request.setAttribute("movs", service.consultarMovimientos(numCuentaOrigen,LocalDate.parse(fecha1),LocalDate.parse(fecha2)));
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
