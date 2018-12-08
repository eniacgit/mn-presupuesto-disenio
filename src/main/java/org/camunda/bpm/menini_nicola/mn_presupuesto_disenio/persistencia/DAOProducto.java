package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Producto;
import org.jfree.util.Log;

public class DAOProducto {
	
	public int inserarProducto(Producto producto) {
		AccesoBD accesoBD = new AccesoBD();		
		Connection con = accesoBD.conectarBD();
		Consultas consultas = new Consultas();
		
		String insert = consultas.insertarProducto();
		int rowCount=0;
		PreparedStatement pstmt = null;
		
		try {
			pstmt= con.prepareStatement(insert);
			pstmt.setString(1, producto.getNombre());
			pstmt.setString(2, producto.getDescripcion());
			pstmt.setFloat(3, producto.getCosto());
			pstmt.setFloat(4, producto.getDescuento());
			pstmt.setFloat(5, producto.getSobreCosto());
			pstmt.setInt(6, producto.getIdCategoria());
			pstmt.setInt(7, producto.getIdPresupuesto());
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
	
	public int obtenerIdCategoria(String categoria) {
		AccesoBD accesoBD = new AccesoBD();		
		Connection con = accesoBD.conectarBD();
		Consultas consultas = new Consultas();
		
		String select = consultas.obtenerIdCategoria();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int idCategoria =0;
		
		try {
			pstmt = con.prepareStatement(select);
			pstmt.setString(1, categoria);
			rs = pstmt.executeQuery();
			rs.next();
			idCategoria = rs.getInt(1);
			
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
		return idCategoria;
		
	}
	
}
