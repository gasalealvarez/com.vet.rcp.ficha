package com.vet.rcp.modelo;

public class foto {
	int idFoto, idPaciente;
	String path, descripcion;
	public static String descripcion_Texto;
	
	
	public foto(int idPaciente, String descripcion, int idFoto, String path) {
		super();
		this.idPaciente = idPaciente;
		this.descripcion = descripcion;
		this.idFoto = idFoto;
		this.path = path;
	}
	
	public foto(String path, String descripcion) {
		super();
		this.path = path;
		this.descripcion = descripcion;
	}
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public int getIdFoto() {
		return idFoto;
	}
	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public  String getDescripcion() {
		return descripcion;
	}
	public  void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return this.path;
	}

	public static String getDescripcion_Texto() {
		return descripcion_Texto;
	}

	public static void setDescripcion_Texto(String descripcion_Texto) {
		foto.descripcion_Texto = descripcion_Texto;
	}
	
}
