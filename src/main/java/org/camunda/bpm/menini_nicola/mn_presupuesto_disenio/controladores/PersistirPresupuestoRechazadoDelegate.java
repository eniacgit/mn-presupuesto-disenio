package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.controladores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.IFachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOPresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOProducto;



public class PersistirPresupuestoRechazadoDelegate implements JavaDelegate{
	
	private final static Logger LOG = Logger.getLogger(PersistirPresupuestoRechazadoDelegate.class.getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		IFachada iFachada = Fachada.getSingletonInstance();
		
		//traer valores del formulario para persistir en tabla mn_cliente
		VOCliente voCliente = new VOCliente();
		voCliente.setNombre((String) execution.getVariable("CLIENTE"));
		voCliente.setCelular((String)execution.getVariable("CELULAR"));
		voCliente.setTelefono((String)execution.getVariable("TEL"));
		voCliente.setEmail((String) execution.getVariable("EMAIL"));		
		
		LOG.info("\n\n=== mn_cliente ======================================================================");
		int rowCount = 0;
		if (!iFachada.existeCliente(voCliente.getNombre())) {
			rowCount = iFachada.insertarCliente(voCliente);
		}
		
		if (rowCount > 0)
			LOG.info("\n## Se insertó cliente en la BD.\nCantidad de registros afectados: " + rowCount);
		else
			LOG.info("\n## Cantidad de registros afectados: " + rowCount + "\nNo se insertó cliente en la BD");
		
						
		// traer valores del formulario para persistir en tabla mn_presupuesto
		VOPresupuesto voPresupuesto = new VOPresupuesto();
		
		voPresupuesto.setCotizacion((String) execution.getVariable("COTIZACION"));
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String fecha = dateFormat.format(date);
		voPresupuesto.setFecha(fecha);
		
		String moneda = (String) execution.getVariable("moneda");
		if (moneda.equals("dolares"))
			moneda = "USD";
		else
			moneda ="$U";
		
		voPresupuesto.setMoneda(moneda);
		voPresupuesto.setCosto(Float.parseFloat((String)execution.getVariable("PRECIO")));
		voPresupuesto.setCondicionesVenta((String) execution.getVariable("CONDICIONES"));
		voPresupuesto.setDescripcion((String) execution.getVariable("DESCRIPCION"));		
		
		// persistir en tabla mn_cliente_presupuesto		
		rowCount = 0;
		rowCount = iFachada.insertarPresupuesto(voPresupuesto);
		
		if (rowCount > 0)
			LOG.info("\n\n## Se insertó presupuesto en la BD.\nCantidad de registros afectados: " + rowCount);
		else
			LOG.info("\n\n## Cantidad de registros afectados: " + rowCount + "\nNo se insertó presupuesto en la BD");
		
		LOG.info("\n\n=== mn_cliente-presupuesto ==========================================================");
		
		// persistir en tabla mn_cliente_presupuesto
		int idCliente = iFachada.obtenerIdCliente(voCliente.getNombre());
		LOG.info("\n\n## idCliente: " + idCliente);		
		
		int idPresupuesto = iFachada.obtenerIdPresupuesto(voPresupuesto.getCotizacion());
		LOG.info("\n\n## idPresupuesto: " + idPresupuesto);		
		
		// estado:
		//	1 = aprobado
		//  0 = rechazado
		byte estado = 0;
		
		VOClientePresupuesto voClientePresupuesto = new VOClientePresupuesto();
		voClientePresupuesto.setEstado(estado);
		voClientePresupuesto.setIdCliente(idCliente);
		voClientePresupuesto.setIdPresupuesto(idPresupuesto);
		
		rowCount = 0;
		rowCount = iFachada.insertarClientePresupuesto(voClientePresupuesto);
		
	
		
		if (rowCount > 0)
			LOG.info("\n## Se insertó cliente-presupuesto en la BD.\nCantidad de registros afectados: " + rowCount);
		else
			LOG.info("\n## Cantidad de registros afectados: " + rowCount + "\nNo se insertó cliente-presupuesto en la BD");		
		
		// persistir datos del producto en mn_producto
		String nombreProducto = (String)execution.getVariable("PRODUCTO_SELECCIONADO");
		int idCategoria = iFachada.obtenerIdCategoria("DISEÑO");
		
		VOProducto voProducto = new VOProducto();
		voProducto.setNombre(nombreProducto);
		voProducto.setDescripcion(voPresupuesto.getDescripcion());
		voProducto.setCosto(voPresupuesto.getCosto());
		voProducto.setDescuento(0);
		voProducto.setSobreCosto(0);
		voProducto.setIdCategoria(idCategoria);
		voProducto.setIdPresupuesto(idPresupuesto);
		
		rowCount = 0;
		rowCount = iFachada.inserarProducto(voProducto);
				
		if (rowCount > 0)
			LOG.info("\n## Se insertó producto en la BD.\nCantidad de registros afectados: " + rowCount);
		else
			LOG.info("\n## Cantidad de registros afectados: " + rowCount + "\nNo se insertó producto en la BD");			
	}

}
