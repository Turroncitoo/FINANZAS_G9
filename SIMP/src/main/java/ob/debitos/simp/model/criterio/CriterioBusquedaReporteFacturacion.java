package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.utilitario.StringsUtils;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaReporteFacturacion
{
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;

	private String descripcionFechaProceso;

	private int codigoMoneda;
	private String verbo;

	private Integer codigoInstitucion;
	private String descripcionInstitucion;

	private String idEmpresa;
	private String descripcionEmpresa;

	private String idCliente;
    private List<String> clientes;
	public String getClientesCadena()
    {
        return (this.clientes == null || this.clientes.isEmpty()) ? "-1" : StringUtils.join(StringsUtils.agregarComillaSimpleLista(this.clientes), ',');
    }
    private String descripcionCliente;

	private String idConceptoComision;
	private List<String> conceptosComision;
	private String descripcionConceptoComision;
}
