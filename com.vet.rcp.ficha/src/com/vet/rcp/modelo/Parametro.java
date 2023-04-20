package com.vet.rcp.modelo;

public class Parametro {
	int idParametro, idItem, idPanel, idEspecie;
	String item, panel, unidad ;
	double minimo, maximo;
	boolean texto;
	
	public Parametro(int idParametro, String parametro, int idEspecie, String unidad,
			double minimo, double maximo,  int 	idPanel, String panel, boolean texto) {
		super();
		this.idParametro = idParametro;
		this.item = parametro;
		this.idEspecie = idEspecie;
		this.unidad = unidad;
		this.minimo = minimo;
		this.maximo = maximo;
		this.idPanel = idPanel;
		this.panel= panel;
		this.texto= texto;
	}
	public Parametro(int idParametro, String parametro){
		this.idParametro = idParametro;
		this.item = parametro;
	}
	
	public int getIdParametro() {
		return idParametro;
	}
	public void setIdParametro(int idParametro) {
		this.idParametro = idParametro;
	}
	public String getParametro() {
		return item;
	}
	public void setParametro(String parametro) {
		this.item = parametro;
	}
	
	
	public double getMinimo() {
		return minimo;
	}

	public void setMinimo(double minimo) {
		this.minimo = minimo;
	}

	public double getMaximo() {
		return maximo;
	}

	public void setMaximo(double maximo) {
		this.maximo = maximo;
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
	
	public int getIdEspecie() {
		return idEspecie;
	}

	public void setIdEspecie(int idEspecie) {
		this.idEspecie = idEspecie;
	}
	
	public boolean isTexto() {
		return texto;
	}

	public void setTexto(boolean texto) {
		this.texto = texto;
	}
	
	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	@Override
	public String toString(){
		return this.item;
	}
}
