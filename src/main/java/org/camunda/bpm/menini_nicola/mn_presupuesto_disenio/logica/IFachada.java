package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

import javax.mail.MessagingException;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Producto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOEmail;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOPresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOProducto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOReporte;

public interface IFachada {

	String generarNroCotizacionFechaActual();

	int insertarCliente(VOCliente voCliente);

	boolean existeCliente(String nombre);

	void generarReporte(VOReporte voReporteParametros);

	void enviarConGmail(VOEmail voEmail) throws MessagingException;

	int insertarPresupuesto(VOPresupuesto voPresupuesto);

	int insertarClientePresupuesto(VOClientePresupuesto voClientePresupuesto);

	int obtenerIdCliente(String nombre);

	int obtenerIdPresupuesto(String cotizacion);

	int inserarProducto(VOProducto voProducto);

	int obtenerIdCategoria(String categoria);
}
