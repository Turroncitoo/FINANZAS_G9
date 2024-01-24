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
public class CriterioBusquedaTransaccionPendiente
{
	private String numeroTarjeta;
    private Integer idCanal;
    private Integer codigoProcesoSwitch;
    private String trace;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioTransaccion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinTransaccion;
    
    private Integer idInstitucion;
    private String descripcionInstitucion;
}
