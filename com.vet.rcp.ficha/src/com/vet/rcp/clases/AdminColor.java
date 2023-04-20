package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Paciente;
import com.vet.rcp.modelo.Pelaje;

public class AdminColor {
	public Collection<Pelaje> obtenerPelaje() {
		Collection<Pelaje> col= new ArrayList<Pelaje>();
		try {
			ResultSet rs = Base.executeQuery("select * from color");
			while (rs.next()){
				col.add(new Pelaje(rs.getInt("idcolor"), rs.getString("color")));
			}
		}catch(Exception e){
			System.out.println("err en col pelaje ..." + e);
		}
		return col;
	}
	
	public Pelaje crearObjeto(Paciente paciente){
		return new  Pelaje(paciente.getIdColor(), paciente.getColor()); 
	}
	
	public Pelaje agregarPelaje(String color) {
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from color", true);
		
			rs.moveToInsertRow();
			rs.updateString("color", color);
			rs.insertRow();
			
			rs.close();
			
			ResultSet rsid = Base.executeQuery("select * from color order by " +
					"idcolor ");
			rsid.last();
			id=rsid.getInt("idcolor");
			rsid.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err in admin pelaje...." + e);
		}
		return new Pelaje(id, color);
	}
	public void eliminarPelaje(Pelaje pelaje) {
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM color WHERE " +
					"	idcolor=" + pelaje.getIdPelaje(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar pelaje..."+ e);
		}
	}
}
