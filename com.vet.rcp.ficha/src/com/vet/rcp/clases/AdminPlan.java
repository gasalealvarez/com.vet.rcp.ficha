package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Analisis;
import com.vet.rcp.modelo.Paciente;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.Plan;

public class AdminPlan {
	public Collection<Plan> obtenerPlan(int idpaciente){
		Collection<Plan> col = new ArrayList<Plan>();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			ResultSet rs = Base.executeQuery("select * from selplan where " +
					"idpaciente=" + idpaciente +  "order by fecha");
			
			while (rs.next()){
				col.add(new Plan(rs.getInt("idplan") ,rs.getInt("idmedicamento"), rs.getInt("idpaciente"), 
						rs.getBoolean("no_mostrar"), rs.getString("medicamento"), ""  + rs.getString("serievacuna"),
						df.format(rs.getDate("fecha")), df.format(rs.getDate("fechaproxima")), rs.getDouble("peso"), rs.getBoolean("ultimadosisvacuna")));
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return col;
	}
	public void guardarPlan(Collection<Plan> col, Paciente paciente, boolean bAviso){
		try {
			
			Base.execute("delete from plan where idpaciente=" + paciente.getIdPaciente());
			for (Plan  xxx : col){
				
				String tsql = "INSERT INTO plan (idplan, idpaciente, fecha, fechaproxima, " +
				"ultimadosisvacuna,  no_mostrar, serievacuna, idmedicamento," +
				"peso) VALUES (DEFAULT, " +  PacienteSeleccionado.paciente_seleccionado().getIdPaciente()+", '" + 
				xxx.getFecha() +"', '"+ xxx.getFechaProxima()
				+ "', " + xxx.isbUltimaVacuna() + ", "  +  false + ", " +
				xxx.getSerie() +", "+ xxx.getIdMedicamento() +", "+
				xxx.getPeso() + ");";
	
				Base.execute(tsql);
			}
			Base.executeQuery("UPDATE paciente SET aviso = " + bAviso + " WHERE " +
					"idpaciente = " + PacienteSeleccionado.paciente_seleccionado().getIdPaciente());
		}catch (Exception e){
			System.out.println("err in guardarAnalisis..."+e);
		}
	}
	
	public void borrarAviso(Paciente paciente){
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
	        java.util.Date date = new java.util.Date();
			
			ResultSet rs = Base.executeQuery("select * from plan where idpaciente=" + paciente.getIdPaciente());
			while (rs.next()){
				Base.executeQuery("update plan set no_mostrar='true' where idpaciente=" 
						+  paciente.getIdPaciente() + " and fechaproxima <= '" + df.format(date) + ";");
			}
		}catch (Exception e){
			System.out.println("error en borrar avisos: " + e);
		}
	}
	public void eliminarElemento(Plan plan){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM plan WHERE " +
					"	idplan=" + plan.getIdPlan(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar plan..."+ e);
		}
	}
	
	public void ActualizarAviso (Plan plan){
		Base.executeQuery("update plan set ultimadosisvacuna='false' where idplan=" +  plan.getIdPlan() );
	}
	
	public Collection<Plan> avisos(){
		Collection<Plan> col = new ArrayList<Plan>();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
	        java.util.Date date = new java.util.Date();
	       
	        String tsql = "select * from selplan where  fechaproxima <= '" + df.format(date) +"' and aviso='true' and ultimadosisvacuna='true'   " +
	        		"and no_mostrar='false' and baja='false' ";
	        System.out.println(tsql);
			ResultSet rs = Base.executeQuery(tsql);
			
			while (rs.next()){
				col.add(new Plan(rs.getInt("idplan"), rs.getInt("idpropietario"), rs.getString("propietario"),
						rs.getInt("idmedicamento"), rs.getInt("idpaciente"), 
						rs.getBoolean("ultimaDosisVacuna"), rs.getBoolean("no_mostrar"),rs.getString("medicamento"), rs.getString("serievacuna"),
						df.format(rs.getDate("fecha")), df.format(rs.getDate("fechaproxima")), rs.getDouble("peso")));
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return col;
	}
}
