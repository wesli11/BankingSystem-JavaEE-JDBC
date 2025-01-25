package model;

import java.util.ArrayList;
import java.util.List;

public class Client {
	
	private int dni;
	private String name;
	private String address;
	private int phone;
	private List<Account>accounts;
	
	public Client() {}

	public Client(int dni, String name, String address, int phone) {
		super();
		this.dni = dni;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.accounts=new ArrayList<>();
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	

}
