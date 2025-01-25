package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import service.ClienteService;
import service.ClienteServiceImpl;

/**
 * Servlet implementation class TransferenciaAction
 */
@WebServlet("/TransferenciaAction")
public class TransferenciaAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteService service;

    @Override
    public void init() throws ServletException {
        ClientRepository repository = new ClientRepositoryImpl(); // Iniciamos el repositorio
        service = new ClienteServiceImpl(repository); // Inicializamos el servicio
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  int numCuentaOrigen=(Integer)request.getSession().getAttribute("numeroCuenta");
          int numeroCuentaDestino=Integer.parseInt(request.getParameter("numeroCuentaDestino"));
	      double cantidad=Double.parseDouble(request.getParameter("cantidad"));
	       service.transferencia(numCuentaOrigen,numeroCuentaDestino,cantidad);

	}

}
