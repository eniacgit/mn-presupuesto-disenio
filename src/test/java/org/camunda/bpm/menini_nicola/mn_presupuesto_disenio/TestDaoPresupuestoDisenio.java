package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Presupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOPresupuestoDisenio;

public class TestDaoPresupuestoDisenio {

	public static void main(String[] args) {
		DAOPresupuestoDisenio dao = new DAOPresupuestoDisenio();
		System.out.println("COTIZACION: " + dao.generarNroCotizacionFechaActual());
		

		String fecha = "";
		DAOPresupuestoDisenio presupuestoDisenio = new DAOPresupuestoDisenio();
		int rowCount=0;

			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();			
			fecha = dateFormat.format(date);
			Presupuesto presupuesto = new Presupuesto();
			float costo = 1500;
			
			presupuesto.setCotizacion("181204-01");
			presupuesto.setFecha(fecha);
			presupuesto.setCosto(costo);
			presupuesto.setCondicionesVenta("CONDICIONES DE VENTA");
			presupuesto.setDescripcion("DESCRIPCION");
			presupuesto.setMoneda("dolar");
			
			rowCount = presupuestoDisenio.insertarPresupuesto(presupuesto);
			
			if (rowCount> 0)
				System.out.println("PRESUPUESTO INSERTADO CON EXITO!!");
			else
				System.out.println("ERROR: NO SE HA PODIDO INSERTAR EL PRESUPUESTO");
			
		
		
		

	}

}
