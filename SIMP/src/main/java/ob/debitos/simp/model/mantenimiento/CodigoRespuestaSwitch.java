package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoRptaSwitch;
import ob.debitos.simp.validacion.MultitabDet;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodigoRespuestaSwitch
{
    @CodigoRptaSwitch(existe = true, groups = IActualizacion.class)
    @CodigoRptaSwitch(existe = false, message = "{Existe.CodigoRptaSwitch.codigoRespuestaSwitch}", groups = IRegistro.class)
    private String codigoRespuestaSwitch;

    @NotNull(message = "{NotNull.CodigoRptaSwitch.descripcion}")
    @NotBlank(message = "{NotBlank.CodigoRptaSwitch.descripcion}")
    @Length(min = 3, max = 80, message = "{Length.CodigoRptaSwitch.descripcion}")
    private String descripcion;

    @MultitabDet(existe = true, min = 1, max = 1, campoIdItem = "tipoRespuesta", idTabla = 8, message = "{NoExiste.MultitabDet.tipoRespuesta}")
    private String tipoRespuesta;

    @NotNull(message = "{NotNull.CodigoRptaSwitch.comisionTipoC}")
    @Range(min = 0, max = 1, message = "{Range.CodigoRptaSwitch.comisionTipoC}")
    private Integer comisionTipoC;
    private String descripcionTipoRespuesta;
}