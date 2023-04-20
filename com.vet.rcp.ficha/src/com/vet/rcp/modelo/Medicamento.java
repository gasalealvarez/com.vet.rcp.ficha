package com.vet.rcp.modelo;

public class Medicamento {
	int idGrupo, idMedicamento;
	String medicamento;
	
	public Medicamento(int idGrupo, int idMedicamento, String medicamento) {
		super();
		this.idGrupo = idGrupo;
		this.idMedicamento = idMedicamento;
		this.medicamento = medicamento;
	}
	
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public int getIdMedicamento() {
		return idMedicamento;
	}
	public void setIdMedicamento(int idMedicamento) {
		this.idMedicamento = idMedicamento;
	}
	public String getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}
	@Override
	public String toString() {
		return this.medicamento;
	}

}
