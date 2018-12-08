package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.valueObjects;

public class VOClientePresupuesto {
	
	private byte estado;
	private int idCliente;
	private int idPresupuesto;
	
	public VOClientePresupuesto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOClientePresupuesto(byte estado, int idCliente, int idPresupuesto) {
		super();
		this.estado = estado;
		this.idCliente = idCliente;
		this.idPresupuesto = idPresupuesto;
	}

	public byte getEstado() {
		return estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(int idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}	
}
