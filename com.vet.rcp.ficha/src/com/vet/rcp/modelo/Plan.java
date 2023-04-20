package com.vet.rcp.modelo;

public class Plan {
	int idPlan, idMedicamento, idPaciente, idpropietario;
	boolean bUltimaVacuna, bNo_mostrar;
	String vacuna, fecha, fechaProxima, serie, propietario;
	double peso;
	static int serieSeleccionada;
	static double pesoSeleccionado;
	
	public Plan(int idPlan, int idMedicamento, int idPaciente, 
			boolean bNo_mostrar, String vacuna, String serie, 
			String fecha, String fechaProxima, double peso,  boolean ultimadosisvacuna ) {
		super();
		this.idPlan = idPlan;
		this.idMedicamento = idMedicamento;
		this.idPaciente = idPaciente;
		this.bNo_mostrar = bNo_mostrar;
		this.vacuna = vacuna;
		this.serie = serie;
		this.fecha = fecha;
		this.fechaProxima = fechaProxima;
		this.peso = peso;
		this.bUltimaVacuna = ultimadosisvacuna;
	}
	
	public Plan(int idPlan, int idpropietario, String propietario, int idMedicamento, int idPaciente, 
			boolean ultimaDosis, boolean bNo_mostrar, String vacuna, String serie, 
			String fecha, String fechaProxima, double peso) {
		super();
		this.idPlan= idPlan;
		this.idpropietario = idpropietario;
		this.propietario = propietario;
		this.idMedicamento = idMedicamento;
		this.idPaciente = idPaciente;
		this.bUltimaVacuna = ultimaDosis;
		this.bNo_mostrar = bNo_mostrar;
		this.vacuna = vacuna;
		this.serie = serie;
		this.fecha = fecha;
		this.fechaProxima = fechaProxima;
		this.peso = peso;
		
	}
	
	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public int getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}
	public int getIdMedicamento() {
		return idMedicamento;
	}
	public void setIdMedicamento(int idMedicamento) {
		this.idMedicamento = idMedicamento;
	}
	public boolean isbUltimaVacuna() {
		return bUltimaVacuna;
	}
	public void setbUltimaVacuna(boolean bUltimaVacuna) {
		this.bUltimaVacuna = bUltimaVacuna;
	}
	public boolean isbNo_mostrar() {
		return bNo_mostrar;
	}
	public void setbNo_mostrar(boolean bNo_mostrar) {
		this.bNo_mostrar = bNo_mostrar;
	}
	public String getVacuna() {
		return vacuna;
	}
	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFechaProxima() {
		return fechaProxima;
	}
	public void setFechaProxima(String fechaProxima) {
		this.fechaProxima = fechaProxima;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int getIdpropietario() {
		return idpropietario;
	}

	public void setIdpropietario(int idpropietario) {
		this.idpropietario = idpropietario;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public static int getSerieSeleccionada() {
		return serieSeleccionada;
	}

	public static void setSerieSeleccionada(int serieSeleccionada) {
		Plan.serieSeleccionada = serieSeleccionada;
	}

	public static double getPesoSeleccionado() {
		return pesoSeleccionado;
	}

	public static void setPesoSeleccionado(double pesoSeleccionado) {
		Plan.pesoSeleccionado = pesoSeleccionado;
	}
	
	
}
