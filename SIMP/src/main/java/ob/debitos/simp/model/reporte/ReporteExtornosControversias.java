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
public class ReporteExtornosControversias 
{
	private Integer codigoInstitucion;
	private String descInstitucion;
	private String idEmpresa;
    private String descEmpresa;
	private String idCliente;
    private String descCliente;
	private Integer secuenciaTransaccion;
	private String codigoRolTransaccion;
	private String descripcionRol;
	private String codigoMembresia;
	private String descripcionMembresia;
	private String codigoClaseServicio;
	private String descripcionServicio;
	private Integer codigoOrigen;
	private String descripcionOrigen;
	private Integer idClaseTransaccion;
	private String descripcionClaseTransaccion;
	private Integer idCodigoTransaccion;
    private String descripcionCodigoTransaccion;
	private Integer codigoInstitucionEmisora;
	private String descripcionEmisor;
	private Integer codigoInstitucionReceptora;
	private String descripcionReceptor;
	private Integer codigoMoneda;
	private String descripcionMoneda;
	private double valorCompensacion;
	@NoIdentificable(campo = "numeroTarjeta")
	private String numeroTarjeta;
	private String numeroCuenta;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaTransaccion;
	private String fechaTransaccionCadena;
	private String horaTransaccion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaProceso;
	private String referenciaIntercambio;
	private String nombreAfiliado;
	private String ciudadAfiliado;
	private Integer fondosCargo;
	private String descFondosCargo;
	private Integer fondosAbono;
	private String descFondosAbono;
	private String codigoRespuesta;
	private String descripcionRespuesta;
	private Integer contadorFondos;
	private String descripcionEstadoContable;
	private String cuentaCargo;
	private String cuentaAbono;
}
