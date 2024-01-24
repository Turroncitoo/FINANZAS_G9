package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoParametroFTPArchivo;
import ob.debitos.simp.validacion.CodigoParametroFTPDirectorio;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoParametroFTPDirectorio(existe = true, campoCodigoProceso = "codigoProceso", campoCodigoTipoOperacion = "tipoOperacion" )
public class ParametrosSFTPArchivo {
	@CodigoParametroFTPArchivo(existe = false, groups = IRegistro.class, message = "{Existe.ParametrosSFTPArchivo.codigoArchivo}")
	@CodigoParametroFTPArchivo(existe = true, groups = IActualizacion.class)
	private String codigoArchivo;
	
	private String codigoProceso;	
	private String tipoOperacion;
	
	@NotNull(message = "{NotNull.ParametrosSFTPArchivo.origenOriginal}")
	@Length(min = 3, max = 200, message = "{Length.ParametrosSFTPArchivo.origenOriginal}")
	@Pattern(regexp = Regex.RUTA, message = "{Pattern.ParametrosSFTPArchivo.origenOriginal}")
	private String origenOriginal;
	
	private String origen;
	
	@NotNull(message = "{NotNull.ParametrosSFTPArchivo.extensionOrigenOriginal}")
	@Length(min = 3, max = 20, message = "{Length.ParametrosSFTPArchivo.extensionOrigenOriginal}")
	@Pattern(regexp = Regex.NOMBRE_EXTENSION_ARCHIVO, message = "{Pattern.ParametrosSFTPArchivo.extensionOrigenOriginal}")
	private String extensionOrigenOriginal;
	
	private String extensionOrigen;
	
	@NotNull(message = "{NotNull.ParametrosSFTPArchivo.habilitado}")
	@Range(min = 0, max = 1, message = "{Range.ParametrosSFTPArchivo.habilitado}")
	private Integer habilitado;
	
	@NotNull(message = "{NotNull.ParametrosSFTPArchivo.validaHeader}")
	@Range(min = 0, max = 1, message = "{Range.ParametrosSFTPArchivo.validaHeader}")
	private Integer validaHeader;
	
	@NotNull(message = "{NotNull.ParametrosSFTPArchivo.numeroBytes}")
	@Range(min = 0, max = 9999999, message = "{Range.ParametrosSFTPArchivo.numeroBytes}")
	private Integer numeroBytes;
	
	@Range(min = -99, max = 99, message = "{Range.ParametrosSFTPArchivo.diasAumentoRebajoFechaProceso}")
    private int diasAumentoRebajoFechaProceso;
	
	private Integer cargaIncremental;
	
	@Length(min = 0, max = 200, message = "{Length.ParametrosSFTPArchivo.descripcion}")
	private String descripcion;
	
	private String descripcionProceso;
	private String descripcionTipoOperacion;
	
	
}
