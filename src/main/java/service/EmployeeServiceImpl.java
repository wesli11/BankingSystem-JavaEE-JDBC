package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Account;
import model.Client;
import model.Movements;
import model.Transfer;
import repository.ClientRepository;
import repository.EmployeeRepository;

public class EmployeeServiceImpl implements EmployeeService {

	EmployeeRepository employeeRepository;
	ClientRepository clientRepository; 
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ClientRepository clientRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.clientRepository=clientRepository;
	}

	@Override
	public boolean registrarCliente(Client cliente) {
      if(employeeRepository.clientByDni(cliente.getDni())==null) {
    	  employeeRepository.registrarCliente(cliente);
    	  return true;
      }
		return false;
	}

	@Override
	public boolean registrarCuenta(int idCliente, Account cuenta) {
		Client client=employeeRepository.clientByDni(idCliente);
		Account account=employeeRepository.accountByNumber(cuenta.getIdAccount());
		if(account==null) {
			employeeRepository.registrarCuenta(idCliente, cuenta);
			return true;
		}
		return false;
	}

	@Override
	public boolean actualizarDatosCliente(Client cliente) {
		Client client=employeeRepository.clientByDni(cliente.getDni());
		if(client!=null) {
			employeeRepository.actualizarDatosCliente(cliente);
			return true;
		}
		return false;
	}

	@Override
	public boolean bloquearCuenta(int idCuenta) {
		Account account=employeeRepository.accountByNumber(idCuenta);
		if(account!=null) {
			employeeRepository.bloquearCuenta(account.getIdAccount());
			return true;
		}return false;
	}

	@Override
	public Client clientInfoAccounts(int idCliente) {
		Client client=employeeRepository.clientByDni(idCliente);
		if(client!=null) {
		return employeeRepository.clientInfoAccounts(idCliente);
		
		}
		return null;
	}

	@Override
	public double obtenerSaldoTotal(int idCliente) {
		Client client=employeeRepository.clientByDni(idCliente);
		if(client!=null) {
		return employeeRepository.obtenerSaldoTotal(idCliente);
		}
		return 0;
	}

	@Override
	public List<Movements> obtenerMovimientosPorCuenta(int idCuenta) {
		Account account=employeeRepository.accountByNumber(idCuenta);
		if(account!=null) {
		return employeeRepository.obtenerMovimientosPorCuenta(account.getIdAccount());
			
		}
		return new ArrayList<>();
	}

	@Override
	public List<Account> obtenerCuentasConSaldoMayor(double saldoMinimo) {
		return employeeRepository.obtenerCuentasConSaldoMayor(saldoMinimo);
	}

	@Override
	public List<Movements> movementsOfClient(int idCliente) {
		Client client=employeeRepository.clientByDni(idCliente);
		if(client!=null) {
			return employeeRepository.movementsOfClient(client.getDni());
		}
		return null;
	}

	@Override
	public List<Movements> obtenerMovimientosPorClienteEnRango(int idCliente, LocalDate desde, LocalDate hasta) {
		Client client=employeeRepository.clientByDni(idCliente);
		if(client==null) {
		throw new IllegalArgumentException("Client dont exist");
		}
		if(desde.isBefore(hasta)) {
			return employeeRepository.obtenerMovimientosPorClienteEnRango(client.getDni(), desde, hasta);
		}
		return null;
	}

	@Override
	public List<Client> obtenerClientesConSaldosNegativos() {
		return employeeRepository.obtenerClientesConSaldosNegativos();
	}

	@Override
	public Map<String, Double> obtenerTotalSaldosPorTipoDeCuenta() {
		return employeeRepository.obtenerTotalSaldosPorTipoDeCuenta();
	}

	@Override
	public Map<Integer,Client> obtenerClientesSinMovimientosDesde(LocalDate fecha) {
		return employeeRepository.obtenerClientesSinMovimientosDesde(fecha);
	}

	@Override
	public List<Client> obtenerClientesConSaldoPromedioMasAlto(int anio) {
		return employeeRepository.obtenerClientesConSaldoPromedioMasAlto(anio);
	}

	@Override
	public boolean desbloquearCuenta(int idCuenta) {
		Account account=employeeRepository.accountByNumber(idCuenta);
		if(account!=null) {
			employeeRepository.desbloquearCuenta(account.getIdAccount());
			return true;
		}return false;
	}

	@Override
	public boolean anularTransferencia(int idTransferencia, LocalDate fechaActual) {
		Transfer tr=employeeRepository.transferById(idTransferencia);
		if(tr==null) {
			throw new IllegalArgumentException("Not found id of transfer");
		}
		if (ChronoUnit.DAYS.between(tr.getFecha(), fechaActual) > 7) {
	        return false; // Fuera del límite de tiempo
	    }
//  
		Account cuentaOrigen = clientRepository.accountById(tr.getCuentaOrigen());
	    Account cuentaDestino = clientRepository.accountById(tr.getCuentaDestino());
	    if(cuentaOrigen==null || cuentaDestino==null) {
	    	throw new IllegalArgumentException("Not found account");
	    }
	    System.out.println("TRRRRR "+ tr.getCantidad());
	    employeeRepository.actualizarSaldo(cuentaOrigen.getIdAccount(), tr.getCantidad()); // Revertir débito
		   System.out.println("saldo origen"+ cuentaOrigen.getBalance());
		   System.out.println("=================================");
	   employeeRepository.actualizarSaldo(cuentaDestino.getIdAccount(), -tr.getCantidad()); // Revertir crédito
	   System.out.println("saldo destino"+ cuentaDestino.getBalance());

	     tr.setEstado("ANULADA");
	     employeeRepository.updateTransferById(tr);
       	 Movements mvs=new Movements();
          mvs.setIdAccount(cuentaOrigen.getIdAccount());
          mvs.setQuantity(tr.getCantidad());
          mvs.setDateMovement(fechaActual);
          mvs.setOperation("Ingreso");
          clientRepository.saveMovimiento(mvs);
          
          Movements mvs2=new Movements();
          mvs2.setIdAccount(cuentaDestino.getIdAccount());
          mvs2.setQuantity(-tr.getCantidad());
          mvs2.setDateMovement(fechaActual);
          mvs2.setOperation("Extraccion");
          clientRepository.saveMovimiento(mvs2);
          return true;
		}
	

}
