package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IAfinidadMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Afinidad;
import ob.debitos.simp.service.IAfinidadService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class AfinidadService extends MantenibleService<Afinidad> implements IAfinidadService 
{

	@SuppressWarnings("unused")
	private IAfinidadMapper afinidadMapper;
	
	public AfinidadService(@Qualifier("IAfinidadMapper") IMantenibleMapper<Afinidad> mapper) 
	{
		super(mapper);
		this.afinidadMapper = (IAfinidadMapper) mapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Afinidad> buscarTodos() 
	{
		return super.buscar(new Afinidad(), Verbo.GETS);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Afinidad> buscarAfinidadPorLogo(String idLogo) 
	{
		Afinidad afinidad = Afinidad.builder().idLogo(idLogo).build();
		return super.buscar(afinidad, Verbo.GET_LOGO);
	}
	
	@Override
	public List<Afinidad> buscarAfinidadPorCodigoIdLogo(String codigo, String idLogo) 
	{
		Afinidad afinidad = Afinidad.builder().codigo(codigo).idLogo(idLogo).build();
		return super.buscar(afinidad, Verbo.GET);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existeAfinidad(String codigo, String idLogo) 
	{
		return !this.buscarAfinidadPorCodigoIdLogo(codigo, idLogo).isEmpty();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarAfinidad(Afinidad afinidad) 
	{
		super.registrar(afinidad);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarAfinidad(Afinidad afinidad) 
	{
		super.actualizar(afinidad);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarAfinidad(Afinidad afinidad) 
	{
		super.eliminar(afinidad);
	}
	
}
