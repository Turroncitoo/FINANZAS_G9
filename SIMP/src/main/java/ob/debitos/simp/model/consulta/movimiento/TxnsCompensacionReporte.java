package ob.debitos.simp.model.consulta.movimiento;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

/**
 * Representa el reporte de las transacciones informadas por UNIBANCA en el
 * proceso de compensación en el archivo de Log Contable.
 * 
 * @author Robert Vega
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsCompensacionReporte
{
    private String secuenciaTransaccion;
    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;
    private Integer rolTransaccion;
    private String descripcionRolTransaccion;
    private Integer idCanal;
    private String descripcionCanal;
    private String numeroCuenta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;
    private String horaTransaccion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaAfectacion;
    private String codigoAutorizacion;
    private String numeroVoucher;
    private Integer codigoClaseTxn;
    private String descripcionCodigoClaseTransaccion;
    private Integer codigoTxn;
    private String descripcionCodigoTransaccion;
    private Integer codigoRespuestaSwitch;
    private String descripcionRespuestaSwitch;
    private Integer codigoMonedaCompensacion;
    private String descripcionCodigoMonedaCompensacion;
    private double valorCompensacion;
    private Integer codigoMonedaAutorizacion;
    private String descripcionCodigoMonedaAutorizacion;
    private double valorAutorizacion;
    private String nombreAfiliado;
    private String numeroDocumentoCompensacion;
    private String idEstadoTarjeta;
    private String descripcionEstadoTarjeta;
    private String tipoDocumento;
    private String numeroDocumento;
    private String apellidos;
    private String nombres;
    private String idClientePersona;
    private String codigoMembresia;
    private String descripcionMembresia;
    private String codigoClaseServicio;
    private String descripcionClaseServicio;
    private Integer codigoOrigen;
    private String descripcionOrigen;
    private Integer codigoInstitucionEmisor;
    private String descripcionInstitucionEmisor;
    private Integer codigoInstitucionReceptor;
    private String descripcionInstitucionReceptor;
    private String codigoBIN;
    private String descripcionBIN;
    private String codigoSeguimientoTarjeta;
    private String codigoSubBIN;
    private String descripcionSubBIN;
    private String codigoGiroNegocio;
    private String descripcionGiroNegocio;
    private Integer idATM;
    private String descripcionATM;
    private String codigoEstablecimiento;
    private String referenciaIntercambio;
    private String ciudadAfiliado;
    private String paisAfiliado;
    private String indicadorCompensacion;
    private int fondoCargo;
    private int fondoAbono;
    private String indicadorMonedaInternacional;
    private int numeroCuotas;
    private double comisionCuotaAccesoAtm;
    private String idEmpresa;
    private String descripcionEmpresa;
    private String idCliente;
    private String descripcionCliente;
    private String cuentaCargo;
    private String cuentaAbono;
    private String descripcionMetodoIdThb;
    private String descripcionIndCorreoTelefono;
    private String descripcionModoEntradaPos;
    private String descripcionTerminalPos;
    private String descripcionAutorizaSTIP;
    private String descripcionTxnQuasiCash;
    private double comision1;
    private double comision2;
    private double comision3;
    private double comision4;
    private double comision5;
    private double comision6;
    private double comision7;
    private double comision8;
    private double comision9;
    private double comision10;
    private double comision11;
    private double comision12;
    private double comision13;
    private double comision14;
    private double totalComisiones;
    private String monedaAutorizacion;
    private String tipoDeCambio;
    private String monedaCargadaThb;
    private String descripcionCodigoMonedaCargadaThb;
    private double valorCargadoThb;
    private String compensaFondos;
    private String compensaComisiones;
}