package ob.debitos.simp.service.impl.seguridad;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ILlavesMapper;
import ob.debitos.simp.model.seguridad.ComponenteLlaveZMK;
import ob.debitos.simp.model.seguridad.LLaveZMK;
import ob.debitos.simp.model.seguridad.LLaveZPK;
import ob.debitos.simp.service.ILlavesService;
import ob.debitos.simp.utilitario.KeyUtil;
import ob.debitos.simp.utilitario.TDESUtil;

@Service
public class LlavesService implements ILlavesService {
	
	private String keyStore = "F5A0100C277F4823C820FBDC0DC7F239";
	private @Autowired ILlavesMapper llavesMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarLLaveTransporte(ComponenteLlaveZMK componenteLlaveZMZ) {
		String llaveZMK = KeyUtil.generarLLaveZMK(componenteLlaveZMZ);
		String zmk2Store = null;
		TDESUtil tDESUtil = new TDESUtil();
		tDESUtil.setKey(keyStore);
		try {
			zmk2Store = tDESUtil.encriptar(llaveZMK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.llavesMapper.registrarLLaveTransporte(new LLaveZMK(zmk2Store));
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarLLaveTrabajo(LLaveZPK llaveZPK) {
		TDESUtil tDESUtil = new TDESUtil();
		LLaveZMK llaveZMK = this.obtenerLlaveTransporte();
		String llaveZMKDecripted =  null;
		String llaveZPKDecripted = null;
		String encriptedZPK2Store = null;
		try {
			tDESUtil.setKey(keyStore);
			llaveZMKDecripted = tDESUtil.desencriptar(llaveZMK.getLlaveZMK());
			tDESUtil.setKey(llaveZMKDecripted);
			llaveZPKDecripted =  tDESUtil.desencriptar(llaveZPK.getLlaveZPK());
			tDESUtil.setKey(keyStore);
			encriptedZPK2Store = tDESUtil.encriptar(llaveZPKDecripted);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.llavesMapper.registrarLLaveTrabajo(new LLaveZPK(encriptedZPK2Store));
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean existeLlaveTransporte() {
		LLaveZMK llave = this.llavesMapper.obtenerLlaveTransporte();
		if(llave==null){
			return false;
		}else{
			if(llave.getLlaveZMK() != null && llave.getLlaveZMK().equals("")){
				return false;
			}else{
				return true;
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LLaveZMK obtenerLlaveTransporte() {
		LLaveZMK llave = this.llavesMapper.obtenerLlaveTransporte();
		if(llave == null){
			return new LLaveZMK("");
		}else{
			return llave;
		}
	}

	

}
