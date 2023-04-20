package com.vet.rcp.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Base {
	static Connection conn;

	static {
		try {
			Class.forName("org.postgresql.Driver");

			}catch (ClassNotFoundException e) {
				System.err.println("Buscando driver: " + e);
			}
		
		try {
//			conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.100/ficha",
//					"postgres","mayodos");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ficha",
					"postgres","mayodos");
		}
		catch (SQLException e) {
			System.err.println("Conectando a la base de datos: " + e);
		}
	}

	public static void execute(String sql) {
		try {
			Statement stmt = conn.createStatement();

			stmt.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("Ejecutando consulta: " + e);
		}
	}

	public static ResultSet executeQuery(String sql) {
		ResultSet rs = null;

		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(sql);
                }
		catch (SQLException e) {
			System.err.println("Error en consulta: " + e);
		}

		return rs;
	}

        public static ResultSet executeQuery(String sql, boolean updatable) {
		ResultSet rs = null;

		try {                        
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);                        
		} catch (SQLException e) {
			System.err.println("Error en consulta: " + e);
		}
		return rs;
	}

	public static PreparedStatement prepareStatement(String sql) {
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			System.err.println("Prepare statement: " + e);
		}

		return stmt;
	}

	public static void close() {
		try {
			conn.close();
		}
		catch (SQLException e) {
			System.err.println("Cerrando conexión" + e);
		}
	}
}
