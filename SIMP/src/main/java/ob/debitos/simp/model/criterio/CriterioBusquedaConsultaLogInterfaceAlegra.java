package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Carlos Mirano
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaConsultaLogInterfaceAlegra
{
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaProcesoInicio;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaProcesoFin;
	
	private String rangoFechaProceso;
}
