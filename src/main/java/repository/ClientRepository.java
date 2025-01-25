package repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.Account;
import model.Movements;

public interface ClientRepository {
	
   // Consulta saldo
	public Optional<Double> checkBalanceByAccount(int idAccount);
	// Realizar un ingreso
    public boolean income(double quantity, int idAccount);
    
    public Account accountById(int idAccount);
    //  Realizar un retiro
    public boolean withdrawal(double quantity, int idAccount);
    // Realizar una transferencia
    public boolean transfer(int idAccountStart, int idAccountEnd,double quantity);
	// Consultar movimientos entre 2 fechas.
    public List<Movements> checkMovements(int idAccount,LocalDate from, LocalDate to);
	
    public void saveMovimiento(Movements movs);
	

}
