package ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente;

import javax.validation.GroupSequence;

@GroupSequence({ IAsociacionBasico.class, IAsociacionClase.class, IExisteAsociacion.class })
public interface ISecuenciaValidacionAsociacionEliminacion
{

}
