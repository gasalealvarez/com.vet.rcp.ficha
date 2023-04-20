package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Paciente;
import com.vet.rcp.modelo.Propietario;


public class AdminPaciente {
	public Collection<Paciente> obtenerPaciente(Object first) {
		Collection<Paciente> col = new  ArrayList<Paciente>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			ResultSet rs = Base.executeQuery("select * from selpaciente where idpropietario=" + ((Propietario) first).getIdPropietario()); 
//					"and baja=false");
			while (rs.next()){
				col.add(new Paciente(rs.getInt("idpaciente"),
						rs.getString("paciente"),rs.getInt("idsexo"), 
						rs.getInt("idpropietario"), rs.getString("propietario"),
						df.format(rs.getDate("fechanacimiento")), rs.getInt("idespecie"),
						rs.getString("especie"), rs.getInt("idraza"), 
						rs.getString("raza"), rs.getInt("idcolor"), 
						rs.getString("color"), rs.getString("pathfoto"), 
						rs.getBoolean("baja")));
			}
		} catch (Exception e){
			System.out.println("error en adminPaciente...." + e);
		}
		return col;
	}
	public Paciente altaPaciente (String paciente, int idPropietario, int idSexo,
			String fechaNacimiento, int idEspecie, int idRaza, int idColor,
			String pathFoto, boolean alta){
		int id=0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date fechadf = df.parse(fechaNacimiento);
			
			ResultSet rs = Base.executeQuery("select * from paciente", true);
			
			rs.moveToInsertRow();
			rs.updateString("paciente", paciente);
			rs.updateInt("idsexo", idSexo);
			rs.updateInt("idpropietario", idPropietario);
			rs.updateInt("idespecie", idEspecie);
			rs.updateInt("idraza", idRaza);
			rs.updateInt("idcolor", idColor);
			rs.updateTimestamp("fechanacimiento", new Timestamp(fechadf.getTime()));
			rs.updateBoolean("baja", false);
			
			rs.insertRow();
			
			rs.close();
			ResultSet rsID = Base.executeQuery("select * from paciente order by " +
					" idpaciente ");
			rsID.last();
			id = rsID.getInt("idpaciente");
			rsID.close();
			
		}catch (Exception e ){
			System.out.println("Error en alta Paciente" + e);
		}
		return new Paciente(id, paciente, idSexo, idPropietario, fechaNacimiento,
				idEspecie, idRaza, idColor, pathFoto, alta);
	}
	public void eliminarPaciente(Paciente paciente){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM paciente WHERE " +
					"	idpaciente=" + paciente.getIdPaciente(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar paciente..."+ e);
		}
	}
	public void bajaPaciente(Paciente paciente){
		try{
			ResultSet rs= Base.executeQuery("SELECT * FROM paciente WHERE " +
					"	idpaciente=" + paciente.getIdPaciente(), true);
			rs.first();
			rs.updateBoolean("baja", true);
			rs.updateRow();
			
		}catch (Exception e){
			System.out.println("Error en baja de paciente....");
		}
	}
}
