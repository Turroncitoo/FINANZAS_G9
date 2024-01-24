package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaReporteInterfaceContable
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaProcesoInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaProcesoFin;
    private String descFechaProceso;
    private Integer idInstitucion;
    private String descripcionInstitucion;
    private String idEmpresa;
    private String descripcionEmpresa;
    private String idCliente;
    private List<String> clientes;
    private String descripcionCliente;
}
