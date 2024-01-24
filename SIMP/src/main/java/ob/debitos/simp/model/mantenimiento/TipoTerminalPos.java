package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoTipoTerminalPos;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoTerminalPos
{
    @CodigoTipoTerminalPos(existe = true, groups = IActualizacion.class)
    @CodigoTipoTerminalPos(existe = false, message = "{Existe.TipoTerminalPos.codigoTipoTerminalPOS}", groups = IRegistro.class)
    private String codigoTipoTerminalPOS;

    @NotNull(message = "{NotNull.TipoTerminalPos.descripcion}")
    @NotBlank(message = "{NotBlank.TipoTerminalPos.descripcion}")
    @Length(min = 3, max = 60, message = "{Length.TipoTerminalPos.descripcion}")
    private String descripcion;
}