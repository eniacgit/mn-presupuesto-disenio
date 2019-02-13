package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.controladores;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.IFachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOArchivoAdjunto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOEmail;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOReporte;

public class EnviarPresupuestoDelegate implements JavaDelegate{

	private final static Logger LOG = Logger.getLogger(EnviarPresupuestoDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {		
		
			
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
		
		//voReporteParametros.setUrlImagen("https://i0.wp.com/www.arkiplus.com/wp-content/uploads/2014/11/dise%C3%B1ador-muebles.jpg");
		
		IFachada iFachada = Fachada.getSingletonInstance();
		iFachada.generarReporte(voReporteParametros);
		

		LOG.info("\n\n## PRESUPUESTO DISENIO");
		
		//enviar presupuesto por email		
		Properties p = new Properties();
		p.load(new FileInputStream("config/parametros.txt"));
		String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");		
						
		String nombreArchivoAdjunto="Cotizacion_DISENIO_" + voReporteParametros.getNombrePresupuesto() + "_" + voReporteParametros.getCliente().replace(' ' , '_') +".pdf" ;
		
		LOG.info("\n## REPORTE GENERADO CON EXITO (" + nombreArchivoAdjunto + ")");
		
		InputStream imgAdjunta = (ByteArrayInputStream)execution.getVariable("IMG_PRODUCTO");
		String nombreImagen = "IMG_"+ voReporteParametros.getNombrePresupuesto() + "_" + voReporteParametros.getCliente().replace(' ' , '_') +".png"; 
		File newFile = new File(rutaArchivoAdjunto + nombreImagen);
		FileOutputStream fos = new FileOutputStream(newFile);
		int data;
		while ((data=imgAdjunta.read())!=-1){
			char ch = (char)data;
			fos.write(ch);			
		}
		fos.flush();
		fos.close();	
		
				
		// obtengo imagen del servidor de imagenes de tomcat ( para configurarlo ver: https://stackoverrun.com/es/q/60423) 
		voReporteParametros.setUrlImagen("http://localhost:8080/mngest/images/" + nombreImagen);
		execution.setVariable("rutaImagen",voReporteParametros.getUrlImagen());	
		
		
		// almaceno rutas de archivo pdf para que mas adelante se puedan enviar a la carpeta
		// rechazados si corresponde				
		execution.setVariable("nombreReportePDF", nombreArchivoAdjunto);
		execution.setVariable("nombreImagen", nombreImagen);
		execution.setVariable("rutaArchivoAdjunto", rutaArchivoAdjunto);
		
		LOG.info("\n## --------------------------------------------------------");
		LOG.info("\n\n## IMG: " + rutaArchivoAdjunto + nombreImagen);
		
		
		//IFachada iFachada = Fachada.getSingletonInstance();
		iFachada.generarReporte(voReporteParametros);
		
		
		
		
		VOArchivoAdjunto arch1 = new VOArchivoAdjunto();
		arch1.setRutaArchivoAdjunto(rutaArchivoAdjunto);
		arch1.setNombreArchivoAdjunto(nombreArchivoAdjunto);
		
		// ArrayList de archivos adjuntos (reporte pdf, cronograma pdf)
		ArrayList<VOArchivoAdjunto> lstArchivosAdjuntos = new ArrayList<VOArchivoAdjunto>();
		lstArchivosAdjuntos.add(arch1);
		

		VOEmail voEmail = new VOEmail();
		voEmail.setDestinatario(voReporteParametros.getEmail());
		voEmail.setAsunto("Correo de prueba enviado desde proceso en camunda mediante Java");
		voEmail.setCuerpo("Estimado " + voReporteParametros.getCliente() +
				":\n\n Esta es una prueba de correo, y si lo estas viendo que es que quedó resuelto como mandar mails desde camunda...");
		voEmail.setLstArchivosAdjuntos(lstArchivosAdjuntos);

		
		iFachada.enviarConGmail(voEmail);
		
		LOG.info("\n## REPORTE GENERADO ENVIADO CON EXITO(" + nombreArchivoAdjunto + ")");
		
		
	}

}
