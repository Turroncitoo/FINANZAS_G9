package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICanalesSegurosWSMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;

import ob.debitos.simp.model.mantenimiento.CanalesSegurosWS;
import ob.debitos.simp.service.ICanalesSegurosWSService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CanalesSegurosWSService extends MantenibleService<CanalesSegurosWS> implements ICanalesSegurosWSService {

	@SuppressWarnings("unused")
	private ICanalesSegurosWSMapper canalesSegurosMapper;

	public CanalesSegurosWSService(@Qualifier("ICanalesSegurosWSMapper") IMantenibleMapper<CanalesSegurosWS> mapper) {
		super(mapper);
		this.canalesSegurosMapper = (ICanalesSegurosWSMapper) mapper;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<CanalesSegurosWS> buscarTodos() {
		return this.buscar(new CanalesSegurosWS(), Verbo.GETS);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CanalesSegurosWS> buscarPorIdCanal(String idCanal) {
		CanalesSegurosWS canalesSegurosWS = CanalesSegurosWS.builder().idCanal(idCanal).build();
		return this.buscar(canalesSegurosWS, Verbo.GET);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCanalSeguro(String idCanal)
    {
        return !this.buscarPorIdCanal(idCanal).isEmpty();
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarCanalSeguroWS(CanalesSegurosWS canalesSegurosWS) {
		String aux = canalesSegurosWS.getContrasenia();
		BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
		canalesSegurosWS.setContrasenia(passwordEnconder.encode(aux));
		this.actualizar(canalesSegurosWS);
		this.registrar(canalesSegurosWS);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarCanalSeguroWS(CanalesSegurosWS canalesSegurosWS) {
		String aux = canalesSegurosWS.getContrasenia();
		BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
		canalesSegurosWS.setContrasenia(passwordEnconder.encode(aux));
		this.actualizar(canalesSegurosWS);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarCanalSeguroWS(CanalesSegurosWS canalesSegurosWS) {
		this.eliminar(canalesSegurosWS);
	}
}
