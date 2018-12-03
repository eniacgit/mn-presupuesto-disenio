package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

import javax.mail.MessagingException;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOEmail;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOReporte;

public interface IFachada {

	String generarNroCotizacionFechaActual();

	int insertarCliente(VOCliente voCliente);

	boolean existeCliente(String nombre);

	void generarReporte(VOReporte voReporteParametros);

	void enviarConGmail(VOEmail voEmail) throws MessagingException;

}
