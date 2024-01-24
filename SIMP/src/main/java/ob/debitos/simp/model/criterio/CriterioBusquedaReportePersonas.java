package ob.debitos.simp.model.criterio;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaReportePersonas {
	private Integer mesInicio;
	private Integer anioInicio;
	private Integer mesFin;
	private Integer anioFin;
}
