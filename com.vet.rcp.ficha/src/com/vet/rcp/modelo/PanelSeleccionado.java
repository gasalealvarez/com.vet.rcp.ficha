package com.vet.rcp.modelo;

public class PanelSeleccionado {
	static Panel panel;

	public PanelSeleccionado(Panel panel) {
		this.panel = panel;
	}
	public static Panel PanelSeleccionado() {
		return panel;
	}
}
