package repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import model.Account;
import model.Client;
import model.Movements;
import model.Transfer;

public interface EmployeeRepository {

	//1-Obtener información de un cliente con sus cuentas.
	public Client clientInfoAccounts(int idCliente);
    //2-Obtener el saldo total de un cliente.
	public double obtenerSaldoTotal(int idCliente);
	//3-Obtener los movimientos de una cuenta específica.
	public List<Movements> obtenerMovimientosPorCuenta(int idCuenta);
	//4-Obtener todos los movimientos de un cliente
	public List<Account> obtenerCuentasConSaldoMayor(double saldoMinimo);
    //5-Obtener todos los movimientos de un cliente.
    List<Movements> movementsOfClient(int idCliente);
    //6
    public List<Movements> obtenerMovimientosPorClienteEnRango(int idCliente, LocalDate desde, LocalDate hasta);
    //7
    public List<Client> obtenerClientesConSaldosNegativos();
    //8
    public Map<String, Double> obtenerTotalSaldosPorTipoDeCuenta();

    //9-Clientes sin movimientos en los últimos 6 meses
    public Map<Integer,Client> obtenerClientesSinMovimientosDesde(LocalDate fecha);
    //9.b
    public List<Client> obtenerClientesSinMovimientosUltimosMeses(int meses);

    //10
    public List<Double> obtenerHistorialDeSaldosPorCuenta(int idCuenta, LocalDate desde, LocalDate hasta);

    //11-Informe de movimientos por tipo de operación en un mes
    public Map<String, Integer> obtenerInformeDeMovimientosPorTipo(int idCuenta, YearMonth mes);

    //12-Encuentra a los clientes con el mayor saldo promedio durante un año.
    public List<Client> obtenerClientesConSaldoPromedioMasAlto(int anio);
    // lo tengo en cliente
   // public List<Movements> detectarMovimientosSospechosos(int idCuenta, LocalDate desde, LocalDate hasta);

    public boolean registrarCliente(Client cliente);//si
    
    public boolean registrarCuenta(int idCliente, Account cuenta);//si

    public boolean actualizarDatosCliente(Client cliente);//si

	public boolean bloquearCuenta(int idCuenta);
	
	public boolean desbloquearCuenta(int idCuenta);
	
    public Client clientByDni(int dni);
    public Account accountByNumber(int numberAcount);

    public Transfer transferById(int id);
    public boolean updateTransferById(Transfer t);
    public void actualizarSaldo(int numeroCuenta, double saldo);
}

/*  
 Operaciones de administrador:
 
Obtener información de un cliente con sus cuentas.

Es algo que los empleados de un banco necesitan consultar para atender a los clientes. Por ejemplo, verificar qué cuentas tiene un cliente.
Obtener los movimientos de una cuenta específica.

Los bancos usan esto para investigar transacciones, resolver disputas o auditar cuentas.
Obtener el saldo total de un cliente.

Aunque no es común para un cliente saber esto, un administrador podría necesitar esta información para evaluar la solvencia de un cliente.
Registrar un movimiento.

Es realista en el contexto de un administrador. Por ejemplo, si hay ajustes manuales en cuentas por algún error, esto sería relevante.
Obtener todos los movimientos de un cliente.

Es útil para generar reportes completos de un cliente en situaciones de auditoría o consultas complejas.
Obtener cuentas con saldo mayor a un valor específico.

Realista, especialmente para identificar clientes VIP o con altas sumas que podrían necesitar atención especial.
3. Consultas avanzadas
Movimientos de un cliente en un rango de fechas.

Esta consulta es una versión más específica de "consultar movimientos" y es completamente factible.
Transferencia de fondos entre dos cuentas (transacciones).

Muy realista. Esto es parte de las operaciones diarias de un banco, asegurando que ambas cuentas se actualicen de manera consistente.
Consulta de clientes con saldos negativos.

Bastante útil y realista. Los bancos necesitan monitorear cuentas en descubierto para gestionar riesgos.
Total de saldos por tipo de cuenta.

Muy común en bancos pequeños y medianos para generar estadísticas de uso y distribución de recursos.
Clientes sin movimientos en los últimos 6 meses.

Usada para identificar cuentas inactivas y planificar estrategias de reactivación o cierre.
Historial de saldos de una cuenta.

Realista si el banco lleva un registro histórico de saldos. Útil para análisis financiero o auditorías.
Informe de movimientos por tipo de operación en un mes.

Común para análisis contable o de gestión interna.
Clientes con el saldo promedio más alto en un año.

Útil para identificar clientes de alto valor (VIP).
Movimientos sospechosos.

Esta consulta es realista y utilizada en sistemas de prevención de fraude y lavado de dinero.



*/