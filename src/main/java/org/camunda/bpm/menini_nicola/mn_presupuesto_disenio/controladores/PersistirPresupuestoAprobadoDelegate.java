package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.controladores;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class PersistirPresupuestoAprobadoDelegate implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		//traer valores del formulario para persistir en tabla mn_cliente
		String cliente= (String) execution.getVariable("CLIENTE");
		// tipo de cliente (consultar con menini nicola)
		String celular= (String)execution.getVariable("CELULAR");
		String tel= (String)execution.getVariable("TEL");
		String email= (String) execution.getVariable("EMAIL");
		
		// traer valores del formulario para persistir en tabla mn_presupuesto
		String cotizacion= (String) execution.getVariable("COTIZACION");
		// fecha
		String moneda= (String) execution.getVariable("moneda");		
		String precio=(String)execution.getVariable("PRECIO");		
		String condiciones= (String) execution.getVariable("CONDICIONES");		
		String descripcion= (String) execution.getVariable("DESCRIPCION");
		
		// persistir en tabla mn_cliente_presupuesto
				
				
		// persistir datos del producto en mn_producto
		String nombreProducto = (String)execution.getVariable("PRODUCTO_SELECCIONADO");
				
				
				
	}

}
