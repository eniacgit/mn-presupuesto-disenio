package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOReporte;

public interface IFachada {

	String generarNroCotizacionFechaActual();

	int insertarCliente(VOCliente voCliente);

	boolean existeCliente(String nombre);

	void generarReporte(VOReporte voReporteParametros);

}
