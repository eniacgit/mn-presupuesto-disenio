package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.controladores;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.IFachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOReporte;

public class EnviarPresupuestoDelegate implements JavaDelegate{

	private final static Logger LOG = Logger.getLogger(EnviarPresupuestoDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		LOG.info("\n## ENVIAR PRESUPUESTO");
		
		//setear valores value object
		VOReporte voReporteParametros= new VOReporte(); 
		voReporteParametros.setNombrePresupuesto((String)execution.getVariable("COTIZACION"));
		voReporteParametros.setCliente((String)execution.getVariable("CLIENTE"));
		voReporteParametros.setEmail((String)execution.getVariable("EMAIL"));
		voReporteParametros.setTel((String)execution.getVariable("TEL"));
		voReporteParametros.setMoneda((String)execution.getVariable("moneda"));
		voReporteParametros.setPrecio((String)execution.getVariable("PRECIO"));
		voReporteParametros.setNombreProducto((String)execution.getVariable("PRODUCTO_SELECCIONADO"));
		voReporteParametros.setDescripcion((String)execution.getVariable("DESCRIPCION"));
		voReporteParametros.setDimensiones((String)execution.getVariable("DIMENSIONES"));
		voReporteParametros.setCondiciones((String)execution.getVariable("CONDICIONES"));
		voReporteParametros.setFormaDePago((String)execution.getVariable("FORMA_DE_PAGO"));
		voReporteParametros.setTiempoDeEntrega((String)execution.getVariable("TIEMPO_DE_ENTREGA"));
		voReporteParametros.setTipoCliente((String)execution.getVariable("tipoCliente"));
		voReporteParametros.setMateriales((String)execution.getVariable("MATERIALES"));
		
		voReporteParametros.setUrlImagen("https://i0.wp.com/www.arkiplus.com/wp-content/uploads/2014/11/dise%C3%B1ador-muebles.jpg");
		
		IFachada iFachada = Fachada.getSingletonInstance();
		iFachada.generarReporte(voReporteParametros);
		
	}

}
