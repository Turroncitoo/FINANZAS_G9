package ob.debitos.simp.model.prepago.criterio;

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
public class CriterioBusquedaFiltroPrepago
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;

   // @Pattern(regexp = Regex.VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO, message = "{Pattern.CriterioBusquedaTransaccionAutorizada.numeroTarjeta}")
   // @Length(max = 20, message = "{Length.CriterioBusquedaTransaccionAutorizada.numeroTarjeta}")
    private String numeroTarjeta;
       
    private String tarjetaAnterior;
    
    private String tarjetaNueva;
    
    private String descripcionRangoFechas;
    private String tipoBusqueda;
    
}
