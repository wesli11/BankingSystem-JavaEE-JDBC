package service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import model.Account;
import model.Client;
import model.Movements;

public interface EmployeeService {
	
  public boolean registrarCliente(Client cliente);
    //1-Registrar cuenta
    public boolean registrarCuenta(int idCliente, Account cuenta);
    
    //2- actualizar cliente
    public boolean actualizarDatosCliente(Client cliente);
    //3.-bloquear cuenta
	public boolean bloquearCuenta(int idCuenta);
	public boolean desbloquearCuenta(int idCuenta);
	//4.- info cliente y cuentas
	public Client clientInfoAccounts(int idCliente);
	//5.-Saldo total del cliente
	public double obtenerSaldoTotal(int idCliente);
	//6-Obtener los movimientos de una cuenta específica.
	public List<Movements> obtenerMovimientosPorCuenta(int idCuenta);
	//7-Obtener todos los movimientos de un cliente
	public List<Account> obtenerCuentasConSaldoMayor(double saldoMinimo);
    //8-Obtener todos los movimientos de un cliente.
    List<Movements> movementsOfClient(int idCliente);
    //9.- Informacion de movimientos del cliente por fechas
    public List<Movements> obtenerMovimientosPorClienteEnRango(int idCliente, LocalDate desde, LocalDate hasta);
    //10.- Saldos negativos
    public List<Client> obtenerClientesConSaldosNegativos();
    //11.- Tipo cuenta y saldos
    public Map<String, Double> obtenerTotalSaldosPorTipoDeCuenta();

  //12-Clientes sin movimientos en los últimos 6 meses
    public Map<Integer,Client> obtenerClientesSinMovimientosDesde(LocalDate fecha);
  //13-Encuentra a los clientes con el mayor saldo promedio durante un año.
    public List<Client> obtenerClientesConSaldoPromedioMasAlto(int anio);
    
    //14.- anular transferencia
    public boolean anularTransferencia(int idTransferencia, LocalDate fechaActual);


}
