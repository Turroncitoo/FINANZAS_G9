package ob.debitos.simp.model.ajuste;

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
public class TxnsAjuste
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaAfectacion;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private Integer rolTransaccion;

    private String descripcionRolTransaccion;

    private String membresia;

    private String descMembresia;

    private String claseServicio;

    private String descClaseServicio;

    private Integer origen;

    private String descOrigen;

    private Integer claseTransaccion;

    private String descClaseTransaccion;

    private Integer codigoTransaccion;

    private String descCodigoTransaccion;

    private String proceso;

    private String descProceso;

    private String codigoRespuesta;

    private String descCodigoRespuesta;

    private Integer canal;

    private String descCanal;

    private Integer tipoDocumento;

    private String descTipoDocumento;

    private String numeroDocumento;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private String secuenciaTransaccion;

    private Integer tipoMovimiento;

    private String descTipoMovimiento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;

    private String fechaTransaccionCadena;

    private String horaTransaccion;

    private Integer monedaCompensacion;

    private String descMonedaCompensacion;

    private Double valorAutorizacion;

    private Double valorCompensacion;

    private Double valorOIF;

    private Double valorDiferencia;

    private String registroContable;

    private String descRegistroContable;

    private String numeroVoucher;

    private String autorizacion;

    private String nombreAfiliado;

    private Integer contabiliza;

    private String descContabiliza;

    private String cuentaCargo;

    private String cuentaAbono;

    private String codigoAnalitico;

    private String atm;

}