package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;

public interface IFachada {

	String generarNroCotizacionFechaActual();

	int insertarCliente(VOCliente voCliente);

	boolean existeCliente(String nombre);

}
