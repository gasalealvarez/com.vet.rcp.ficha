package com.vet.rcp.modelo;

public class GrupoMedicamento {
	int idGrupo;
	String grupo;
	static GrupoMedicamento grupo_seleccionado;
	
	public GrupoMedicamento(String grupo, int idGrupo) {
		super();
		this.grupo = grupo;
		this.idGrupo = idGrupo;
	}
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public static GrupoMedicamento getGrupo_seleccionado() {
		return grupo_seleccionado;
	}
	public static void setGrupo_seleccionado(GrupoMedicamento grupo_seleccionado) {
		GrupoMedicamento.grupo_seleccionado = grupo_seleccionado;
	}
	@Override
	public String toString() {
		return this.grupo ;
	}
}
