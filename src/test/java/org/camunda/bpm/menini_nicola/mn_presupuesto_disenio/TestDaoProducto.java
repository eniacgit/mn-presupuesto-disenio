package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Producto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOProducto;

public class TestDaoProducto {

	public static void main(String[] args) {
		Producto producto = new Producto();
		producto.setNombre("MESA DE LUZ");
		producto.setDescripcion("Mesita de luz finamente terminada en madera y marmol");
		
		producto.setCosto(1800F);
		producto.setDescuento(0);
		producto.setSobreCosto(0);
		producto.setIdCategoria(8);
		producto.setIdPresupuesto(27);
				
		DAOProducto dao = new DAOProducto();
		
		int rowCount = dao.inserarProducto(producto);
		
		if (rowCount > 0)
			System.out.println("PRODUCTO INSERTADO CON ÉXITO!!");
		else
			System.out.println("ERROR: NO SE HA PODIDO INSERTAR EL PRODUCTO");

	}

}
