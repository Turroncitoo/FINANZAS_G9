package ob.debitos.simp.service;

import ob.debitos.simp.model.seguridad.ComponenteLlaveZMK;
import ob.debitos.simp.model.seguridad.LLaveZMK;
import ob.debitos.simp.model.seguridad.LLaveZPK;

public interface ILlavesService {
	
	public void registrarLLaveTransporte(ComponenteLlaveZMK componenteLlaveZMK);
	
	public void registrarLLaveTrabajo(LLaveZPK llaveZPK);
	
	public boolean existeLlaveTransporte();
	
	public LLaveZMK obtenerLlaveTransporte();
	
	
}
