package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.utilitario.StringsUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTransaccionCompensacion
{
    private String modo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProcesoInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProcesoFin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccionInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccionFin;

    private Integer codigoInstitucion;

    private String idEmpresa;

    private List<String> clientes;

    public String getClientesCadena()
    {
        return (this.clientes == null || this.clientes.isEmpty()) ? "-1"
                : StringUtils.join(
                        StringsUtils.agregarComillaSimpleLista(this.clientes),
                        ',');
    }

    private List<Integer> rolesTransaccion;

    public String getRolesTransaccionCadena()
    {
        return (this.rolesTransaccion == null
                || this.rolesTransaccion.isEmpty()) ? "-1"
                        : StringUtils.join(this.rolesTransaccion, ',');
    }

    private String codigoMembresia;

    private List<String> servicios;

    public String getServiciosCadena()
    {
        return (this.servicios == null || this.servicios.isEmpty()) ? "-1"
                : StringUtils.join(
                        StringsUtils.agregarComillaSimpleLista(this.servicios),
                        ',');
    }

    private List<Integer> origenes;

    public String getOrigenesCadena()
    {
        return (this.origenes == null || this.origenes.isEmpty()) ? "-1"
                : StringUtils.join(this.origenes, ',');
    }

    private Integer idClaseTransaccion;

    private List<Integer> codigosTransaccion;

    public String getCodigosTransaccionCadena()
    {
        return (this.codigosTransaccion == null
                || this.codigosTransaccion.isEmpty()) ? "-1"
                        : StringUtils.join(this.codigosTransaccion, ',');
    }

    private String idLogo;

    private List<String> productos;

    public String getProductosCadena()
    {
        return (this.productos == null || this.productos.isEmpty()) ? "-1"
                : StringUtils.join(
                        StringsUtils.agregarComillaSimpleLista(this.productos),
                        ',');
    }

    private List<Integer> institucionesEmisor;

    public String getInstitucionesEmisorCadena()
    {
        return (this.institucionesEmisor == null
                || this.institucionesEmisor.isEmpty()) ? "-1"
                        : StringUtils.join(this.institucionesEmisor, ',');
    }

    private List<Integer> institucionesReceptor;

    public String getInstitucionesReceptorCadena()
    {
        return (this.institucionesReceptor == null
                || this.institucionesReceptor.isEmpty()) ? "-1"
                        : StringUtils.join(this.institucionesReceptor, ',');
    }

    private List<String> codigosRespuesta;

    public String getCodigosRespuestaCadena()
    {
        return (this.codigosRespuesta == null
                || this.codigosRespuesta.isEmpty())
                        ? "-1"
                        : StringUtils
                                .join(StringsUtils.agregarComillaSimpleLista(
                                        this.codigosRespuesta), ',');
    }

    private List<Integer> canales;

    public String getCanalesCadena()
    {
        return (this.canales == null || this.canales.isEmpty()) ? "-1"
                : StringUtils.join(this.canales, ',');
    }

    private Integer monedaTransaccion;

    private String numeroTarjeta;

    private String numeroVoucher;

    private String codigoAutorizacion;

    // Descripcion filtros
    private String descripcionRangoFechasProceso;

    private String descripcionRangoFechasTransaccion;

    private String descripcionInstitucion;

    private String descripcionEmpresa;

    private String descripcionCliente;

    private String descripcionRolTransaccion;

    private String descripcionMembresia;

    private String descripcionServicio;

    private String descripcionOrigen;

    private String descripcionClaseTransaccion;

    private String descripcionCodigoTransaccion;

    private String descripcionLogo;

    private String descripcionProducto;

    private String descripcionInstitucionEmisor;

    private String descripcionInstitucionReceptor;

    private String descripcionCodigoRespuesta;

    private String descripcionCanal;

    private String descripcionMonedaTransaccion;
}