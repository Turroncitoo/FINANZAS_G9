package ob.debitos.simp.model.tarifario;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdEsquemaTar;
import ob.debitos.simp.validacion.IdTipoTarifa;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoTarifa
{
    @IdTipoTarifa(existe = true, groups = IActualizacion.class)
    @IdTipoTarifa(existe = false, message = "{ExisteTipoTarifa.TipoTarifa.tipoTarifa}", groups = IRegistro.class)
    private int tipoTarifa;

    @NotNull(message = "{NotNull.TipoTarifa.aplicaBin}")
    @Range(min = 0, max = 1, message = "{Pattern.TipoTarifa.aplicaBin}")
    private int aplicaBin;

    @NotNull(message = "{NotNull.TipoTarifa.diferenteTran}")
    @Range(min = 0, max = 1, message = "{Range.TipoTarifa.diferenteTran}")
    private int diferenteTran;

    @IdEsquemaTar(existe = true, groups = ICampo.class)
    private int codigoEsquema;

    @NotNull(message = "{NotNull.TipoTarifa.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.TipoTarifa.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 50, message = "{Length.TipoTarifa.descripcion}", groups = IBasico.class)
    private String descripcion;
    private String descripcionEsquema;
}