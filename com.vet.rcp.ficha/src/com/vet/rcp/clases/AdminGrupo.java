package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.GrupoMedicamento;

public class AdminGrupo {
	public Collection<GrupoMedicamento>obtnerGrupo() {
		Collection<GrupoMedicamento> col =new ArrayList<GrupoMedicamento>();
		try {
			ResultSet rs = Base.executeQuery("select * from grupo_medicamento");
			while (rs.next()){
				col.add(new GrupoMedicamento(rs.getString("grupo"), rs.getInt("idgrupo_medicamento")));
			}
				
			} catch (Exception e){
				System.out.println("Error en cargar grupo: " + e);
			}
		return col;
	}
}
