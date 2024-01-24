package ob.debitos.simp.model.consulta.movimiento;

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
public class TxnsWebServices
{

    private Integer idInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descripcionEmpresa;

    private String idCliente;

    private String descripcionCliente;

    private String idMembresia;

    private String descripcionMembresia;
    
    private String idLogo;
   
    private String descripcionLogo;

    private String descripcionLogoBin;
    
    private String idBin;

    private String descripcionBin;

    private String messageType;

    private String operacion;

    private String idTransaccion;

    private Integer idPersona;

    private Integer tipoDocumento;

    private String descTipoDocumento;

    private String numeroDocumento;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String codigoSeguimiento;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;

    private String fechaTransaccionCadena;

    private String horaTransaccion;

    private String numeroTrace;

    private String idComercio;

    private String dirComercio;

    private String idTerminal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date localDate;

    private String localDateCadena;

    private String localTime;

    private String codigoSeguimientoOrigen;

    private String codigoSeguimientoDestino;

    private Integer exito;

    private String codigoRespuesta;

    private Integer monedaRecarga;

    private String descMonedaRecarga;

    private Double montoRecarga;

    private Integer monedaComisionExtra;

    private String descMonedaComisionExtra;

    private Double montoComisionExtra;

    private Integer indExtorno;

    private String traceExtorno;

    private String usuarioSolitudExtorno;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaSolicitudExtorno;

    private Integer solicitoExtorno;

    private String indidEmpresaExtorno;

    private String extornoEnTexto;

    private String solicitoExtornoEnTexto;

}
