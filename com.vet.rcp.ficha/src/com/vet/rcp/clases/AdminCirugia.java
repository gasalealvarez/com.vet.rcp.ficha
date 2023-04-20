package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Cirugia;
import com.vet.rcp.modelo.Internacion;
import com.vet.rcp.modelo.Paciente;

public class AdminCirugia {
	public Collection<Cirugia> obtenerCirugias(Paciente paciente){
		Collection<Cirugia> col = new ArrayList<Cirugia>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			ResultSet rs = Base.executeQuery("select * from cirugia where" +
					" idpaciente=" + paciente.getIdPaciente());
			while (rs.next()){
				col.add(new Cirugia(rs.getInt("idcirugia"),rs.getInt("idpaciente"), 
						rs.getString("cirugia"), df.format(rs.getDate("fecha"))));
			}
		}catch (Exception e){
			System.out.println("err en admin cirugia..."+ e);
		}
		return col;
	}
	public Cirugia agregarCirugia(Paciente paciente, String cirugia,  String fecha){
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from cirugia", true);
		
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechadf= df.parse(fecha);
			
			rs.moveToInsertRow();
			rs.updateInt("idpaciente", paciente.getIdPaciente());
			rs.updateTimestamp("fecha", new Timestamp(fechadf.getTime()));
			rs.updateString("cirugia", cirugia);
			rs.insertRow();
			
			rs.close();
			
			ResultSet rsid = Base.executeQuery("select * from cirugia order by " +
					"idcirugia ");
			rsid.last();
			id=rsid.getInt("idcirugia");
			rsid.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err in admin cirugia...." + e);
		}
		return new Cirugia(id, paciente.getIdPaciente(),cirugia, fecha);
	}
}
