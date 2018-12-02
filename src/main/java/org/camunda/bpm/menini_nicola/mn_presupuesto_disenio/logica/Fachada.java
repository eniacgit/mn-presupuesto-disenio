package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Cliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOPresupuestoDisenio;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOReporte;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;



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
	
	@Override
	public void generarReporte(VOReporte voReporteParametros) {
		HashMap parametros = new HashMap<String, Object>();
		parametros.put("cotizacion", voReporteParametros.getNombrePresupuesto());
		//fecha, no es necesario, se genera en el jasper
		parametros.put("cliente", voReporteParametros.getCliente());
		parametros.put("email", voReporteParametros.getEmail());
		parametros.put("tel", voReporteParametros.getTel());

		parametros.put("nombre",voReporteParametros.getNombreProducto());
		parametros.put("dimensiones","Dimensiones:\n" + voReporteParametros.getDimensiones());
		parametros.put("materiales","Materiales:\n" + voReporteParametros.getMateriales());
		//parametros.put("terminacion",voReporteParametros.getTerminacion());
		parametros.put("descripcion",voReporteParametros.getDescripcion());
		
		String moneda ="";
		if (voReporteParametros.getMoneda().equals("dolares"))
			moneda = "USD";
		else
			moneda = "$U";
		
		parametros.put("precio","Precio unitario ("+ moneda +"): "  + voReporteParametros.getPrecio());
		//parametros.put("descuento", "Descuento (%): " + voReporteParametros.getDescuento());
		//parametros.put("sobrecosto", "Sobre costo (%): " + voReporteParametros.getSobreCosto());
		//parametros.put("precioFinal", "Precio final (" + moneda + "): " + voReporteParametros.getPrecioFinal());
		
		
		parametros.put("condiciones", voReporteParametros.getCondiciones());
		parametros.put("formaPago", voReporteParametros.getFormaDePago());
		
		
		
		// En el codigo de bpmn hay que setear lo siguiente:
		// <imageExpression><![CDATA[new java.net.URL($P{url})]]></imageExpression>
		// más info: https://coderanch.com/t/642610/java/Loading-byte-data-image-url
		parametros.put("urlimgProd", voReporteParametros.getUrlImagen());
		
		//parametros.put("tiempo_de_entrega", voReporteParametros.getTiempoDeEntrega());
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("reportes//jasper//presupuestoDisenio.jasper");
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
	 
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream); 
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,new JREmptyDataSource());

			Properties p = new Properties();
			p.load(new FileInputStream("config/parametros.txt"));
			String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");

			// Se crea el archivo pdf con el nombre:
			// Ejemplo: Cotizacion_ESPACIO_180926-01_Fernando_Pelaez.pdf
			String cotizacion=(String)parametros.get("cotizacion");
			String cliente=(String)parametros.get("cliente");	

		
			String nombreArchivoAdjunto="Cotizacion_DISENIO_" + cotizacion + "_" + cliente.replace(' ' , '_') +".pdf" ;

			JasperExportManager.exportReportToPdfFile(jasperPrint,rutaArchivoAdjunto + nombreArchivoAdjunto);
			
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
