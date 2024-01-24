package ob.debitos.simp.model.consulta.movimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;
import ob.debitos.simp.model.paginacion.ItemPagina;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsSwDmpLog extends ItemPagina
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private String idLogo;

    private String descripcionLogo;

    private String codigoProducto;

    private String descProducto;

    private String idBin;

    private String descripcionBin;

    private String idSubBin;

    private String descripcionSubBin;

    private Integer rolTransaccion;

    private String descripcionRol;

    private String idProceso;

    private String descripcionProceso;

    private String codigoRespuesta;

    private String descripcionRespuesta;

    private Integer idCanal;

    private String descripcionCanal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransmision;

    private String fechaTransmisionCadena;

    private String horaTransmision;

    private String tipoMensaje;

    private Integer tipoDocumento;

    private String descTipoDocumento;

    private String numeroDocumento;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private String codigoSeguimiento;

    private String identificadorCuenta;

    private String estadoTarjeta;

    private Integer moneda;

    private String descMoneda;

    private Double importe;

    private String giroNegocio;

    private String idMembresia;

    private String descripcionMembresia;

    private String descripcionGiroNegocio;

    private String trace;

    private String codigoAutorizacion;

    private String descripcionAdquirente;

    private String institucionAdquirente;

    private String descripcionInstitucionAdquirente;

    private String institucionSolicitante;

    private String descripcionInstitucionSolicitante;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaSwitch;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccionLocal;

    private String fechaTransaccionLocalCadena;

    private String horaTransaccionLocal;

    private String panEntryMode;

    private String descripcionPANEntryMode;

    private String pinEntryCapability;

    private String descripcionPINEntryCapability;

    private String cuentaCargo;

    private String cuentaAbono;

    private String codigoAnalitico;

    private String dMembresia;

    private String descMembresia;

    private String idClaseServicio;

    private String descClaseServicio;

    private Integer idOrigen;

    private String descOrigen;

    private Integer idClaseTransaccion;

    private String descClaseTransaccion;

    private Integer idCodigoTransaccion;

    private String descCodigoTransaccion;

    private String indStoreForward;

    private String cuentaFrom;

    private String cuentaTo;

    private Integer settlementCurrency;

    private Double settlementAmount;

    private Double cardIssuerAmount;

    private Double valorSurcharge;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date expiryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date setllementDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date conversionDate;

    private String networkIdentifier;

    private Double settAmountTransactionFee;

    private Double settAmountProcessingFee;

    private String track2Data;

    private String referenceNumber;

    private String cardAcceptorTermId;

    private Integer billingCurrency;

    private String originalMessageType;

    private String originalTraceNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date originalDate;

    private String originalTime;

    private String originalAdquiringInst;

    private String originalForwardingInst;

    private Double replaTransactionAmount;

    private Double replaSettlementAmount;

    private Double replaTransactionFee;

    private Double replaSettlementFee;

    private String cardCategory;

    private String tierII;

    private String cardAcceptorId;

    private Integer conciliaAutorizacion;

    private Integer conciliaLogContable;

    private Integer contabFondos;

    private Integer idATM;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaConciliaLog;

    private String descripcionGiroNegocioUnido;

    // Extras
    @NoIdentificable(campo = "numeroCuenta")
    private String numeroCuenta;

    private String numeroRastreo;

    private Integer transaccionMonetaria;

    private String cantidadTransaccion;

    private String ubicacionTarjeta;

    private String numeroDocumentoSwDmpLog;

    private String codigoSeguimientoTarjeta;

    private String descripcionLogoCompleto;
}