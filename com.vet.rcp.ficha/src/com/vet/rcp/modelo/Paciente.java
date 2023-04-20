package com.vet.rcp.modelo;

public class Paciente {
	int idPaciente, idSexo, idPropietario, idEspecie, idRaza, idColor;
	String paciente, propietario, fecha, pathFoto, especie, raza, color;
	boolean baja;
	
	
	public Paciente(int idPaciente, String Paciente,int idSexo, int idPropietario,
			String Propietario, String fecha, int idEspecie, String Especie,
			int idRaza, String Raza, int idColor, String Color,
			String pathFoto, boolean baja) {
		super();
				
		this.idPaciente = idPaciente;
		this.paciente = Paciente;
		this.idSexo = idSexo;
		this.idPropietario = idPropietario;
		this.propietario=Propietario;
		this.idEspecie = idEspecie;
		this.especie = Especie;
		this.idRaza = idRaza;
		this.raza = Raza;
		this.idColor = idColor;
		this.color = Color;
		this.pathFoto = pathFoto;
		this.baja = baja;
		this.fecha = fecha;
		
	}
	public Paciente(int idPaciente, String paciente, int idPropietario, int idSexo,
			String fechaNacimiento, int idEspecie, int idRaza, int idColor,
			String pathFoto, boolean alta){
		this.idPaciente = idPaciente;
		this.paciente = paciente;
		this.idSexo = idSexo;
		this.idPropietario = idPropietario;
		this.idEspecie = idEspecie;
		this.idRaza = idRaza;
		this.idColor = idColor;
		this.pathFoto = pathFoto;
		this.baja = alta;
		this.fecha = fechaNacimiento;
		
	}
	
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	
	public int getIdSexo() {
		return idSexo;
	}

	public void setIdSexo(int idSexo) {
		this.idSexo = idSexo;
	}

	public int getIdPropietario() {
		return idPropietario;
	}
	public void setIdPropietario(int idPropietario) {
		this.idPropietario = idPropietario;
	}
	public int getIdEspecie() {
		return idEspecie;
	}
	public void setIdEspecie(int idEspecie) {
		this.idEspecie = idEspecie;
	}
	public int getIdRaza() {
		return idRaza;
	}
	public void setIdRaza(int idRaza) {
		this.idRaza = idRaza;
	}
	public int getIdColor() {
		return idColor;
	}
	public void setIdColor(int idColor) {
		this.idColor = idColor;
	}
	public String getPaciente() {
		return paciente;
	}
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getPathFoto() {
		return pathFoto;
	}
	public void setPathFoto(String pathFoto) {
		this.pathFoto = pathFoto;
	}
	public boolean isBaja() {
		return baja;
	}
	public void setBaja(boolean baja) {
		this.baja = baja;
	}
	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return  this.paciente ;
	}
}
