package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Especie;
import com.vet.rcp.modelo.Paciente;

public class AdminEspecie {
	public Collection<Especie> obtenerEspecie() {
		Collection<Especie>colEspecie = new ArrayList<Especie>();
		try {
			ResultSet rs = Base.executeQuery("select * from especie");
			while (rs.next()){
				colEspecie.add(new Especie(rs.getString("especie"), 
						rs.getInt("idespecie")));
			}
			
		}catch (Exception e){
			System.out.println(e);
		}
		return colEspecie;
	}
	public Especie crearObjeto(Paciente paciente){
		return new Especie(paciente.getEspecie(), paciente.getIdEspecie());
	}
	public Especie agregarEspecie(String especie) {
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from especie", true);
		
			rs.moveToInsertRow();
			rs.updateString("especie", especie);
			rs.insertRow();
			
			rs.close();
			
			ResultSet rsid = Base.executeQuery("select * from especie order by " +
					"idespecie ");
			rsid.last();
			id=rsid.getInt("idespecie");
			rsid.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err in admin Especie...." + e);
		}
		return new Especie(especie, id);
	}
	public void eliminarEspecie(Especie especie){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM especie WHERE " +
					"	idespecie=" + especie.getIdEspecie(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar especie..."+ e);
		}
	}
}
