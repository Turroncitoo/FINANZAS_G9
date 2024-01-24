package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTransaccionConsolidada {
	
	private String numeroTarjeta;
	private String numeroCuenta;
	private String idCanal;
	private String idOrigenArchivo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioTxn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinTxn;
    private Integer codigoInstitucion;
}
