package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.AsociacionProductoCliente;
import ob.debitos.simp.validacion.CodigoProducto;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionBasico;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IExisteAsociacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.INoExisteAsociacion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AsociacionProductoCliente(existe = false, groups = { INoExisteAsociacion.class, IAsociacionActualizacion.class })
@AsociacionProductoCliente(existe = true, groups = IExisteAsociacion.class)
public class ProductoCliente
{

    @NotNull(message = "{NotNull.SubBin.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.SubBin.descripcion}", groups = IBasico.class)
    @Length(min = 1, max = 4, message = "{Length.SubBin.descripcion}", groups = IBasico.class)
    private String idEmpresa;

    private String descEmpresa;

    @NotNull(message = "{NotNull.SubBin.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.SubBin.descripcion}", groups = IBasico.class)
    @Length(min = 1, max = 4, message = "{Length.SubBin.descripcion}", groups = IBasico.class)
    private String idCliente;

    private String descCliente;

    @CodigoProducto(existe = true, groups = { ICampo.class, IAsociacionBasico.class })
    private String codigoProducto;

    private String descProducto;

}
