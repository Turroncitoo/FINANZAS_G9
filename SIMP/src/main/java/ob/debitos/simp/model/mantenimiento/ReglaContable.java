package ob.debitos.simp.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ob.debitos.simp.validacion.existeReglaContable;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionClase;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionClaseActualizacion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@existeReglaContable(existe = true, groups = { IActualizacion.class, IAsociacionClase.class,
		IAsociacionClaseActualizacion.class })
@existeReglaContable(existe = false, groups = IRegistro.class)
@existeReglaContable(existe = true, campoIdComercio = "idComercio", campoIdCliente = "idCliente", groups = IAsociacionClaseActualizacion.class)
public class ReglaContable
{
	private String idComercio;
	private String descComercio;
	private String idCliente;
	private String descCliente;
	private String idEmpresa;
	private String descEmpresa;
	private String operacion;
	private Integer monedaRecarga;
	private String descMonedaRecarga;
	private String cuentaCargo;
	private String cuentaAbono;
}
