package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.utilitario.StringsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.service.impl.reporte.ReporteCompensacionService;

/**
 * Representa el criterio de búsqueda para los reporte resumen de transacciones
 * de compensación agrupadas por canal e institución.
 * 
 * @author Stevens Sifuentes
 * @see ReporteCompensacionService
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaCompensacion
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    
    private String descripcionRangoFechas;

    private String verbo;

    private Integer rolTransaccion;
    private String descripcionRolTransaccion;

    private Integer codigoRespuestaTransaccion;
    private String descripcionCodigoRespuestaTransaccion;

    private Integer tipoMoneda;
    private String descripcionTipoMoneda;
    
    private Integer idInstitucion;
    private String descripcionInstitucion;

    private String idEmpresa;
    private String descripcionEmpresa;

    private List<String> clientes;

    public String getClientesCadena()
    {
        return (this.clientes == null || this.clientes.isEmpty()) ? "-1" : StringUtils.join(StringsUtils.agregarComillaSimpleLista(this.clientes), ',');
    }

    private String idCliente;
    private String descripcionCliente;

    private String tipoCompensacion;
}