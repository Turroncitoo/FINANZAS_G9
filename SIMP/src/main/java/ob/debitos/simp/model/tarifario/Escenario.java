package ob.debitos.simp.model.tarifario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClaseServicio;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.CodigoOrigen;
import ob.debitos.simp.validacion.IdCodigoTransaccion;
import ob.debitos.simp.validacion.IdEscenario;
import ob.debitos.simp.validacion.IdTipoTarifa;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.IClase;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdEscenario(existe = true, groups = IActualizacion.class)
@IdEscenario(existe = false, groups = IRegistro.class)
@IdCodigoTransaccion(existe = true, groups = IClase.class)
@CodigoClaseServicio(existe = true, groups = IClase.class)
public class Escenario
{
    private int codigoInstitucion;

    @CodigoMembresia(existe = true, groups = ICampo.class)
    private String codigoMembresia;
    private String codigoClaseServicio;

    @CodigoClaseTransaccion(existe = true, groups = ICampo.class)
    private int codigoClaseTransaccion;
    private int codigoTransaccion;

    @CodigoOrigen(existe = true, groups = ICampo.class)
    private int codigoOrigen;

    @IdTipoTarifa(existe = true, groups = ICampo.class)
    private int codigoTipoTarifa;

    private String descripcionInstitucion;
    private String descripcionMembresia;
    private String descripcionClaseServicio;
    private String descripcionClaseTransaccion;
    private String descripcionCodigoTransaccion;
    private String descripcionOrigen;
    private String descripcionTipoTarifa;
}