package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Cliente;
import org.jfree.util.Log;

public class DAOCliente {
	
	public boolean existeCliente(String nombre) {
		// Retorna true si el nombre del cliente ya fue dado de alta
		AccesoBD accesoBD = new AccesoBD();
			boolean existeCliente = false;
			Connection con = con = accesoBD.conectarBD();
			Consultas consultas = new Consultas();
			
			String select = consultas.existeCliente();
			try {
				PreparedStatement pstmt = con.prepareStatement(select);
				pstmt.setString(1, nombre);
				
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
					existeCliente = true;
				rs.close();
				pstmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			accesoBD.desconectarBD(con);
			return existeCliente;
		}

	public int insertarCliente(Cliente cliente) {
		AccesoBD accesoBD = new AccesoBD();		
		Connection con = accesoBD.conectarBD();
		Consultas consultas = new Consultas();
		
		int rowCount=0;
		String insert = consultas.insertarCliente();
		PreparedStatement pstmt = null;
		
		try {
			pstmt =con.prepareStatement(insert);
			
			pstmt.setString(1, cliente.getNombre());
			pstmt.setString(2, cliente.getEmail());
			pstmt.setString(3, cliente.getTelefono());
			pstmt.setString(4, cliente.getCelular());
			rowCount = pstmt.executeUpdate();			
		} catch (SQLException e) {
			Log.error(e + "EEROR 1");
			System.out.println(">> Tipo de datos incorrectos");
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				accesoBD.desconectarBD(con);
			} catch (SQLException e) {
				Log.error(e + "ERROR 2");
				System.out.println(">> ERROR 2");
				e.printStackTrace();
			}			
		}		
		return rowCount;
	}
	
	
	public int obtenerIdCliente(String nombre) {
		AccesoBD accesoBD = new AccesoBD();		
		Connection con = accesoBD.conectarBD();
		Consultas consultas = new Consultas();
		
		int idCliente = 0;
		String select = consultas.obtenerIdCliente();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(select);
			pstmt.setString(1, nombre);
			rs = pstmt.executeQuery();
			rs.next();
			idCliente = rs.getInt(1);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				accesoBD.desconectarBD(con);
			} catch (SQLException e) {
				Log.error(e + "ERROR 2");
				System.out.println(">> ERROR 2");
				e.printStackTrace();
			}			
		}				
		return idCliente;
	}
}
