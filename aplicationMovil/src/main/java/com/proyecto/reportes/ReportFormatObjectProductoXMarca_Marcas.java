package com.proyecto.reportes;

import java.util.List;

public class ReportFormatObjectProductoXMarca_Marcas {
	
	private String descripcion, listaPrecio1, listaPrecio2;
	private List<ReportFormatObjectProductoXMarca_Marcas_Detalles> detalles;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<ReportFormatObjectProductoXMarca_Marcas_Detalles> getDetalles() {
		return detalles;
	}
	public void setDetalles(
			List<ReportFormatObjectProductoXMarca_Marcas_Detalles> detalles) {
		this.detalles = detalles;
	}

	public String getListaPrecio1() {
		return listaPrecio1;
	}

	public void setListaPrecio1(String listaPrecio1) {
		this.listaPrecio1 = listaPrecio1;
	}

	public String getListaPrecio2() {
		return listaPrecio2;
	}

	public void setListaPrecio2(String listaPrecio2) {
		this.listaPrecio2 = listaPrecio2;
	}
}
