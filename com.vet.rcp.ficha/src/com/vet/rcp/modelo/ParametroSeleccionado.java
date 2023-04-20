package com.vet.rcp.modelo;


public class ParametroSeleccionado {
	public static Parametro parametroSeleccionado;
	public static String resultado;
	
	public ParametroSeleccionado(Parametro parametro) {
		this.parametroSeleccionado = parametro;
	}
	public static Parametro ParametroSeleccionado() {
		return parametroSeleccionado;
	}
	public void resultado(String resultado) {
		this.resultado = resultado;
	}
	public static String resultado() {
		return resultado;
	}
}
