package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Presupuesto;
import org.jfree.util.Log;


public class DAOPresupuestoDisenio {
	
	// convierte una fecha de tipo Date de java.sql a String con formato "yyyy-MM-dd" para insertar en la BD de MySQL
	private String fechaToMysql(Date fecha) {
		String fechaMysql=null;
		String format = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(format);		
		fechaMysql =df.format(fecha);
		return fechaMysql;
	}
	
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
	
	
	public int insertarPresupuesto(Presupuesto presupuesto)  {
		AccesoBD accesoBD = new AccesoBD();
		Connection con = accesoBD.conectarBD();
		Consultas consultas = new Consultas();
		
		String insert = consultas.insertarPresupuesto();
		PreparedStatement pstmt = null;
		int rowCount=0;		
		
		try {
			pstmt = con.prepareStatement(insert);
			pstmt.setString(1, presupuesto.getCotizacion());
			pstmt.setString(2, presupuesto.getFecha());
			pstmt.setString(3, presupuesto.getMoneda());
			pstmt.setFloat(4, presupuesto.getCosto());
			pstmt.setString(5, presupuesto.getCondicionesVenta());
			pstmt.setString(6, presupuesto.getDescripcion());
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
	
	public int obtenerIdPresupuesto(String cotizacion) {
		AccesoBD accesoBD = new AccesoBD();
		Connection con = accesoBD.conectarBD();
		Consultas consultas = new Consultas();
		
		String select = consultas.obtenerIdPresupuesto();
		int idPresupuesto = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(select);
			pstmt.setString(1, cotizacion);
			rs = pstmt.executeQuery();
			rs.next();
			idPresupuesto = rs.getInt(1);			
		} catch (SQLException e) {
			Log.error(e + "EEROR 1");
			System.out.println(">> Tipo de datos incorrectos");
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
		return idPresupuesto;
	}
	
}
