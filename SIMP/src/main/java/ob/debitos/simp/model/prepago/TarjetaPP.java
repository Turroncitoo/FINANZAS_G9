package ob.debitos.simp.model.prepago;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaPP
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private Integer idInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descripcionEmpresa;

    private String idCliente;

    private String descripcionCliente;

    private String idBin;

    private String idLogo;
    
    private String descLogo;
    
    private String descLogoBin;

    private String codigoProducto;

    private String descProducto;

    private Integer idTarjeta;

    private String codigoSeguimiento;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaActivacion;

    private String fechaActivacionCadena;

    private String horaActivacion;

    private String estado;

    private String descripcionEstado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaBloqueo;

    private String fechaBloqueoCadena;

    private String horaBloqueo;

    private String tipoBloqueo;

    private String descripcionTipoBloqueo;

    private String poseeDuenio;

    private Integer idPersona;

    private Integer codigoUBA;

    private Integer tipoDocumento;

    private String descripcionTipoDocumento;

    private String numeroDocumento;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaOrden;

    private String tipoTarjeta;

    private String descripcionTipoTarjeta;

    private String idAfinidad;

    private String descripcionAfinidad;

    private Integer tarjetaFisicaNacidaDeVirtual;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTarjetaFisicaNacidaDeVirtual;

    private Integer habilitadaTarjetaFisicaNacidaDeVirtual;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaHabilitacionTarjetaFisicaNacidaVirtual;

    private String idCuenta;

    private String cuentaDefecto;

    private Integer monedaCuenta;

    private String descripcionMonedaCuenta;

    private String tipoCuenta;

    private String titularidad;

    private String cuentaLinea1EnAtmBcoPlaza;

    private String cuentaLinea2EnAtmTpoCodCta;

    private Integer permiteRealizarRetiros;

    private Integer permiteRealizarDeposito;

    private Integer permiteRealizarTransferencia;

    private Integer permiteRecibirTransferencia;

    private Integer permiteConsulta;

    private Integer idLote;

    private String traceLote;

    private String claveId;

    private String fechaVencimiento;

    private String numeroTarjetaTruncado;

    //atributos de SIMP_PRE
    private String numTarjeta;

    private String codSeguimiento;

    private String codCuenta;

    private PersonaPP persona;

}
