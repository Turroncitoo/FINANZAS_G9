package ob.debitos.simp.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ISecRecursoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.seguridad.SecRecurso;
import ob.debitos.simp.service.ISecRecursoService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class SecRecursoService extends MantenibleService<SecRecurso> implements ISecRecursoService 
{
	private ISecRecursoMapper secRecursoMapper;
	private static final String GETS_AUDIT = "GETS_AUDIT";

	public SecRecursoService(@Qualifier("ISecRecursoMapper") IMantenibleMapper<SecRecurso> mapper) 
	{
		super(mapper);
		this.secRecursoMapper = (ISecRecursoMapper) mapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<SecRecurso> buscarTodos() 
	{
		return this.buscar(new SecRecurso(), Verbo.GETS);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<SecRecurso> buscarPorIdUsuario(String idUsuario) 
	{
		return secRecursoMapper.obtenerRecursosPermitidosPorIdUsuario(idUsuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SecRecurso> buscarPorCodigoRecurso(String idRecurso) 
	{
		SecRecurso secRecurso = SecRecurso.builder().idRecurso(idRecurso).build();
		return this.buscar(secRecurso, Verbo.GET);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SecRecurso> buscarAuditables() {
		return this.buscar(new SecRecurso(), GETS_AUDIT);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarRecurso(SecRecurso recurso) 
	{
		this.registrar(recurso);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarRecurso(SecRecurso recurso) 
	{
		this.actualizar(recurso);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarRecurso(SecRecurso recurso) 
	{
		this.eliminar(recurso);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existeRecurso(String idRecurso) 
	{
		return !this.buscarPorCodigoRecurso(idRecurso).isEmpty();
	}
}