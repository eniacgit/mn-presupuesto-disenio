package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DAOPresupuestoDisenio {
	
	public boolean existeNroCotización(String nroCotizacion) {
		AccesoBD accesoBD = new AccesoBD();
		// Retorna true si el nro de la cotizacion ya existe en la bd
			boolean existeCliente = false;
			Connection con = con = accesoBD.conectarBD();
			Consultas consultas = new Consultas();
			
			String select = consultas.existeNroCotización();
			try {
				PreparedStatement pstmt = con.prepareStatement(select);
				pstmt.setString(1, nroCotizacion);
				
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
	
	public String generarNroCotizacionFechaActual() {
		// A partir de la fecha actual genera un nro de cotizacion
		// Si ya hay una cotizcion para el dia actual incremente el digito del indice
		// Ejemplo: Si ya existe la cotizacion 180924-01,la siguiente sera 180924-02 
		
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Calendar cal = Calendar.getInstance();
		
		// Obtengo nro de cotizacion del dia actual
		String fecha = dateFormat.format(cal.getTime());
		String nroCotizacion = fecha  + "-01";
		int cont=1;
		while (existeNroCotización(nroCotizacion)) {
			cont++;
			nroCotizacion = fecha + "-" + String.format("%02d",cont);
		}		
		return nroCotizacion;
	}
	
}
