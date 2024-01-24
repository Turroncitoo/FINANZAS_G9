package ob.debitos.simp.model.consulta.movimiento;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;
import ob.debitos.simp.model.paginacion.ItemPagina;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TxnsCompensacion extends ItemPagina
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private String descripcionLogoCompleto;

    private String idLogo;

    private String descLogo;

    private String codigoProducto;

    private String descProducto;

    private String idBin;

    private String descBin;

    private String idSubBin;

    private String descSubBin;

    private String codigoMembresia;

    private String descMembresia;

    private String codigoClaseServicio;

    private String descClaseServicio;

    private Integer codigoOrigen;

    private String descOrigen;

    private Integer idClaseTransaccion;

    private String descripcionClaseTransaccion;

    private Integer idCodigoTransaccion;

    private String descripcionCodigoTransaccion;

    private Integer rolTransaccion;

    private String descripcionRol;

    private Integer codigoInstitucionEmisor;

    private String descripcionInstitucionEmisor;

    private Integer codigoInstitucionReceptor;

    private String descripcionInstitucionReceptor;

    private String codigoRespuestaSwitch;

    private String descripcionCodigoRespuesta;

    private Integer idCanal;

    private String descripcionCanal;

    private Integer tipoDocumento;

    private String descTipoDocumento;

    private String numeroDocumento;

    private String secuenciaTransaccion;

    private String codigoSeguimiento;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private String numeroCuenta;

    private String estadoTarjeta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;

    private String fechaTransaccionCadena;

    private String horaTransaccion;

    private String numeroVoucher;

    private String codigoAutorizacion;

    private Integer monedaTransaccion;

    private String descMonedaTransaccion;

    private Double valorTransaccion;

    private Integer monedaAutorizacion;

    private String descMonedaAutorizacion;

    private Double valorAutorizacion;

    private Integer monedaCompensacion;

    private String descMonedaCompensacion;

    private Double valorCompensacion;

    private Integer monedaCargadaThb;

    private String descripcionCodigoMonedaCargadaThb;

    private Double valorCargadoThb;

    private Double tipoDeCambio;

    private String nombreAfiliado;

    private String numeroDocumentoCompensacion;

    private Integer compensaFondos;

    private Integer compensaComisiones;

}