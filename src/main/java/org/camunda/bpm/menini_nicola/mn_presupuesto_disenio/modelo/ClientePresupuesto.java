package org.camunda.bpm.menini_nicola.mn_presupuesto_disenio.modelo;

public class ClientePresupuesto {
	
	protected byte estado;
	protected int idCliente;
	protected int idPresupuesto;
	
	public ClientePresupuesto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClientePresupuesto(byte estado, int idCliente, int idPresupuesto) {
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
