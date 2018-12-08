package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.ClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOClientePresupuesto;

public class TestDAOClientePresupuesto {

	public static void main(String[] args) {
		
		DAOClientePresupuesto dao = new DAOClientePresupuesto();
		
		ClientePresupuesto clientePresupuesto = new ClientePresupuesto();
		
		/*
		 * protected byte estado;
	protected int idCliente;
	protected int idPresupuesto;
		 */
		byte estado = 0;
		clientePresupuesto.setEstado(estado);
		clientePresupuesto.setIdCliente(18);
		clientePresupuesto.setIdPresupuesto(18);
		
		int rowCount = dao.insertarClientePresupuesto(clientePresupuesto);
		
		if (rowCount > 0)
			System.out.println("CLIENTE PRESUPUESTO INSERTADO CON EXITO!!");
		else
			System.out.println("ERROR: CLIENTE PRESUPUESTO NO INSERTADO!!");
		

	}

}
