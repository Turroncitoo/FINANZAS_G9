package ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente;

import javax.validation.GroupSequence;

@GroupSequence({ IAsociacionBasico.class, IAsociacionClase.class, INoExisteAsociacion.class })
public interface ISecuenciaValidacionAsociacionRegistro
{

}
