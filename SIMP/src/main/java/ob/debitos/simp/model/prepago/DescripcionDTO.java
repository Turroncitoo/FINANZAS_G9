package ob.debitos.simp.model.prepago;

import java.io.Serializable;

public class DescripcionDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int tabla;
	private int subTabla;
	private int idElemento; //
	private String valorLiteral; //
	private String descripcion;
	private int valorNumerico;
	
	public int getTabla() {
		return tabla;
	}
	public void setTabla(int tabla) {
		this.tabla = tabla;
	}
	public int getSubTabla() {
		return subTabla;
	}
	public void setSubTabla(int subTabla) {
		this.subTabla = subTabla;
	}
	public int getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(int idElemento) {
		this.idElemento = idElemento;
	}
	public String getValorLiteral() {
		return valorLiteral;
	}
	public void setValorLiteral(String valorLiteral) {
		this.valorLiteral = valorLiteral;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getValorNumerico() {
		return valorNumerico;
	}
	public void setValorNumerico(int valorNumerico) {
		this.valorNumerico = valorNumerico;
	}
	@Override
	public String toString() {
		return "DTO_Descripcion [tabla=" + tabla + ", subTabla=" + subTabla + ", idElemento=" + idElemento
				+ ", valorLiteral=" + valorLiteral + ", valorNumerico=" + valorNumerico + "]";
	}
}
