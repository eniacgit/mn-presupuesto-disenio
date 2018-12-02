package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio;

import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica.IFachada;
import org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects.VOReporte;

public class TestReporteJasper {

	public static void main(String[] args) {
		
		VOReporte voReporteParametros= new VOReporte(); 
		voReporteParametros.setNombrePresupuesto("20181202-01");
		voReporteParametros.setCliente("Naara Barrios");
		voReporteParametros.setEmail("deleon.danielo@gmail.com");
		voReporteParametros.setTel("26198548");
		voReporteParametros.setMoneda("Pesos");
		voReporteParametros.setPrecio("1800");
		voReporteParametros.setNombreProducto("Rosca de chicharrones");
		voReporteParametros.setDescripcion("Rosca artesanal hecha con productos naturales y de muy buena calidad");
		voReporteParametros.setDimensiones("15 x 15 x 8 cm ");
		voReporteParametros.setCondiciones("Se paga al contado y se recibe el producto de forma instantanea");
		voReporteParametros.setFormaDePago("contado rabioso");
		voReporteParametros.setTiempoDeEntrega("24hs");
		voReporteParametros.setTipoCliente("consumidor final");
		voReporteParametros.setMateriales("harina, huevos, gerasa de chancho, sal");
		voReporteParametros.setUrlImagen("https://i0.wp.com/www.arkiplus.com/wp-content/uploads/2014/11/dise%C3%B1ador-muebles.jpg");
		
		
		IFachada iFachada = Fachada.getSingletonInstance();
		iFachada.generarReporte(voReporteParametros);

	}

}
