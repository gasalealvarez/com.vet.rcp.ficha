package com.vet.rcp.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Panel;

public class AdminPanel {
	public Collection<Panel> obtnerPanel() {
		Collection<Panel> col = new ArrayList<Panel>();
		try{
			ResultSet rs = Base.executeQuery("select * from panel");
			while(rs.next()){
				col.add(new Panel(rs.getInt("idpanel"), rs.getString("panel")));
			}
			rs.close();
		}catch(Exception e){
			System.out.println("err en col panel..."+ e);
		}
		return col;
	}
	public Panel agregarPanel (String panel){
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from panel", true);
		
			rs.moveToInsertRow();
			rs.updateString("panel", panel);
			rs.insertRow();
			
			rs.close();
			
			ResultSet rsid = Base.executeQuery("select * from panel order by " +
					"idpanel ");
			rsid.last();
			id=rsid.getInt("idpanel");
			rsid.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err in admin panel...." + e);
		}
		return new Panel(id, panel);
	}
	public void eliminarPanel(Panel panel){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM panel WHERE " +
					"	idpanel=" + panel.getIdPanel(), true);
			rs.first();
			rs.deleteRow();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar panel..."+ e);
		}
	}
}
