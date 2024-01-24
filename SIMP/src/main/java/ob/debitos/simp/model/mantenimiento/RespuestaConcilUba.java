package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdRptaConcilUba;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaConcilUba
{
    @IdRptaConcilUba(existe = true, groups = IActualizacion.class)
    @IdRptaConcilUba(existe = false, message = "{Existe.RptaConcilUba.idRespuestaConcilUba}", groups = IRegistro.class)
    private String idRespuestaConcilUba;

    @NotNull(message = "{NotNull.RptaConcilUba.descripcion}")
    @NotBlank(message = "{NotBlank.RptaConcilUba.descripcion}")
    @Length(min = 3, max = 40, message = "{Length.RptaConcilUba.descripcion}")
    private String descripcion;
}