package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Item;
import com.vet.rcp.modelo.Panel;

public class AdminNombreParametro {
	public Collection<Item> obtenerNombres() {
		Collection<Item> col = new ArrayList<Item>();
		try {
			ResultSet rs = Base.executeQuery("select * from item");
			while (rs.next()){
				col.add(new Item(rs.getInt("iditem"), rs.getString("item"), rs.getBoolean("texto")));
			}
		}catch(Exception e){
			System.out.println("err en obtenerNombres.." +e);
		}
		return col;
	}
	public Item nuevoNombreParametro(Panel panel, String parametro, String unidad, boolean texto) {
		int id=0;
		try{
			ResultSet rs = Base.executeQuery("select * from item", true);
			rs.moveToInsertRow();
			rs.updateInt("idpanel", panel.getIdPanel());
			rs.updateString("item", parametro);
			rs.updateString("unidad", unidad);
			rs.updateBoolean("texto",texto);
			rs.insertRow();
			
			rs.close();
			ResultSet rsid = Base.executeQuery("select * from item order by " +
			"iditem ");
			rsid.last();
			id=rsid.getInt("iditem");
			rsid.close();

		}catch (Exception e){
			System.out.println("err en nuevo nombreParametro..." +e);
		}
		return new Item(id, parametro, texto);
	}
	public void eliminarNombreParametro(Item item){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM item WHERE " +
					"	idespecie=" + item.getIdItem(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar item..."+ e);
		}
	}
}
