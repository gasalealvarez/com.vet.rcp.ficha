package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Propietario;

public class AdminPropietario {
	public Collection<Propietario> obtenerPropietario() {
		Collection<Propietario> col = new  ArrayList<Propietario>();
		try {
			ResultSet rs = Base.executeQuery("select * from propietario order by propietario");
			while (rs.next()){
				col.add(new Propietario(rs.getString("direccion"),"", 
						rs.getInt("idpropietario"), rs.getString("propietario"), rs.getString("telefono")));
//				System.out.println(rs.getString("propietario"));
			}
		} catch (Exception e){
			System.out.println("error en adminPropietario...." + e);
		}
		return col;
	}
	public Propietario editarPropietario(int idPropietario, String propietario,
		String direccion,String email, String telefono){
		ResultSet rs= Base.executeQuery("SELECT * FROM propietario WHERE idpropietario= " + idPropietario, true);
		try {
			rs.first();
			rs.updateString("propietario", propietario);
			rs.updateString("direccion", direccion);
			rs.updateString("telefono", telefono);
			rs.updateString("email", email);
			rs.updateRow();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return new Propietario(direccion,email,idPropietario, propietario, telefono);
	}
	public void eliminarPropietario(Propietario propietario){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM propietario WHERE " +
					"	idpropietario=" + propietario.getIdPropietario(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar propietario..."+ e);
		}
	}
	public Propietario altaPropietario(String propietario, String direccion, 
			String email, String telefono){
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from propietario", true);
			
			rs.moveToInsertRow();
			rs.updateString("propietario", propietario);
			rs.updateString("direccion", direccion);
			rs.updateString("email", email);
			rs.updateString("telefono", telefono);
			
			rs.insertRow();
			
			rs.close();
			
			ResultSet rsid = Base.executeQuery("select * from propietario order by " +
					"idpropietario ");
			rsid.last();
			id=rsid.getInt("idpropietario");
			rsid.close();
			
		}catch (Exception e ){
			System.out.println("erron en agregar propietario...." + e);
		}
		
		return new Propietario(direccion,email, id,propietario, telefono);
	}
	public boolean existePropietario(Propietario propietario){
		boolean bValor = true;
		ResultSet rs = Base.executeQuery("select * from propietario where idpropietario=" +
				propietario.getIdPropietario(), true);
		try {
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
}
