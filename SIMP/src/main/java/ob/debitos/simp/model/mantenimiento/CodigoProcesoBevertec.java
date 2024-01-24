package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoCanalEmisor;
import ob.debitos.simp.validacion.CodigoProcBevertec;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoProcBevertec(existe = true, groups = IActualizacion.class)
@CodigoProcBevertec(existe = false, groups = IRegistro.class)
public class CodigoProcesoBevertec
{
    private String tipoTransaccion;

    @CodigoCanalEmisor(existe = true, groups = IBasico.class)
    private String codigoCanalEmisor;

    @NotNull(message = "{NotNull.CodigoProcBevertec.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.CodigoProcBevertec.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 50, message = "{Length.CodigoProcBevertec.descripcion}", groups = IBasico.class)
    private String descripcion;
    private String descripcionCanalEmisor;
}