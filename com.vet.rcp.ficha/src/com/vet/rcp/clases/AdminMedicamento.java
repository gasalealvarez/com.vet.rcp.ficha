package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.GrupoMedicamento;
import com.vet.rcp.modelo.Medicamento;

public class AdminMedicamento {
	public Collection<Medicamento> obtenerMedicamento(GrupoMedicamento grupo){
		Collection<Medicamento> col = new ArrayList<Medicamento>();

		try {
			ResultSet rs = Base.executeQuery("select * from medicamento where idgrupo_medicamento=" + grupo.getIdGrupo());
			while (rs.next()){
				col.add(new Medicamento(rs.getInt("idgrupo_medicamento"),
						rs.getInt("idmedicamento"), 
						rs.getString("medicamento")));
			}
		}catch (Exception e){
			System.out.println("err en col medicamento...." + e);
		}
		return col;
	}

	public Medicamento nuevoMedicamento(GrupoMedicamento grupo, String medicamento){
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from medicamento", true);
			rs.moveToInsertRow();
			rs.updateInt("idgrupo_medicamento", grupo.getIdGrupo());
			rs.updateString("medicamento", medicamento);
			rs.insertRow();
			
			rs.close();
			ResultSet rs1 = Base.executeQuery("select * from medicamento order" +
					" by idmedicamento");
			rs1.last();
			id= rs1.getInt("idmedicamento");
			rs1.close();
		} catch (Exception e){
			System.out.println("err en agregar medicamento..."+e);
		}
		return new Medicamento(grupo.getIdGrupo(), id, medicamento);
	}
	public void eliminarMedicamento() {
		try {
			ResultSet rs = Base.executeQuery("select * from medicamento");
			rs.first();
			rs.deleteRow();
		} catch (Exception e){
			System.out.println(e);
		}
	}
}
