package service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.Account;
import model.Movements;

public interface ClienteService {
	
	    public Account validarCuenta(int idCuenta);
	// Consulta saldo
		public Optional<Double> consultarSaldo(int idCuenta);
		// Realizar un ingreso
	    public boolean ingresar(double cantidad, int idCuenta);
	    //  Realizar un retiro
	    public boolean retirar(double cantidad, int idCuenta);
	    // Realizar una transferencia
	    public boolean transferencia(int cuentaOrigen, int cuentaDestino,double cantidad);
		// Consultar movimientos entre 2 fechas.
	    public List<Movements> consultarMovimientos(int idCuenta,LocalDate inicio, LocalDate fin);
		

}
