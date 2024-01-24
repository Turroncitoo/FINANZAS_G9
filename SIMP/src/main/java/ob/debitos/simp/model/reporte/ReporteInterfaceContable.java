package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteInterfaceContable
{
    private Integer idInstitucion;
	private String descInstitucion;
    private String idEmpresa;
    private String descripcionEmpresa;
    private String idCliente;
    private String descripcionCliente;
    private String claseServicio;
    private String descClaseServicio;
    private Integer origen;
	private String descOrigen;
    private String idThb;
    private String membresia;
    private String descMembresia;
    private Integer codigoTransaccion;
    private String descripcionCodigoTransaccion;
    private Integer claseTransaccion;
    private String descripcionClaseTransaccion;
    private String operacion;
    private Integer institucionEmisora;
    private String descripcionInstitucionEmisora;
    private Integer institucionReceptora;
    private String descripcionInstitucionReceptora;
    private Double tipoCambioSBS;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;
    private String fechaTransaccionCadena;
    private String horaTransaccion;
    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;
    private Integer monedaTransaccion;
    private String descMonedaTransaccion;
    private Double montoTransaccion;
    private Integer monedaCompensacion;
    private String descMonedaCompensacion;
    private Double montoCompensacion;
    private String cuentaCargo;
    private String descripcionCuentaCargo;
    private String cuentaAbono;
    private String descripcionCuentaAbono;
}
