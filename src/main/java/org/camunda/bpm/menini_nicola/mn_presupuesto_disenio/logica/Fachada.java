package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Cliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOPresupuestoDisenio;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;



public class Fachada implements IFachada{
	
	private static IFachada fachada;
	
	// El constructor es privado, no permite que se genere un constructor por defecto
	private Fachada() {
		
	}
	
	public static IFachada getSingletonInstance() {
		if (fachada==null) {
			fachada = new Fachada();
		}
		return fachada;
	}
	
	@Override
	public String generarNroCotizacionFechaActual() {
		DAOPresupuestoDisenio dao = new DAOPresupuestoDisenio();
		return dao.generarNroCotizacionFechaActual();
	}
	
	
	@Override
	public int insertarCliente(VOCliente voCliente) {
		DAOCliente dao = new DAOCliente();
		Cliente cliente = new Cliente();
		
		cliente.setNombre(voCliente.getNombre());
		cliente.setEmail(voCliente.getEmail());
		cliente.setTelefono(voCliente.getTelefono());
		cliente.setCelular(voCliente.getCelular());		
		
		return dao.insertarCliente(cliente);
	}
	
	@Override
	public boolean existeCliente(String nombre) {
		DAOCliente dao = new DAOCliente();
		return dao.existeCliente(nombre);
	}
	

}
