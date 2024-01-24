package ob.debitos.simp.model.mantenimiento;

import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClaseServicio;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.CodigoLogo;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.CodigoProducto;
import ob.debitos.simp.validacion.CodigoMoneda;
import ob.debitos.simp.validacion.CodigoOrigen;
import ob.debitos.simp.validacion.IdCliente;
import ob.debitos.simp.validacion.IdCodigoTransaccion;
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.MultitabDet;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.IClase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdCliente(existe = true, groups = IClase.class)
@IdCodigoTransaccion(existe = true, groups = IClase.class)
@CodigoClaseServicio(existe = true, groups = IClase.class)
public class CuentaContableEmisor
{
    private Integer codigoInstitucion;

    @IdEmpresa(existe = true, groups = ICampo.class)
    private String idEmpresa;
    private String idCliente;

    @CodigoMoneda(existe = true, groups = ICampo.class)
    private Integer codigoMoneda;

    @CodigoMembresia(existe = true, groups = ICampo.class)
    private String codigoMembresia;
    private String codigoClaseServicio;

    //@CodigoBin(existe = true, groups = ICampo.class)
    private String codigoBIN;
    
    @CodigoProducto(existe=true, groups =ICampo.class)
    private String codigoProducto;

    @CodigoOrigen(existe = true, groups = ICampo.class)
    private Integer codigoOrigen;
    @MultitabDet(existe = true, min = 1, max = 1, campoIdItem = "idRolTransaccion", idTabla = 3, message = "{NoExiste.MultitabDet.idRolTransaccion}", groups = ICampo.class)
    private Integer idRolTransaccion;

    @CodigoClaseTransaccion(existe = true, groups = ICampo.class)
    private Integer codigoClaseTransaccion;
    private Integer idCodigoTransaccion;
    private Integer codigoTransaccion;
    private Integer idConceptoComision;
    
    @CodigoLogo(existe = true, groups = ICampo.class)
    private String idLogo;

    private @Valid List<ContabConceptoCuenta> contabConceptosCuentas;
    private String cuentaCargo;
    private String cuentaAbono;
    private String codigoAnalitico;
    private String descripcionInstitucion;
    private String descripcionLogo;
    private String descripcionEmpresa;
    private String descripcionCliente;
    private String descripcionMoneda;
    private String descripcionMembresia;
    private String descripcionClaseServicio;
    private String descripcionBIN;
    private String descripcionProducto;
    private String descripcionOrigen;
    private String descripcionClaseTransaccion;
    private String descripcionCodigoTransaccion;
    private String descripcionRolTransaccion;
}