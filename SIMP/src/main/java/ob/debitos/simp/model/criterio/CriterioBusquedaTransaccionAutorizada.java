package ob.debitos.simp.model.criterio;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import ob.debitos.simp.utilitario.Regex;

@Data
public class CriterioBusquedaTransaccionAutorizada
{	
	@Pattern(regexp = Regex.VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO, message = "{Pattern.CriterioBusquedaTransaccionAutorizada.numeroTarjeta}")
	@Length(max = 20, message = "{Length.CriterioBusquedaTransaccionAutorizada.numeroTarjeta}")
    private String numeroTarjeta;
	
	@Pattern(regexp = Regex.VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO, message = "{Pattern.CriterioBusquedaTransaccionAutorizada.numeroTrace}")
    @Length(max = 15, message = "{Length.CriterioBusquedaTransaccionAutorizada.numeroTrace}")
    private String trace;
	
    private Integer idCanal;
    private Integer codigoProcesoSwitch;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fechaInicioTransaccion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fechaFinTransaccion;
}