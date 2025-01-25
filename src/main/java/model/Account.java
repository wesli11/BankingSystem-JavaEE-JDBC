package model;

public class Account {
	
	private int idAccount;
	private double balance;
	private String typeAccount;
	private Status status;
	
	public enum Status{
		ACTIVA,
		BLOQUEADA
	}
	public Account() {}
	
	
	public Account(int idAccount, double balance, String typeAccount) {
		super();
		this.idAccount = idAccount;
		this.balance = balance;
		this.typeAccount = typeAccount;
		this.status=Status.ACTIVA;
	}



	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(String typeAccount) {
		this.typeAccount = typeAccount;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Account [idAccount=" + idAccount + ", balance=" + balance + ", typeAccount=" + typeAccount + ", status="
				+ status + "]";
	}
	
	

}
