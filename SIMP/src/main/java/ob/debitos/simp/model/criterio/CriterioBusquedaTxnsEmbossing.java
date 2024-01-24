package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriterioBusquedaTxnsEmbossing {
	String numeroTarjeta;
	String numeroDocumento;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioProceso;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinProceso;
}
