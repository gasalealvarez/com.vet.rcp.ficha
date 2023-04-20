package com.vet.rcp.modelo;

public class Pelaje {
	int idPelaje;
	String pelaje;
	
	
	public Pelaje(int idPelaje, String pelaje) {
		this.idPelaje = idPelaje;
		this.pelaje = pelaje;
	}
	
	public int getIdPelaje() {
		return idPelaje;
	}
	public void setIdPelaje(int idPelaje) {
		this.idPelaje = idPelaje;
	}
	public String getPelaje() {
		return pelaje;
	}
	public void setPelaje(String pelaje) {
		this.pelaje = pelaje;
	}
	@Override
	public String toString(){
		return this.pelaje;
	}
}
