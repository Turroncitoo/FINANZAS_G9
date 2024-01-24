package ob.debitos.simp.model.consulta.movimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsCompensacionDetalle
{

    private String descripcionMembresia;

    private String descripcionClaseServicio;

    private String descripcionOrigen;

    private String descripcionClaseTransaccion;

    private String descripcionCodigoTransaccion;

    private String descripcionEmisor;

    private String descripcionReceptor;

    private String descripcionLogo;

    private String descripcionProducto;

    private String descripcionFechaTransaccion;

    private String descripcionHoraTransaccion;

    private String descripcionFechaProceso;

    private String descripcionFechaAfectacion;

    private String descripcionMonedaTransaccion;

    private String descripcionValorTransaccion;

    private String descripcionMonedaCompensacion;

    private String descripcionValorCompensacion;

    @NoIdentificable(campo = "descripcionNumeroTarjeta")
    private String descripcionNumeroTarjeta;

    private String descripcionNumeroCuenta;

    private String descripcionTrace;

    private String descripcionAutorizacion;

    private String descripcionCodigoTerminal;

    private String descripcionCodigoEstablecimiento;

    private String descripcionNombreAfiliado;

    private String descripcionPaisAfiliado;

    private String descripcionCiudadAfiliado;

    private String descripcionReferenciaIntercambio;

    private String descripcionPorcentajeVisanet;

    private String descripcionAplicaCobroTipoC;

    private String descripcionPaisReceptor;

    private String descripcionRegionReceptor;

    private String descripcionTipoProducto;

    private String descripcionTipoTarjeta;

    private String descripcionPagoRecurrente;

    private String descripcionMonedaInternacional;

    private String descripcionIndCompensacion;

    private String descripcionRespuesta;

    private String descripcionCodigoProceso;

    private String descripcionCanal;

    private String descripcionMetodoIdThb;

    private String descripcionIndCorreoTelefono;

    private String descripcionGlosaRegularizacion;

    private String descripcionGiroNegocio;

    private String descripcionModoEntradaPos;

    private String descripcionTerminalPos;

    private String descripcionTipoCuentaAtm;

    private String descripcionFondosCargo;

    private String descripcionFondosAbono;

    private String descripcionNivelCompensacion;

    private String descripcionValorCashBack;

    private String descripcionConciliacionAutorizacion;

    private String descripcionSwDmpLog;

    private String descripcionCuentaCargo;

    private String descripcionCuentaAbono;

    private String descripcionCodigoAnalitico;

    private String descripcionNumeroDocumentoCompensacion;

    // Nuevos Campos para Datos 2
    private String descripcionIndicadorRegularizacion;

    private String descripcionCodigoReclamo;

    private String descripcionSecuenciaOriginal;

    private String descripcionValorOriginal;

    // Nuevos Campos para Datos 9
    private String descripcionCodigoPagoServicios;

    private String descripcionComisionPagoServicios;

    private String descripcionComisionIntercambio;

    private String descripcionIndicadorProgramaComision;

    private Integer descripcionCantidadInstituciones;

    // Campos para la nueva seccion: Miscelaneos
    private String descripcionCodigoEventoMarcaInternacional;

    private String descripcionDistribucionCobroMarcaInternacional;

    private String descripcionNumeroFacturaMarca;

    private Double descripcionTarifaMarcaInternacional;

    private Integer descripcionTotalUnidades;

    private String descripcionIndicadorDistribucionCobro;

    private Integer descripcionIndicadorUnidades;

    private Double descripcionValorFacturaMarcaInternacional;

    private String descripcionSecuenciaRegistroFacturaMarca;

    // Campos para la nueva seccion: Facturacion
    private String descripcionCodigoArticulo;

    private String descripcionSecuenciaDocumento;

    // Datos del Cliente
    private String descripcionTipoDocumento;

    private String descripcionNumeroDocumento;

    private String descripcionNombreCliente;

    private String descripcionApellidoCliente;

}
