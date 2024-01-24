package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoLogo;
import ob.debitos.simp.validacion.CodigoProducto;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto
{
    @CodigoLogo(existe = true, groups = ICampo.class)
    private String idLogo;

    @CodigoProducto(existe = true, groups = IActualizacion.class)
    @CodigoProducto(existe = false, message = "{Existe.Producto.codigoProducto}", groups = IRegistro.class)
    private String codigoProducto;

    @NotNull(message = "{NotNull.Producto.descripcion}")
    @NotBlank(message = "{NotBlank.Producto.descripcion}")
    @Length(min = 5, max = 30, message = "{Length.Producto.descripcion}")
    private String descProducto;

    private String idEmpresa;

    private String idCliente;

    private String idBin;

    @NotNull(message = "{NotNull.Producto.codigoMoneda}")
    private Integer codigoMoneda;

    @Range(min = 3, max = 11, message = "{Range.Producto.longitudBin}", groups = IBasico.class)
    private Integer longitudBin;

    @Range(min = 14, max = 19, message = "{Range.Producto.longitudPan}", groups = IBasico.class)
    private Integer longitudPan;

    // DESCRIPCION
    private String descEmpresa;
    private String descLogo;
    private String descMoneda;
    private String descCliente;
}