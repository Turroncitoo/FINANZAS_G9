package ob.debitos.simp.model.proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoContableFondosPrepago {
	   private String idEmpresa;
	   private Integer origenInformacion;
	   private Integer secuenciaTransaccion;	
	   private String idCliente;
	   private String tipoDocumento;
	   private String numeroDocumento;
	   private String nombres;
	   private String apellidoPaterno;
	   private String apellidoMaterno;
	   private String idMembresia;
	   private String idClaseServicio;
	   private String idBIN;
	   private String idSubBIN;
	   private String numeroTarjeta;
	   private String codigoSeguimiento;
	   private String fechaTransaccion;
	   private String fechaProceso;
	   private String fechaAfectacion;
	   private String numeroVoucher;
	   private Integer transaccion;
	   private String nombreAfiliado;
	   private Integer monedaTransaccion;	
	   private String valorTransaccion;
	   private Integer monedaCompensacion;
	   private String valorCompensacion;
	   private Integer monedaCargoTHB;
	   private String valorCargoTHB;
	   private String tipoCambioTxn;
	   private String cuentaCargo;
	   private String cuentaAbono;
	   private String codigoAnalitico;   
	   private String numeroOperacion;
	   private String txnPrincipal;
}
