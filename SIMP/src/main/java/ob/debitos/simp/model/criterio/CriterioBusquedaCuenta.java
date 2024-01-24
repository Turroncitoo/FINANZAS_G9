package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaCuenta {
	
	@Length(max = 30, message = "{Length.CriterioBusquedaCuenta.numeroCuenta}")
	private String numeroCuenta;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicioAlta;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFinAlta;
	private String idBin;
	private String idSubBin;
	
	
}
