package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITipoEmisionMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.TipoEmision;
import ob.debitos.simp.service.ITipoEmisionService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class TipoEmisionService extends MantenibleService<TipoEmision> implements ITipoEmisionService {

	@SuppressWarnings("unused")
	private ITipoEmisionMapper tipoEmisionMapper;
	
	public TipoEmisionService(@Qualifier("ITipoEmisionMapper") IMantenibleMapper<TipoEmision> mapper) {
		super(mapper);
		this.tipoEmisionMapper = (ITipoEmisionMapper) mapper;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<TipoEmision> buscarTodos() {
		return super.buscar(new TipoEmision(), Verbo.GETS);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<TipoEmision> buscarTipoEmisionPorCodigo(String codigo) {
		TipoEmision tipoEmision = TipoEmision.builder().codigo(codigo).build();
		return super.buscar(tipoEmision, Verbo.GET);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existeTipoEmision(String codigo) {
		return !this.buscarTipoEmisionPorCodigo(codigo).isEmpty();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarTipoEmision(TipoEmision tipoEmision) {
		super.registrar(tipoEmision);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarTipoEmision(TipoEmision TipoEmision) {
		super.actualizar(TipoEmision);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarTipoEmision(TipoEmision tipoEmision) {
		super.eliminar(tipoEmision);
	}
	
}
