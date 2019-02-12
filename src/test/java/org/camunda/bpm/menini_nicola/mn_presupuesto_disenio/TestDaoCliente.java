package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Cliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOCliente;

public class TestDaoCliente {

	public static void main(String[] args) {
		DAOCliente dao = new DAOCliente();

		Cliente cli = new Cliente();
		cli.setNombre("Isaac Asimov");
		cli.setEmail("isaac@asimov.com");
		cli.setTelefono("12345678");
		cli.setCelular("111111111");
		
		if (dao.existeCliente(cli.getNombre())) {
			System.out.println("## El cliente ya existe.");
		}else {
		
			int rowCount = dao.insertarCliente(cli);
			
			if (rowCount > 0)
				System.out.println("## Se insertó cliente en la BD.\nCantidad de registros afectados: " + rowCount);
			else
				System.out.println("## Cantidad de registros afectados: " + rowCount + "\nNo se insertó cliente en la BD");
			
			}	
	}
	
	
	
}
