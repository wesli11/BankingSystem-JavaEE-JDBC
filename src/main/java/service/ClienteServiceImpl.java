package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Account;
import model.Movements;
import repository.ClientRepository;

public class ClienteServiceImpl implements ClienteService {
	
	ClientRepository repositoryJdbc;
	
	public ClienteServiceImpl(ClientRepository repositoryJdbc) {
		this.repositoryJdbc=repositoryJdbc;
	}

	@Override
	public Optional<Double> consultarSaldo(int idCuenta) {
		   Account account=repositoryJdbc.accountById(idCuenta);
         if(account!=null){
        	 return Optional.of(account.getBalance());
         }
		return Optional.empty();
	}

	@Override
	public boolean ingresar(double cantidad, int idCuenta) {
		  if (cantidad <= 0) {
		        throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
		    }
		   Account account=repositoryJdbc.accountById(idCuenta);
	         if(account!=null){
	        	 Movements mvs=new Movements();
	        	 mvs.setIdAccount(idCuenta);
	        	 mvs.setDateMovement(LocalDate.now());
	        	 mvs.setQuantity(cantidad);
	        	 mvs.setOperation("Ingreso");
	        	 repositoryJdbc.saveMovimiento(mvs);
	        	return repositoryJdbc.income(cantidad, idCuenta);
	         }
		return false;
	}

	@Override
	public boolean retirar(double cantidad, int idCuenta) {
		 Account account=repositoryJdbc.accountById(idCuenta);
		 if(account.getBalance()<cantidad) {
			 throw new IllegalArgumentException("saldo insuficiente");
		 }
         if(account!=null){
        	 Movements mvs=new Movements();
        	 mvs.setIdAccount(idCuenta);
        	 mvs.setDateMovement(LocalDate.now());
        	 mvs.setQuantity(account.getBalance()- cantidad);
        	 mvs.setOperation("Extraccion");
        	 repositoryJdbc.saveMovimiento(mvs);
        	return repositoryJdbc.withdrawal(cantidad, idCuenta);
         }
	   return false;
	}

	@Override
	public boolean transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) {
		 Account account=repositoryJdbc.accountById(cuentaOrigen);
		 Account account2=repositoryJdbc.accountById(cuentaDestino);
		 if(account!=null && account2!=null){
			return repositoryJdbc.transfer(cuentaOrigen, cuentaDestino, cantidad);
		 }
		return false;
	}

	@Override
	public List<Movements> consultarMovimientos(int idCuenta, LocalDate inicio, LocalDate fin) {
		 Account account=repositoryJdbc.accountById(idCuenta);
		 if(inicio.isBefore(fin) && account!=null) {
			 return repositoryJdbc.checkMovements(idCuenta, inicio, fin);
		 }
		return new ArrayList<>();
	}

	@Override
	public Account validarCuenta(int idCuenta) {
		 Account account=repositoryJdbc.accountById(idCuenta);
		 if(account==null) {
			 throw new IllegalArgumentException("el numero de cuenta no existe"); 
		 }
		return account;
	}

}
