package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Caso;
import com.vet.rcp.modelo.Paciente;

public class AdminCaso {
	
	public Caso altaCaso(int idPaciente, String antecendentes, String sintomas,
			String diagnostico, String tratamiento, String fecha){
		int id =0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechadf= df.parse(fecha);
				
			ResultSet rs= Base.executeQuery("select * from caso", true);
			rs.moveToInsertRow();
			rs.updateInt("idpaciente", idPaciente);
			rs.updateString("antecedentes", antecendentes);
			rs.updateString("sintomas", sintomas);
			rs.updateString("diagnostico", diagnostico);
			rs.updateString("tratamiento", tratamiento);
			rs.updateTimestamp("fecha", new Timestamp(fechadf.getTime()));
			rs.insertRow();
			rs.close();
			
			ResultSet rsID = Base.executeQuery("select * from caso order by idcaso");
			rsID.last();
			id= rsID.getInt("idcaso");
			rsID.close();
		} catch (Exception e){
			System.out.println("err en alta caso..." + e);
		}
		return new Caso(id, antecendentes, sintomas, diagnostico,tratamiento, fecha);
	}
	public Caso editarCaso(Caso caso, String antecendentes, String sintomas,
			String diagnostico, String tratamiento){
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			ResultSet rs= Base.executeQuery("select * from caso where idcaso=" +
					caso.getIdCaso(), true);
			rs.first();
			rs.updateString("antecedentes", antecendentes);
			rs.updateString("sintomas", sintomas);
			rs.updateString("diagnostico", diagnostico);
			rs.updateString("tratamiento", tratamiento);
			rs.updateRow();
			rs.close();
			
		} catch (Exception e){
			System.out.println("err en alta caso..." + e);
		}
		return new Caso(caso.getIdCaso(), antecendentes, sintomas, diagnostico,
				tratamiento, caso.getFecha());
	}
	public Collection<Caso> obtenerHistorial (Paciente paciente){
		Collection<Caso> col = new  ArrayList<Caso>();
		try {
			ResultSet rs = Base.executeQuery("select * from caso where " +
					"idpaciente=" + paciente.getIdPaciente());
			System.out.println(paciente.getIdPaciente());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			while (rs.next()){
				col.add(new Caso(rs.getInt("idcaso"), rs.getString("antecedentes"), 
						rs.getString("sintomas"), rs.getString("diagnostico"), 
						rs.getString("tratamiento"),df.format(rs.getDate("fecha"))));
			}
		} catch (Exception e){
			System.out.println("err en collection caso..." + e);
		}
		return col;
	}
}
