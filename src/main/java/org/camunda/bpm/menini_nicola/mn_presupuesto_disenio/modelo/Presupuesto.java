package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo;

import java.util.Date;

public class Presupuesto {
	
	protected String cotizacion;
	protected String fecha;
	protected String moneda;
	protected float costo;
	protected String condicionesVenta;
	protected String descripcion;
	
	public Presupuesto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Presupuesto(String cotizacion, String fecha, String moneda, float costo, String condicionesVenta,
			String descripcion) {
		super();
		this.cotizacion = cotizacion;
		this.fecha = fecha;
		this.moneda = moneda;
		this.costo = costo;
		this.condicionesVenta = condicionesVenta;
		this.descripcion = descripcion;
	}

	public String getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getCondicionesVenta() {
		return condicionesVenta;
	}

	public void setCondicionesVenta(String condicionesVenta) {
		this.condicionesVenta = condicionesVenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
}
