package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects;

import java.util.Date;

public class VOPresupuesto {
	private int idPresupuesto;
	private String cotizacion;
	private String fecha;
	private String moneda;
	private float costo;
	private String condicionesVenta;
	private String descripcion;
	private int unidades;  // TIENE QUE GUARDAR 1 EN LA mn_presupuesto
	
	
	public VOPresupuesto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOPresupuesto(int idPresupuesto, String cotizacion, String fecha, String moneda, float costo,
			String condicionesVenta, String descripcion, int unidades) {
		super();
		this.idPresupuesto = idPresupuesto;
		this.cotizacion = cotizacion;
		this.fecha = fecha;
		this.moneda = moneda;
		this.costo = costo;
		this.condicionesVenta = condicionesVenta;
		this.descripcion = descripcion;
		this.unidades = unidades;
	}

	public int getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(int idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
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

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	
	
}
