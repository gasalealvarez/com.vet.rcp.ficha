package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Especie;
import com.vet.rcp.modelo.Item;
import com.vet.rcp.modelo.Panel;
import com.vet.rcp.modelo.Parametro;

public class AdminParametro {
	public Collection<Parametro> obtenerParametros(Panel panel, int idEspecie) {
		Collection<Parametro>col = new ArrayList<Parametro>();
		try {
			String tsql= "select * from selitem " +
				"where idpanel=" + panel.getIdPanel();
			
			ResultSet rs = Base.executeQuery(tsql, true);
			
			while (rs.next()){
				if (rs.getBoolean("texto")==false && rs.getInt("idespecie")==idEspecie){
						col.add(new Parametro(rs.getInt("iditem_panel"), rs.getString("item"),
							rs.getInt("idespecie"), rs.getString("unidad"), rs.getDouble("minimo"), 
							rs.getDouble("maximo"),
							rs.getInt("idpanel"),
							"", rs.getBoolean("texto")));
					} else if (rs.getBoolean("texto")) {
						col.add(new Parametro(rs.getInt("iditem_panel"), rs.getString("item"),
								rs.getInt("idespecie"), rs.getString("unidad"), rs.getDouble("minimo"), 
								rs.getDouble("maximo"),
								rs.getInt("idpanel"),
								"", rs.getBoolean("texto")));
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error en coleccion parametros.." +e);
		}
		return col;
	}
	public Collection<Parametro> obtenerParametros(){
		Collection<Parametro>col = new ArrayList<Parametro>();
		try {
			ResultSet rs = Base.executeQuery("select * from item");
			while (rs.next()){
				col.add(new Parametro(rs.getInt("iditem"), rs.getString("item")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error en coleccion parametros.." +e);
		}
		return col;
	}
	
//	actualiza los valores para el parametro en rcp application
	
	public void valorParametro(Item item, Especie especie, double minimo, double maximo){
		try {
			String tsql ="select * from item_panel where iditem=" + item.getIdItem();
			ResultSet rs = Base.executeQuery(tsql, true);
			rs.first();
			rs.updateDouble("minimo", minimo);
			rs.updateDouble("maximo", maximo);
			rs.updateRow();
		}catch (Exception e){
			System.out.println("Error en asignar el valor al parametro " + e);
		}
		
	}
	
//	determina si  el parametro existe  en rcp application para luego actualizarlo en "valorparametro"
//	o para asociarlo
	public void asociarParametro(Item item, Especie especie,  
			double minimo, double maximo){
		try {
			String tsql ="select * from item_panel" ;
			ResultSet rs = Base.executeQuery(tsql, true);
			rs.moveToInsertRow();
			rs.updateInt("idespecie", especie.getIdEspecie());
			rs.updateInt("iditem", item.getIdItem());
			rs.updateDouble("minimo", minimo);
			rs.updateDouble("maximo", maximo);
			rs.insertRow();
		}catch (Exception e){
			System.out.println("Error en asignar el valor al parametro " + e);
		}
	}
	public boolean existeParametroAsociado(Item item, Especie especie) {
		boolean bValor=false;
		String tsql ="select * from item_panel where iditem=" + item.getIdItem() +
		 " and idespecie=" + especie.getIdEspecie();
		try {
			ResultSet rs = Base.executeQuery(tsql, true);
			if (rs.next()){
				bValor= true;
			}else{
				bValor = false;
			}
		} catch (Exception e){
			System.out.println("error en existe parametro " + e);
		}
		return bValor;
	}
	
	public Parametro nuevoParametro(Panel panel, Item item, Especie especie,
			String unidad, double minimo, double maximo, boolean texto) {
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from item_panel", true);
			rs.moveToInsertRow();
			rs.updateInt("idpanel", panel.getIdPanel());
			rs.updateInt("iditem",item.getIdItem());
			rs.updateInt("idespecie", especie.getIdEspecie());
			rs.updateString("unidad", unidad);
			rs.updateDouble("minimo", minimo);
			rs.updateDouble("maximo", maximo);
			rs.updateBoolean("texto", texto);
			

			rs.insertRow();
			
			ResultSet rsid = Base.executeQuery("select * from item_panel order by " +
			"iditem ");
			rsid.last();
			id=rsid.getInt("iditem");
			rsid.close();
	
		}catch (Exception e){
			System.out.println(e);
		}
		
		return  new  Parametro(id, item.getItem(), especie.getIdEspecie(), unidad, minimo,
				maximo , panel.getIdPanel(), panel.getPanel(), texto);
	}
//	public void eliminarParametro(Parametro parametro){
//		try {
//			ResultSet rs= Base.executeQuery("SELECT * FROM item_panel WHERE " +
//					"	iditem_panel=" + parametro.getIdParametro(), true);
//			rs.first();
//			rs.deleteRow();
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("err en borrar parametro..."+ e);
//			
//		}
//	}
//	public boolean existeParametro(Item item, Especie especie){
//		boolean existe = false;
//		try {
//			ResultSet rs = Base.executeQuery("select * from item_panel where " +
//					"idespecie= " + especie.getIdEspecie() +
//					" and iditem=" + item.getIdItem() + ";");
//			if (rs.next()) {
//				existe= true;
//			}
//		}catch (Exception e){
//			System.out.println(e);
//		}
//		return existe;
//	}
}
