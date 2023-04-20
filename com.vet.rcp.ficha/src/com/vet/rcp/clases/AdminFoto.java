package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Paciente;
import com.vet.rcp.modelo.foto;

public class AdminFoto {
	public Collection<foto> obtnerFotos(Paciente paciente) {
		Collection<foto>col = new ArrayList<foto>();
		try {
			ResultSet rs = Base.executeQuery("select * from foto where idpaciente=" + paciente.getIdPaciente() );
			while (rs.next()){
				col.add(new foto(rs.getInt("idpaciente"), rs.getString("descripcion"),
						rs.getInt("idfoto"), rs.getString("path") ));
			}
			
		}catch (Exception e){
			System.out.println("Error en collection foto " + e);
		}
		return col;
	}
	public foto agregarFoto(Paciente paciente, String path, String descripcion){
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from foto", true);
		
//			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//			Date date = df.parse(fecha);
			
			rs.moveToInsertRow();
			rs.updateInt("idpaciente", paciente.getIdPaciente());
			rs.updateString("path", path);
			rs.updateString("descripcion", descripcion);
			rs.insertRow();
			
			rs.close();
			
			ResultSet rsid = Base.executeQuery("select * from foto order by " +
					"idfoto ");
			rsid.last();
			id=rsid.getInt("idfoto");
			rsid.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err in admin foto...." + e);
		}
		return new foto(paciente.getIdPaciente(),descripcion, id, path);
	}
	
}
