package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaResumenLogContableEmisor
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioProceso;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinProceso;
    private String descripcionFechas;
    private Integer idInstitucion;
    private String descripcionInstitucion;
    private String idEmpresa;
    private String descripcionEmpresa;
    private String idCliente;
    private List<String> clientes;
    private String descripcionCliente;
    private Integer rolTransaccion;
    private String descripcionRolTransaccion;
    private String codigoMembresia;
    private String codigoMembresiaCadena;
    private String descripcionMembresia;
    private String codigoClaseServicio;
    private List<String> servicios;
    private String descripcionClaseServicio;
    private Integer codigoOrigen;
    private List<Integer> origenes;
    private String descripcionOrigen;
    private Integer claseTransaccion;
    private String descripcionClaseTransaccion;
    private Integer codigoTransaccion;
    private List<Integer> codigosTransaccion;
    private String descripcionCodigoTransaccion;
    private Integer idCanal;
    private List<Integer> canales;
    private String descripcionCanal;

    private int codigoMoneda;
    private String descripcionMoneda;
}