package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.utilitario.StringsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTransaccionSwDmpLog
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaProcesoInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaProcesoFin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaTransaccionInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaTransaccionFin;

    private Integer codigoInstitucion;

    private String idEmpresa;

    private List<String> clientes;

    public String getClientesCadena()
    {
        return (this.clientes == null || this.clientes.isEmpty()) ? "-1" : StringUtils.join(StringsUtils.agregarComillaSimpleLista(this.clientes), ',');
    }

    private List<Integer> rolesTransaccion;

    public String getRolesTransaccionCadena()
    {
        return (this.rolesTransaccion == null || this.rolesTransaccion.isEmpty()) ? "-1" : StringUtils.join(this.rolesTransaccion, ',');
    }

    private List<String> codigosProceso;

    public String getCodigosProcesoCadena()
    {
        return (this.codigosProceso == null || this.codigosProceso.isEmpty()) ? "-1" : StringUtils.join(StringsUtils.agregarComillaSimpleLista(this.codigosProceso), ',');
    }

    private List<String> codigosRespuesta;

    public String getCodigosRespuestaCadena()
    {
        return (this.codigosRespuesta == null || this.codigosRespuesta.isEmpty()) ? "-1" : StringUtils.join(StringsUtils.agregarComillaSimpleLista(this.codigosRespuesta), ',');
    }

    private List<Integer> canales;

    public String getCanalesCadena()
    {
        return (this.canales == null || this.canales.isEmpty()) ? "-1" : StringUtils.join(this.canales, ',');
    }

    private String numeroTarjeta;

    private String codigoSeguimiento;

    private String trace;
    private String nombreCompleto;

    private String modo;

    // Descripcion filtros
    private String descripcionRangoFechasProceso;

    private String descripcionRangoFechasTransaccion;

    private String descripcionInstitucion;

    private String descripcionEmpresa;

    private String descripcionCliente;

    private String descripcionRolTransaccion;

    private String descripcionCodigoProceso;

    private String descripcionCodigoRespuesta;

    private String descripcionCanal;

}