package ob.debitos.simp.model.tarjetas;

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
public class LoteDetalle
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    private Integer idInstitucion;
    private String descripcionInstitucion;
    private String idEmpresa;
    private String descripcionEmpresa;
    private String idCliente;
    private String descripcionCliente;
    private String idLogo;
    private String descLogo;
    private String idBin;
    private String codigoProducto;
    private String descProducto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRegistro;
    private String horaRegistro;
    private String tipoLote;
    private String descripcionTipoLote;
    private String estadoLote;
    private String descripcionEstadoLote;
    private Integer indActivado;
    private Integer indRecargado;
    private String tipoAfiliacion;
    private String descripcionTipoAfiliacion;
    private String tipoTarjetas;
    private String descripcionTipoTarjetas;
    private String idAfinidad;
    private String descripcionAfinidad;
    private String idCategoria;
    private String descripcionCategoria;
    private Integer idLotePadre;
    private Integer enviadoUBA;
    private String idLoteUBA;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaEmisionUBA;
    private Integer recibioRespuesta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRespuesta;
    private Integer idLote;
    private Integer registros;
    private Integer idDetalle;
    private String tipoMensaje;
    private String descTipoMensaje;
    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;
    private String numeroTrace;
    private String binAdquirente;
    private String binEmisor;
    private String codigoRespuesta;
    private String descRespuesta;
    private String messageType;
    private String descMessageType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaAfiliacion;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String titulo;
    private String direccion;
    private String direccion2;
    private String distrito;
    private String provincia;
    private String codigoPostal;
    private String telefono;
    private String categoriaTarjeta;
    private String descCategoriaTarjeta;
    private String sexo;
    private String descSexo;
    private String fechaNacimiento;
    private String estadoCivil;
    private String descEstadoCivil;
    private String codigoPlazaAfiliacion;
    private String tipoDocumento;
    private String descTipoDocumento;
    private String numeroDocumento;
    private String nombreEmpresa;
    private String nombreCortoThb;
    private String estadoTarjeta;
    private String descEstadoTarjeta;
    private String statusTarjeta;
    private Integer contadorPinErrado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaOrden;
    private String fechaExpiracion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaIngresoSw;
    private String horaIngreso;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaUltimActualizaAdmin;
    private String horaUltimActualizaAdmin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaUltimActualizaFinan;
    private String horaUltimActualizaFinan;
    private String codigoSeguimiento;
    private String rucEmpresa;
    private Integer indicadorDistribucion;
    private String descDistribucion;
    private Integer estadoRegistro;
    private Integer idPersona;
    private String numeroTarjetaDetalle;
    private String estadoTarjetaDetalle;
    private String descEstadoTarjetaDetalle;
    private String tipoTarjetaDetalle;
    private String descTipoTarjetaDetalle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaActivacionDetalle;
    private String horaActivacionDetalle;
    private String tipoBloqueoDetalle;
    private String descTipoBloqueoDetalle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaBloqueoDetalle;
    private String horaBloqueoDetalle;
    private Integer fisicaNacidaVirtualDetalle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaFisicaNacidaVirtualDetalle;
    private String codigoSeguimientoDetalle;
    private Integer tipoDocumentoDetalle;
    private String descTipoDocumentoDetalle;
    private String numeroDocumentoDetalle;
    
    private String  nombreMandatorio1;
    private Integer tipoDocumento1;
    private String  descTipoDocumento1;
    private String  numeroDocumento1;
    private String  telefonoMandatorio1;
    private String  nombreMandatorio2;
    private Integer tipoDocumento2;
    private String  descTipoDocumento2;
    private String  numeroDocumento2;
    private String  telefonoMandatorio2;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;
    
    private String  operacion;
    private String  descOperacion;
    private Integer moneda;
    private String  descMoneda;
    private Double monto;
    private Double montoRespuesta;

}
