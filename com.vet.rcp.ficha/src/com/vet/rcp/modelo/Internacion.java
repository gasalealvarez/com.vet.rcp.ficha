package com.vet.rcp.modelo;

public class Internacion {
	int idinternacion, idpaciente;
	String fecha, texto;
	
	
	
	public Internacion(int idinternacion, int idpaciente, String fecha,
			String texto) {
		super();
		this.idinternacion = idinternacion;
		this.idpaciente = idpaciente;
		this.fecha = fecha;
		this.texto = texto;
	}
	public int getIdinternacion() {
		return idinternacion;
	}
	public void setIdinternacion(int idinternacion) {
		this.idinternacion = idinternacion;
	}
	public int getIdpaciente() {
		return idpaciente;
	}
	public void setIdpaciente(int idpaciente) {
		this.idpaciente = idpaciente;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
}
