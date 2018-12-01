package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.logica;

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
	
	
	
	
	

}
