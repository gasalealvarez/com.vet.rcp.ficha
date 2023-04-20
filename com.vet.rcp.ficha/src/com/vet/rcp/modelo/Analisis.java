package com.vet.rcp.modelo;

public class Analisis {
	int idAnalisis, idPaciente, iditem_panel;
	String fecha, item,  descripcion, unidad, panel;
	double valor, minimo, maximo;
	boolean isTexto;
	

	public Analisis(String fecha, int idAnalisis, int idPaciente) {
		super();
		this.fecha = fecha;
		this.idAnalisis = idAnalisis;
		this.idPaciente = idPaciente;
	}
	
	public Analisis(String descripcion, int idAnalisis, int iditem_panel,
			double valor) {
		super();
		this.descripcion = descripcion;
		this.idAnalisis = idAnalisis;
		this.iditem_panel = iditem_panel;
		this.valor = valor;
	}

	public Analisis(int iditem_panel, String item, double valor, double minimo, double maximo,  
			String descripcion, String unidad, boolean istexto){
		this.iditem_panel = iditem_panel;
		this.item = item;
		this.valor = valor;
		this.minimo = minimo;
		this.maximo = maximo;
		this.descripcion = descripcion;
		this.unidad = unidad;
		this.isTexto= istexto;
	}
	public Analisis(String panel, String item, double valor, double minimo, double maximo,  
			String descripcion, String unidad, boolean istexto){
		this.panel = panel;
		this.item = item;
		this.valor = valor;
		this.minimo = minimo;
		this.maximo = maximo;
		this.descripcion = descripcion;
		this.unidad = unidad;
		this.isTexto= istexto;
	}
	
	public String getpanel() {
		return panel;
	}

	public void setpanel(String panel) {
		this.panel = panel;
	}

	public int getIdAnalisis() {
		return idAnalisis;
	}
	public void setIdAnalisis(int idAnalisis) {
		this.idAnalisis = idAnalisis;
	}
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public int getIditem_panel() {
		return iditem_panel;
	}
	public void setIditem_panel(int iditem_panel) {
		this.iditem_panel = iditem_panel;
	}
	
	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
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


	public String getUnidad() {
		return unidad;
	}


	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}


	public boolean isTexto() {
		return isTexto;
	}


	public void setTexto(boolean isTexto) {
		this.isTexto = isTexto;
	}


	@Override
	public String toString() {
		return this.fecha;
	}
}
