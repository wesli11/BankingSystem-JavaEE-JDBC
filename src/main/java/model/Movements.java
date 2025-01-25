package model;

import java.time.LocalDate;

public class Movements {
	
	private int idMovements;
	private int idAccount;
	private LocalDate dateMovement;
	private double quantity;
	private String operation;
	
	public Movements() {}

	public Movements(int idMovements, int idAccount, LocalDate dateMovement, double quantity, String operation) {
		super();
		this.idMovements = idMovements;
		this.idAccount = idAccount;
		this.dateMovement = dateMovement;
		this.quantity = quantity;
		this.operation = operation;
	}

	public int getIdMovements() {
		return idMovements;
	}

	public void setIdMovements(int idMovements) {
		this.idMovements = idMovements;
	}

	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public LocalDate getDateMovement() {
		return dateMovement;
	}

	public void setDateMovement(LocalDate dateMovement) {
		this.dateMovement = dateMovement;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	

}
