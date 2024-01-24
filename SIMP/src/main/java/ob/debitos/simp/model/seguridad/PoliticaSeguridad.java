package ob.debitos.simp.model.seguridad;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliticaSeguridad
{
    @NotNull(message = "{NotNull.PoliticaSeguridad.numeroMaximoIntentos}")
    @Range(min = 1, max = 99, message = "{Length.Politica.numeroMaximoIntentos}")
    private Integer numeroMaximoIntentos;

    @NotNull(message = "{NotNull.PoliticaSeguridad.complejidadContrasenia}")
    @Range(min = 0, max = 1, message = "{Range.PoliticaSeguridad.complejidadContrasenia}")
    private Integer complejidadContrasenia;

    @NotNull(message = "{NotNull.PoliticaSeguridad.cantidadDiasParaCaducidadContrasenia}")
    @Range(min = 1, max = 999, message = "{Length.Politica.cantidadDiasParaCaducidadContrasenia}")
    private Integer cantidadDiasParaCaducidadContrasenia;

    @NotNull(message = "{NotNull.PoliticaSeguridad.longitudMinimaContrasenia}")
    @Range(min = 1, max = 99, message = "{Length.Politica.longitudMinimaContrasenia}")
    private Integer longitudMinimaContrasenia;

    @NotNull(message = "{NotNull.PoliticaSeguridad.autenticacionActiveDirectory}")
    @Range(min = 0, max = 1, message = "{Range.PoliticaSeguridad.autenticacionActiveDirectory}")
    private Integer autenticacionActiveDirectory;
}