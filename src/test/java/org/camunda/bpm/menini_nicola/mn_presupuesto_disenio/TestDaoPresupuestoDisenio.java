package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOPresupuestoDisenio;

public class TestDaoPresupuestoDisenio {

	public static void main(String[] args) {
		DAOPresupuestoDisenio dao = new DAOPresupuestoDisenio();
		System.out.println("COTIZACION: " + dao.generarNroCotizacionFechaActual());
		
		
		

	}

}
