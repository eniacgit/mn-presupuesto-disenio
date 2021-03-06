package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo;

public class Producto {
	
	protected String nombre;
	protected String descripcion;
	protected float costo;
	protected float descuento;
	protected float sobreCosto;
	protected int idCategoria;
	protected int idPresupuesto;
	
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Producto(String nombre, String descripcion, float costo, float descuento, float sobreCosto, int idCategoria,
			int idPresupuesto) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.descuento = descuento;
		this.sobreCosto = sobreCosto;
		this.idCategoria = idCategoria;
		this.idPresupuesto = idPresupuesto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public float getSobreCosto() {
		return sobreCosto;
	}

	public void setSobreCosto(float sobreCosto) {
		this.sobreCosto = sobreCosto;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(int idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	
}
