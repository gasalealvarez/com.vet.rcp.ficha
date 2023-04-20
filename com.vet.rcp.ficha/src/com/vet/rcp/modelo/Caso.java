package com.vet.rcp.modelo;

public class Caso {
	int idCaso, idPaciente;
	String antecedentes, sintomas, diagnostico, tratamiento, fecha;
	
	
	public Caso(int idCaso, String antecedentes, String sintomas,  String diagnostico,
			String tratamiento ,String fecha) {
		super();
		this.antecedentes = antecedentes;
		this.diagnostico = diagnostico;
		this.idCaso = idCaso;
		this.sintomas = sintomas;
		this.tratamiento = tratamiento;
		this.fecha = fecha;
	}
	
	public int getIdCaso() {
		return idCaso;
	}
	public void setIdCaso(int idCaso) {
		this.idCaso = idCaso;
	}
	public String getAntecedentes() {
		return antecedentes;
	}
	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}
	public String getSintomas() {
		return sintomas;
	}
	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}
	public String getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	
	
}
