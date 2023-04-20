package com.vet.rcp.modelo;

public class Especie {
	int idEspecie;
	String especie;
	
	public Especie(String especie, int idEspecie) {
		super();
		this.especie = especie;
		this.idEspecie = idEspecie;
	}
	public int getIdEspecie() {
		return idEspecie;
	}
	public void setIdEspecie(int idEspecie) {
		this.idEspecie = idEspecie;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	
	@Override
	public String toString(){
		return  this.especie ;
	}
}
