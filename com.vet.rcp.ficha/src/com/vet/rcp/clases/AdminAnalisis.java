package com.vet.rcp.clases;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.vet.rcp.base.Base;
import com.vet.rcp.modelo.Analisis;
import com.vet.rcp.modelo.Paciente;

public class AdminAnalisis {
	public Collection<Analisis> obtenerAnalisis(Paciente paciente){
		Collection<Analisis> col = new ArrayList<Analisis>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			ResultSet rs = Base.executeQuery("select * from analisis where" +
					" idpaciente=" + paciente.getIdPaciente());
			while (rs.next()){
				col.add(new Analisis(df.format(rs.getDate("fecha")),
						rs.getInt("idanalisis"), rs.getInt("idpaciente")));
			}
		}catch (Exception e){
			System.out.println("err en admin analisis..."+ e);
		}
		return col;
	}
	public Collection<Analisis> loadAnalisis(Analisis analisis){
		Collection<Analisis> col = new ArrayList<Analisis>();
		double valor =0;
		double minimo =0;
		double maximo =0;
		try {
			ResultSet rs = Base.executeQuery("select * from selanalisis where" +
					" idanalisis= " + analisis.getIdAnalisis());
			while (rs.next()){
				valor = round(rs.getDouble("valor"),1);
				minimo= round(rs.getDouble("minimo"),1);
				maximo = round (rs.getDouble("maximo"),1);

				col.add(new Analisis(rs.getString("panel"), rs.getString("item"),valor, minimo ,maximo,
						rs.getString("descripcion"), rs.getString("unidad"), rs.getBoolean("texto")));
			}
		}catch (Exception e){
			System.out.println("err en admin analisis..."+ e);
		}
		return col;
	}
	public Analisis altaAnalisis (Paciente paciente, String fecha){
		int id=0;
		try {
			ResultSet rs = Base.executeQuery("select * from analisis", true);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date fechadf= df.parse(fecha);
			rs.moveToInsertRow();
			rs.updateInt("idpaciente", paciente.getIdPaciente());
			rs.updateTimestamp("fecha", new Timestamp(fechadf.getTime()));
			rs.insertRow();
			rs.close();
			
			ResultSet rs1 = Base.executeQuery("select * from analisis order" +
					" by idanalisis");
			rs1.last();
			id= rs1.getInt("idanalisis");
			rs1.close();
		}catch (Exception e){
			System.out.println("err en alta analisis ..." + e);
		}
		return new Analisis(fecha, id,paciente.getIdPaciente());
	}
	public void GuardarAnalisis(int idAnalisis, Collection<Analisis>col) {
		try {
			Base.execute("delete from combina_analisis where idanalisis=" + idAnalisis);
			for (Analisis  xxx : col){
				ResultSet rs = Base.executeQuery("select * from combina_analisis", true);
				rs.moveToInsertRow();
				rs.updateInt("idanalisis", idAnalisis);
				rs.updateInt("iditem_panel", xxx.getIditem_panel());
				rs.updateDouble("valor", xxx.getValor());
				rs.updateString("descripcion", xxx.getDescripcion());
				rs.insertRow();
				rs.close();		
			}
		}catch (Exception e){
			System.out.println("err in guardarAnalisis..."+e);
		}
	}
	public void EliminarAnalisis (Analisis analisis){
		try {
			ResultSet rs= Base.executeQuery("SELECT * FROM selanalisis WHERE " +
					"	idanalisis=" + analisis.getIdAnalisis(), true);
			while (rs.next()){
				rs.deleteRow();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err en borrar analisis..."+ e);
		}
	}
	public static double round(double d, int decimalPlace){
	    // see the Javadoc about why we use a String in the constructor
	    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	  }

}
