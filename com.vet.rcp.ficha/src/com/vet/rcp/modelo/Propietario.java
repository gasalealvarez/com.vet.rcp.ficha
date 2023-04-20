package com.vet.rcp.modelo;

public  class Propietario {
	int idPropietario;
	String propietario, direccion, telefono, email;
	static Propietario propietario_seleccionado;
	
	public Propietario(int idPropietario, String propietario) {
		super();
		this.propietario = propietario;
		this.idPropietario = idPropietario;
	}
	public Propietario(String direccion, String email, int idPropietario,
			String propietario, String telefono) {
		super();
		this.direccion = direccion;
		this.email = email;
		this.idPropietario = idPropietario;
		this.propietario = propietario;
		this.telefono = telefono;
	}
	public static Propietario getPropietario_seleccionado() {
		return propietario_seleccionado;
	}
	public static void setPropietario_seleccionado(
			Propietario propietario_seleccionado) {
		Propietario.propietario_seleccionado = propietario_seleccionado;
	}
	
	
	public int getIdPropietario() {
		return idPropietario;
	}
	public void setIdPropietario(int idPropietario) {
		this.idPropietario = idPropietario;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}	
	@Override
	public String toString() {
		return this.propietario;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
		
}
