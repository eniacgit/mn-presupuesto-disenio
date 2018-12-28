package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Cliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.ClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Presupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo.Producto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.AccesoBD;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOPresupuestoDisenio;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.persistencia.DAOProducto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOArchivoAdjunto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOEmail;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOPresupuesto;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOProducto;
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
		cliente.setRut(voCliente.getRut());
		cliente.setRazonSocial(voCliente.getRazonSocial());
		cliente.setTipo(voCliente.getTipo());
		cliente.setDireccion(voCliente.getDireccion());
		
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
	
	private static void addAtachment(Multipart multipart, String rutaArcvhivo, String nombreArchivo) throws MessagingException {
		DataSource source = new FileDataSource(rutaArcvhivo+nombreArchivo);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(nombreArchivo);
		multipart.addBodyPart(messageBodyPart);
	}
	
	@Override
	public void enviarConGmail (VOEmail voEmail) throws MessagingException {
		// Envia un correo electronico con archivos adjuntos (ArrayList) utilizando el email y contrasenia
		// almacenados en la tabla mn_email
			//String remitente = voEmail.getRemitente(); // este campo viene vacio
			String destinatario = voEmail.getDestinatario();
			String asunto = voEmail.getAsunto();
			String cuerpo = voEmail.getCuerpo();
			ArrayList<VOArchivoAdjunto> lstArchivosAdjuntos = voEmail.getLstArchivosAdjuntos();
			
			AccesoBD accesoBD = new AccesoBD();
			String email = accesoBD.obtenerRemitente();
			String [] arrayEmail = email.split("@");
			String remitente = arrayEmail[0];
			String clave = accesoBD.obtenerPasswordRemitente();			
			
			// Se obtiene el objeto Session. La configuración es para una cuenta de gmail
			Properties props = new Properties();		
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.user", "remitente");
			props.put("mail.smtp.clave", clave);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			
			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);
			message.addRecipients(Message.RecipientType.TO, destinatario);
			message.setSubject(asunto);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(cuerpo);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			
			// obtengo archivos adjuntos de la lista
			for (int i=0; i<lstArchivosAdjuntos.size();i++) {
				String rutaArchivo = lstArchivosAdjuntos.get(i).getRutaArchivoAdjunto();
				String nombreArchivo = lstArchivosAdjuntos.get(i).getNombreArchivoAdjunto();
				addAtachment(multipart, rutaArchivo, nombreArchivo);
			}
			message.setContent(multipart);
			
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente, clave);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();		
		}
	
	@Override
	public int insertarPresupuesto(VOPresupuesto voPresupuesto)  {
		DAOPresupuestoDisenio dao = new DAOPresupuestoDisenio();
		Presupuesto presupuesto = new Presupuesto();
		
		presupuesto.setCotizacion(voPresupuesto.getCotizacion());
		presupuesto.setFecha(voPresupuesto.getFecha());
		presupuesto.setMoneda(voPresupuesto.getMoneda());
		presupuesto.setCosto(voPresupuesto.getCosto());
		presupuesto.setCondicionesVenta(voPresupuesto.getCondicionesVenta());
		presupuesto.setDescripcion(voPresupuesto.getDescripcion());
		
		return dao.insertarPresupuesto(presupuesto);
	}
	
	@Override
	public int insertarClientePresupuesto(VOClientePresupuesto voClientePresupuesto) {
		DAOClientePresupuesto dao = new DAOClientePresupuesto();
		ClientePresupuesto clientePresupuesto = new ClientePresupuesto();
		
		clientePresupuesto.setEstado(voClientePresupuesto.getEstado());
		clientePresupuesto.setIdCliente(voClientePresupuesto.getIdCliente());
		clientePresupuesto.setIdPresupuesto(voClientePresupuesto.getIdPresupuesto());
		
		return dao.insertarClientePresupuesto(clientePresupuesto);
	}
	
	@Override
	public int obtenerIdCliente(String nombre) {
		DAOCliente dao = new DAOCliente();
		return dao.obtenerIdCliente(nombre);
	}
	
	@Override
	public int obtenerIdPresupuesto(String cotizacion) {
		DAOPresupuestoDisenio dao = new DAOPresupuestoDisenio();
		return dao.obtenerIdPresupuesto(cotizacion);
	}
	
	@Override
	public int inserarProducto(VOProducto voProducto) {
		DAOProducto dao = new DAOProducto();
		Producto producto = new Producto();
		
		producto.setNombre(voProducto.getNombre());
		producto.setDescripcion(voProducto.getDescripcion());
		producto.setCosto(voProducto.getCosto());
		producto.setDescuento(voProducto.getDescuento());
		producto.setSobreCosto(voProducto.getSobreCosto());
		producto.setIdCategoria(voProducto.getIdCategoria());
		producto.setIdPresupuesto(voProducto.getIdPresupuesto());
		
		return dao.inserarProducto(producto);
	}
	
	@Override
	public int obtenerIdCategoria(String categoria) {
		DAOProducto dao = new DAOProducto();
		return dao.obtenerIdCategoria(categoria);
	}

}
