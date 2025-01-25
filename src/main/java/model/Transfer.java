package model;

import java.time.LocalDate;

public class Transfer {
	 private int idTransferencia;
	    private int cuentaOrigen;
	    private int cuentaDestino;
	    private double cantidad;
	    private String estado; // COMPLETADA, ANULADA, etc.
	    private LocalDate fecha;

	    // Constructor por defecto
	    public Transfer() {}

	    // Constructor parametrizado
	    public Transfer(int idTransferencia, int cuentaOrigen, int cuentaDestino, double cantidad, String estado, LocalDate fecha) {
	        this.idTransferencia = idTransferencia;
	        this.cuentaOrigen = cuentaOrigen;
	        this.cuentaDestino = cuentaDestino;
	        this.cantidad = cantidad;
	        this.estado = estado;
	        this.fecha = fecha;
	    }

		public int getIdTransferencia() {
			return idTransferencia;
		}

		public void setIdTransferencia(int idTransferencia) {
			this.idTransferencia = idTransferencia;
		}

		public int getCuentaOrigen() {
			return cuentaOrigen;
		}

		public void setCuentaOrigen(int cuentaOrigen) {
			this.cuentaOrigen = cuentaOrigen;
		}

		public int getCuentaDestino() {
			return cuentaDestino;
		}

		public void setCuentaDestino(int cuentaDestino) {
			this.cuentaDestino = cuentaDestino;
		}

		public double getCantidad() {
			return cantidad;
		}

		public void setCantidad(double cantidad) {
			this.cantidad = cantidad;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public LocalDate getFecha() {
			return fecha;
		}

		public void setFecha(LocalDate fecha) {
			this.fecha = fecha;
		}
	    
	    
}
