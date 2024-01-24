package ob.debitos.simp.model.proceso;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReporteContableSwDmpLg {
	 private Date fechaTransaccion;
	 private Date fechaProceso;
	 private String idCliente;
	 private String descripcionCliente;
	 private Integer idPersona;
	 private Integer tipoDocumento;	
	 private String numeroDocumento;
	 private String nombres;
	 private String apellidoPaterno;
	 private String apellidoMaterno;
	 private String idBIN;
	 private String idSubBIN;
	 private String cardAcceptorLocation;
	 private Integer transaccion ;
	 private String descripcionTransaccion;
	 private Integer monedaTransaccion;
	 private Double valorTransaccion;  
}
