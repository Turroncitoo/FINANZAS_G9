package ob.debitos.simp.validacion.grupo.secuencia;

import javax.validation.GroupSequence;

import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.IClase;
import ob.debitos.simp.validacion.grupo.IClaseDos;
import ob.debitos.simp.validacion.grupo.IClaseTres;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@GroupSequence({ IBasico.class, ICampo.class, IClase.class, IClaseDos.class, IClaseTres.class,
        IRegistro.class })
public interface ISecuenciaValidacionRegistro
{

}