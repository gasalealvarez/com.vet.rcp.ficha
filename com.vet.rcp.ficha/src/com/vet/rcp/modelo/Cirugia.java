package com.vet.rcp.modelo;

public class Cirugia {
	int idCirugia, idPaciente;
	String cirugia, fecha;
	
	public Cirugia(int idCirugia, int idPaciente, String cirugia, String fecha) {
		super();
		this.idCirugia = idCirugia;
		this.idPaciente = idPaciente;
		this.cirugia = cirugia;
		this.fecha = fecha;
	}
	public int getIdCirugia() {
		return idCirugia;
	}
	public void setIdCirugia(int idCirugia) {
		this.idCirugia = idCirugia;
	}
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public String getCirugia() {
		return cirugia;
	}
	public void setCirugia(String cirugia) {
		this.cirugia = cirugia;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	
}
