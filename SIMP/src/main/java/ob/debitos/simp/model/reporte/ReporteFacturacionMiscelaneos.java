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
public class ReporteFacturacionMiscelaneos
{
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaProceso;
	private Integer codigoInstitucion;
	private String descripcionInstitucion;
	private String idEmpresa;
    private String descEmpresa;
	private String idCliente;
    private String descCliente;
	private String idMembresia;
	private String descMembresia;
	// private String claseServicio;
    private String idClaseServicio;
	private String descClaseServicio;
	private Integer idOrigen;
	private String descOrigen;
	private Integer idClaseTransaccion;
	private String descClaseTransaccion;
	// private String descripcionClaseTransaccion;
	private Integer idCodigoTransaccion;
	private String descCodigoTransaccion;
	private Integer secuenciaTransaccion;
	private String cuentaCargo;
	private String cuentaAbono;
	private double valorCompensacion;
	private String glosaRegularizacion;
	private String codigoEventoMarcaInternacional;
	private String distribucionCobroMarcaInternacional;
	private String indicadorDistribucionCobro;
	private String numeroFacturaMarca;
	private Double tarifaMarcaInternacional;
	private Integer totalUnidades;
	private String indicadorDistribucionCodigo;
	private Integer indicadorUnidades;
	private Double valorFacturaMarcaInternacional;
	private String secuenciaRegistroFacturaMarca;
}
