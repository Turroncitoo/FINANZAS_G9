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
import ob.debitos.simp.utilitario.MultiTablaUtil;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoParametroFTPDirectorio;
import ob.debitos.simp.validacion.CodigoParametroFTPProceso;
import ob.debitos.simp.validacion.MultitabDet;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoParametroFTPDirectorio(existe = false, groups = IRegistro.class, 
campoCodigoProceso = "codigoProceso", campoCodigoTipoOperacion = "tipoOperacion", message = "{Existe.ParametrosSFTPDirectorio.codigoProceso}")
@CodigoParametroFTPDirectorio(existe = true, groups = IActualizacion.class, 
campoCodigoProceso = "codigoProceso", campoCodigoTipoOperacion = "tipoOperacion" )
public class ParametrosSFTPDirectorio {
	@CodigoParametroFTPProceso(existe = true)
	private String codigoProceso;
	
	@MultitabDet(existe = true, min = 1, max = 1, campoIdItem = "tipoOperacion", idTabla = MultiTablaUtil.TABLA_TIPO_OPERACION_SFTP, message = "{NoExiste.MultitabDet.tipoOperacion}")
	private String tipoOperacion;
	
	@NotNull(message = "{NotNull.ParametrosSFTPDirectorio.directorioOrigen}")
    @NotBlank(message = "{NotBlank.ParametrosSFTPDirectorio.directorioOrigen}")
    @Length(min = 3, max = 200, message = "{Length.ParametrosSFTPDirectorio.directorioOrigen}")
    @Pattern(regexp = Regex.RUTA, message = "{Pattern.ParametrosSFTPDirectorio.directorioOrigen}")
	private String directorioOrigen;
	
	@NotNull(message = "{NotNull.ParametrosSFTPDirectorio.directorioDestino}")
    @NotBlank(message = "{NotBlank.ParametrosSFTPDirectorio.directorioDestino}")
    @Length(min = 3, max = 200, message = "{Length.ParametrosSFTPDirectorio.directorioDestino}")
    @Pattern(regexp = Regex.RUTA, message = "{Pattern.ParametrosSFTPDirectorio.directorioDestino}")
	private String directorioDestino;
	
	@NotNull(message = "{NotNull.ParametrosSFTPDirectorio.borraFichero}")
	@Range(min = 0, max = 1, message = "{Range.ParametrosSFTPDirectorio.borraFichero}")
	private Integer borraFichero;
	
	private String descripcionProceso;
	private String descripcionTipoOperacion;

}
