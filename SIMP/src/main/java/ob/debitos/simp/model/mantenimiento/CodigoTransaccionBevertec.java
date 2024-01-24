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
import ob.debitos.simp.validacion.CodigoTxnBevertec;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.IClase;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoTxnBevertec(existe = true, groups = IActualizacion.class)
@CodigoTxnBevertec(existe = false, groups = IRegistro.class)
@CodigoProcBevertec(existe = true, groups = IClase.class)
public class CodigoTransaccionBevertec
{
    @CodigoCanalEmisor(existe = true, groups = IBasico.class)
    private String codigoCanalEmisor;

    private String tipoTransaccion;
    private String codTransaccion;

    @NotNull(message = "{NotNull.CodigoTxnBevertec.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.CodigoTxnBevertec.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 60, message = "{Length.CodigoTxnBevertec.descripcion}", groups = IBasico.class)
    private String descripcion;
    private String descripcionCanalEmisor;
    private String descripcionTipoTransaccion;
}