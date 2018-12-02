package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.controladores;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.IFachada;

public class GenerarCotizacionDelegate implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		IFachada iFachada = Fachada.getSingletonInstance();
		execution.setVariable("COTIZACION", iFachada.generarNroCotizacionFechaActual());
		
		execution.setVariable("CATEGORIA_PRODUCTO", "DISEÑO");
		
	}

}
