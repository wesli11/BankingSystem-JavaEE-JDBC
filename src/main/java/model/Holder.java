package model;

public class Holder {
	
	private int idAccount;
	private int idClient;
	
	public Holder() {}

	public Holder(int idAccount, int idClient) {
		super();
		this.idAccount = idAccount;
		this.idClient = idClient;
	}

	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	
	

}
