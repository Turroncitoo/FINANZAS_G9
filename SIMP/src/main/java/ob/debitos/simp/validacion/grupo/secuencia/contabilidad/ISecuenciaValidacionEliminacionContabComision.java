package ob.debitos.simp.validacion.grupo.secuencia.contabilidad;

import javax.validation.GroupSequence;

import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.IClase;
import ob.debitos.simp.validacion.grupo.IClaseTres;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;

@GroupSequence({ ICampo.class, IClase.class, IClaseTres.class, IActualizacion.class })
public interface ISecuenciaValidacionEliminacionContabComision
{

}
