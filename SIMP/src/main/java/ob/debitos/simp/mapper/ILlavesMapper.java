package ob.debitos.simp.mapper;

import ob.debitos.simp.model.seguridad.LLaveZMK;
import ob.debitos.simp.model.seguridad.LLaveZPK;

public interface ILlavesMapper {
	
	public void registrarLLaveTransporte(LLaveZMK llaveZMK);
	
	public LLaveZMK obtenerLlaveTransporte();
	
	public void registrarLLaveTrabajo(LLaveZPK llaveZPK);
}
