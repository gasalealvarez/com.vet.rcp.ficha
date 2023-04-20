package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Internacion;
import com.vet.rcp.modelo.Paciente;

public class AdminInternacion {
	public Collection<Internacion> obtenerInternacion(Paciente paciente){
		Collection<Internacion> col = new ArrayList<Internacion>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			ResultSet rs = Base.executeQuery("select * from internacion where" +
					" idpaciente=" + paciente.getIdPaciente());
			while (rs.next()){
				col.add(new Internacion(rs.getInt("idinternacion"),rs.getInt("idpaciente"), 
						df.format(rs.getDate("fecha")), rs.getString("texto")));
			}
		}catch (Exception e){
			System.out.println("err en admin internacion..."+ e);
		}
		return col;
	}
	public Internacion agregarInternacion(Paciente paciente, String fecha, String texto){
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from internacion", true);
		
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechadf= df.parse(fecha);
			
			rs.moveToInsertRow();
			rs.updateInt("idpaciente", paciente.getIdPaciente());
			rs.updateTimestamp("fecha", new Timestamp(fechadf.getTime()));
			rs.updateString("texto", texto);
			rs.insertRow();
			
			rs.close();
			
			ResultSet rsid = Base.executeQuery("select * from internacion order by " +
					"idinternacion ");
			rsid.last();
			id=rsid.getInt("idinternacion");
			rsid.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err in admin internacion...." + e);
		}
		return new Internacion(id, paciente.getIdPaciente(),fecha,texto);
	}
}
