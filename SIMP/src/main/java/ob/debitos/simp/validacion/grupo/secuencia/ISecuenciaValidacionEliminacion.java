package ob.debitos.simp.validacion.grupo.secuencia;

import javax.validation.GroupSequence;

import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;

@GroupSequence({ ICampo.class, IActualizacion.class })
public interface ISecuenciaValidacionEliminacion
{

}
