package com.vet.rcp.modelo;

public class PacienteSeleccionado {
	static Paciente paciente_seleccionado;
	public PacienteSeleccionado(Paciente paciente) {
		// TODO Auto-generated constructor stub
		this.paciente_seleccionado = paciente;
	}
	public static Paciente paciente_seleccionado(){
		return paciente_seleccionado;
		
	}
}
