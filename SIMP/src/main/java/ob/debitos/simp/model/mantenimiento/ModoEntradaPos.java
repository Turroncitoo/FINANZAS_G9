package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoModoEntradaPos;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModoEntradaPos
{
    @CodigoModoEntradaPos(existe = true, groups = IActualizacion.class)
    @CodigoModoEntradaPos(existe = false, message = "{Existe.ModoEntradaPos.codigoModoEntradaPOS}", groups = IRegistro.class)
    private String codigoModoEntradaPOS;

    @NotNull(message = "{NotNull.ModoEntradaPos.descripcion}")
    @NotBlank(message = "{NotBlank.ModoEntradaPos.descripcion}")
    @Length(min = 3, max = 80, message = "{Length.ModoEntradaPos.descripcion}")
    private String descripcion;
}