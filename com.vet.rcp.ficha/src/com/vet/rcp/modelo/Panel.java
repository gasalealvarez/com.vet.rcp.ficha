package com.vet.rcp.modelo;

public class Panel {
	int idPanel;
	String panel;
	
	public Panel(int idPanel, String panel) {
		super();
		this.idPanel = idPanel;
		this.panel = panel;
	}
	
	public int getIdPanel() {
		return idPanel;
	}
	public void setIdPanel(int idPanel) {
		this.idPanel = idPanel;
	}
	public String getPanel() {
		return panel;
	}
	public void setPanel(String panel) {
		this.panel = panel;
	}
	@Override
	public String toString() {
		return this.panel;
	}
}
