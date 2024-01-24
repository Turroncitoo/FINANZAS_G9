package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdCliente;
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdCliente(existe = true, groups = IActualizacion.class)
@IdCliente(existe = false, groups = IRegistro.class)
public class Cliente
{
    private String idCliente;

    @IdEmpresa(existe = true, groups = IActualizacion.class)
    private String idEmpresa;

    @NotNull(message = "{NotNull.Cliente.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.Cliente.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 40, message = "{Length.Cliente.descripcion}", groups = IBasico.class)
    private String descripcion;
    private String descripcionEmpresa;
}