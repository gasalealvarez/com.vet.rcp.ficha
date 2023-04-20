package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Raza;

public class AdminRaza {
	public Collection<Raza> obtenerRaza(){
		Collection<Raza> col= new ArrayList<Raza>();
		try {
			ResultSet rs = Base.executeQuery("select * from raza order by raza");
			while (rs.next()){
				col.add(new Raza(rs.getInt("idraza"), rs.getString("raza")));
			}
		} catch (Exception e) {
			System.out.println("err in col raza...." + e);
		}
		return col;
	}
	
	public Raza agregarRaza(String raza){
		int id=0;
		try{
			ResultSet rs = Base.executeQuery("select * from raza", true);
			rs.moveToInsertRow();
			rs.updateString("raza",raza);
			rs.insertRow();
			rs.close();
			
			ResultSet rs1 = Base.executeQuery("select * from raza order by idraza");
			rs1.last();
			id = rs1.getInt("idraza");
			rs.close();
			
		}catch(Exception e){
			System.out.println("err en agregar raza...." + e);
		}
		return new Raza(id, raza);
	}
	public void eliminarRaza(Raza raza){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM raza WHERE " +
					"	idraza=" + raza.getIdRaza(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar raza..."+ e);
		}
	}
}
