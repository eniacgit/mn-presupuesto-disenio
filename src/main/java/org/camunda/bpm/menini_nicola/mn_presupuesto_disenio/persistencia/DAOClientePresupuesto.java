package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.ClientePresupuesto;
import org.jfree.util.Log;

public class DAOClientePresupuesto {

	public int insertarClientePresupuesto(ClientePresupuesto clientePresupuesto) {
		int rowCount = 0;
		AccesoBD accesoBD = new AccesoBD();
		Connection con = accesoBD.conectarBD();
		Consultas consultas = new Consultas();
		
		String insert = consultas.insertarClientePresupuesto();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(insert);
			pstmt.setByte(1, clientePresupuesto.getEstado());
			pstmt.setInt(2, clientePresupuesto.getIdCliente());
			pstmt.setInt(3, clientePresupuesto.getIdPresupuesto());
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
	
	
	
}
