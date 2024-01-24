package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
public class CriterioBusquedaExtornosControversias
{
    @Length(min = 1, max = 20)
    private String numeroTarjeta;
    @Length(min = 1, max = 23)
    private String referenciaIntercambio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioProceso;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinProceso;
    private Integer codigoInstitucion;
    private String descripcionInstitucion;

    private String descripcionRangoFechas;

    private String idEmpresa;
    private String descripcionEmpresa;

    private String idCliente;
    private List<String> clientes;
    private String descripcionCliente;

    private String codigoRolTransaccion;
    private List<String> rolesTransaccion;
    private String descripcionRolTransaccion;

    private String codigoRespuesta;
    private List<String> codigosRespuesta;
    private String descripcionCodigoRespuesta;

}
