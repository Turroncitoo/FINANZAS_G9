package ob.debitos.simp.model.proceso;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.MultiTablaUtil;
import ob.debitos.simp.validacion.CodigoGrupo;
import ob.debitos.simp.validacion.HoraProgramada;
import ob.debitos.simp.validacion.MultitabDet;
import ob.debitos.simp.validacion.OrdenEjecucion;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@HoraProgramada
@OrdenEjecucion
public class ProcesoAutomatico
{
    @CodigoGrupo(existe = true, groups = IActualizacion.class)
    @CodigoGrupo(existe = false, message = "{Existe.ProcesoAutomatico.codigoGrupo}", groups = IRegistro.class)
    private String codigoGrupo;

    @NotNull(message = "{NotNull.ProcesoAutomatico.descripcion}")
    @NotBlank(message = "{NotBlank.ProcesoAutomatico.descripcion}")
    @Length(min = 3, max = 100, message = "{Length.ProcesoAutomatico.descripcion}")
    private String descripcion;
    
    private String horaProgramada;
    private String horaReprogramada;
    private int ordenEjecucion;

    @NotNull(message = "{NotNull.ProcesoAutomatico.habilitado}")
    @Range(min = 0, max = 1, message = "{Range.ProcesoAutomatico.habilitado}")
    private Integer habilitado;

    @NotNull(message = "{NotNull.ProcesoAutomatico.estrictamenteSecuencial}")
    @Range(min = 0, max = 1, message = "{Range.ProcesoAutomatico.estrictamenteSecuencial}")
    private Integer estrictamenteSecuencial;

    @MultitabDet(existe = true, idTabla = MultiTablaUtil.TABLA_TIPO_PROCESO_AUTOMATICO, campoIdItem = "tipo")
    private String tipo;
    private String descripcionTipo;
}