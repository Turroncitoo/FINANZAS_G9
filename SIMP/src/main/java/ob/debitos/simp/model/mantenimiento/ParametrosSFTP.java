package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.Regex;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosSFTP {
	@NotNull(message = "{NotNull.ParametrosSFTP.host}")
    @NotBlank(message = "{NotBlank.ParametrosSFTP.host}")
	@Length(min = 3, max = 30, message = "{Length.ParametrosSFTP.host}")
	@Pattern(regexp = Regex.DESCRIPCION_SIN_ESPACIOS, message = "{Pattern.ParametrosSFTP.host}")
	private String host;
	
	@NotNull(message = "{NotNull.ParametrosSFTP.usuario}")
    @NotBlank(message = "{NotBlank.ParametrosSFTP.usuario}")
	@Length(min = 3, max = 30, message = "{Length.ParametrosSFTP.usuario}")
	@Pattern(regexp = Regex.DESCRIPCION_SIN_ESPACIOS, message = "{Pattern.ParametrosSFTP.usuario}")
	private String usuario;
	
	@Length(min = 0, max = 30, message = "{Length.ParametrosSFTP.contrasenia}")
	private String contrasenia;
	
	@NotNull(message = "{NotNull.ParametrosSFTP.puerto}")
	@Range(min = 1, max = 65536, message= "{Range.ParametrosSFTP.puerto}")
	private Integer puerto;	
	
	private String contraseniaParaTest;
}
