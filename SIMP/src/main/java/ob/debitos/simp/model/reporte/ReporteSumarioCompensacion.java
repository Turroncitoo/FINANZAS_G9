package ob.debitos.simp.model.reporte;

import lombok.Data;

@Data
public class ReporteSumarioCompensacion {

	private Integer idInstitucion;
	private String descripcionInstitucion;
	private String codigoMembresia;
	private String descripcionMembresia;
	private String codigoClaseServicio;
	private String descripcionClaseServicio;
	private int cantidad;
	private double montoFondoDeudor;
	private double montoFondoAcreedor;
	private double montoComisionDeudor;
	private double montoComisionAcreedor;
	private double montoMiscelaneoDeudor;
	private double montoMiscelaneoAcreedor;
}
