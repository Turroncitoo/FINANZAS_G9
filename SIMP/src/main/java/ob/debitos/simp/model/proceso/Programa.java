package ob.debitos.simp.model.proceso;

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
import ob.debitos.simp.validacion.CodigoGrupo;
import ob.debitos.simp.validacion.IdPrograma;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdPrograma(existe = true, groups = IActualizacion.class)
@IdPrograma(existe = false, message = "{NoExiste.Programa.codigoPrograma}", groups = IRegistro.class)
public class Programa
{
    @NotNull(message = "{NotNull.Programa.codigoPrograma}")
    @NotBlank(message = "{NotBlank.Programa.codigoPrograma}")
    @Length(max = 4, message = "{Length.Programa.codigoPrograma}")
    private String codigoPrograma;

    @CodigoGrupo(existe = true)
    private String codigoGrupo;
    private String descripcionGrupo;
    
    @NotNull(message = "{NotNull.Programa.codigoSubModulo}")
    @NotBlank(message = "{NotBlank.Programa.codigoSubModulo}")
    @Length(max = 4, message = "{Length.Programa.descripcion}")
    private String codigoSubModulo;
    private String descripcionSubModulo;

    @NotNull(message = "{NotNull.Programa.procedimiento}")
    @Pattern(regexp = Regex.VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO, message = "{Pattern.Programa.procedimiento}")
    @Length(max = 100, message = "{Length.Programa.procedimiento}")
    private String procedimiento;

    @NotNull(message = "{NotNull.Programa.descripcion}")
    @NotBlank(message = "{NotBlank.Programa.descripcion}")
    @Length(min = 3, max = 50, message = "{Length.Programa.descripcion}")
    private String descripcion;

    @NotNull(message = "{NotNull.Programa.archivo}")
    @Pattern(regexp = Regex.VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO, message = "{Pattern.Programa.archivo}")
    @Length(max = 200, message = "{Length.Programa.archivo}")
    private String archivo;

    @NotNull(message = "{NotNull.Programa.ordenEjecucion}")
    @Range(min = 1, max = 99, message = "{Pattern.Programa.ordenEjecucion}")
    private Integer ordenEjecucion;

    @NotNull(message = "{NotNull.Programa.periodoEjecucion}")
    @NotBlank(message = "{NotBlank.Programa.periodoEjecucion}")
    @Length(max = 1, message = "{Length.Programa.periodoEjecucion}")
    private String periodoEjecucion;

    @NotNull(message = "{NotNull.Programa.procesaSabado}")
    @Range(min = 0, max = 1, message = "{Pattern.Programa.procesaSabado}")
    private Integer procesaSabado;

    @Range(min = 1, max = 99999, message = "{Digits.Programa.longitud}")
    private Integer longitud;
    
    private String urlSistema;
    private boolean cargaMultiple;
    private boolean ejecucionOpcional;
    private Integer ordenEjecucionGrupo;
    private boolean ejecucionDetallada;
    private boolean ejecucionObligatoria;
    private boolean ejecucionPorInstitucion;

}