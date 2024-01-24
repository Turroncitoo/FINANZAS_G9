package ob.debitos.simp.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaGeneracionTarjetas {
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaInicial;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaFinal;

    private List<String> idsMembresias;
    private List<String> idsAfinidades;
    private List<String> idsBines;
    private String descripcionFecha;
}
