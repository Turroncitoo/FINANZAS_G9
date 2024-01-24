package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteFacturacionResumen
{
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaProceso;
	private Integer codigoInstitucion;
	private String descripcionInstitucion;
	private String idEmpresa;
    private String descEmpresa;
	private String idCliente;
    private String descCliente;
	private String idConceptoComision;
	private String descConceptoComision;
	private String aplicaComision;
	private String idMembresia;
    private String descMembresia;
	private String idClaseServicio;
    private String descClaseServicio;
	private String codigoFacturacion;
	private String descCodigoFacturacion;
	private String cuentaCargo;
	private String cuentaAbono;
	private Integer cantidad;
	private double comisionImporte;
	private double comisionIGV;
	private double comisionTotal;
	private double comisionPromedio;
}
