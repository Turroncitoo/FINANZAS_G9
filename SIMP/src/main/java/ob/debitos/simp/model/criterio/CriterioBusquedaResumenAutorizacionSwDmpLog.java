package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaResumenAutorizacionSwDmpLog
{    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    private String descripcionRangoFechas;
    private Integer idInstitucion;
    private String descripcionInstitucion;
    private String idEmpresa;
    private String descripcionEmpresa;
    
    private String idCliente;
    private List<String> clientes;
    private String descripcionCliente;

    private Integer rolTransaccion;
    private List<Integer> rolesTransaccion;
    private String descripcionRolTransaccion;

    private String codigoProceso;
    private List<String> procesos;
    private String descripcionCodigoProceso;

    private Integer idCanal;
    private List<Integer> canales;
    private String descripcionIdCanal;
}