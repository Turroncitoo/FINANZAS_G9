package ob.debitos.simp.model.mantenimiento;

import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClaseServicio;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.CodigoMoneda;
import ob.debitos.simp.validacion.CodigoOrigen;
import ob.debitos.simp.validacion.IdCodigoTransaccion;
import ob.debitos.simp.validacion.IdATM;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.IClase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdCodigoTransaccion(existe = true, groups = IClase.class)
@CodigoClaseServicio(existe = true, groups = IClase.class)
public class CuentaContableReceptor
{
    private Integer codigoInstitucion;

    @IdATM(existe = true, esAtmInstitucional = true, groups = ICampo.class)
    private Integer idATM;

    @CodigoMoneda(existe = true, groups = ICampo.class)
    private Integer codigoMoneda;

    @CodigoMembresia(existe = true, groups = ICampo.class)
    private String codigoMembresia;
    private String codigoClaseServicio;

    @CodigoOrigen(existe = true, groups = ICampo.class)
    private Integer codigoOrigen;

    @CodigoClaseTransaccion(existe = true, groups = ICampo.class)
    private Integer codigoClaseTransaccion;
    private Integer codigoTransaccion;
    private Integer idConceptoComision;

    private @Valid List<ContabConceptoCuenta> contabConceptosCuentas;
    private String cuentaCargo;
    private String cuentaAbono;
    private String cuentaAtm;
    private String codigoAnalitico;
    private String descripcionCodigoInstitucion;
    private String descripcionMoneda;
    private String descripcionATM;
    private String descripcionMembresia;
    private String descripcionClaseServicio;
    private String descripcionOrigen;
    private String descripcionClaseTransaccion;
    private String descripcionCodigoTransaccion;
}