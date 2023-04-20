package com.vet.rcp.modelo;

public class Raza {
	int idRaza;
	String raza;
	
	
	public Raza(int idRaza, String raza) {
		super();
		this.idRaza = idRaza;
		this.raza = raza;
	}
	public int getIdRaza() {
		return idRaza;
	}
	
	public void setIdRaza(int idRaza) {
		this.idRaza = idRaza;
	}
	public String getRaza() {
		return raza;
	}
	public void setRaza(String raza) {
		this.raza = raza;
	}
	
	@Override
	public String toString(){
		return this.raza;
	}
}
