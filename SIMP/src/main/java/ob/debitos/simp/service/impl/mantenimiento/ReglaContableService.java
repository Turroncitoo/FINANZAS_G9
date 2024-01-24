package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReglaContableMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ReglaContable;
import ob.debitos.simp.service.IReglaContableService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ReglaContableService extends MantenibleService<ReglaContable> implements IReglaContableService
{
	@SuppressWarnings("unused")
	private IReglaContableMapper reglaContableMapper;

	public ReglaContableService(@Qualifier("IReglaContableMapper") IMantenibleMapper<ReglaContable> mantenibleMapper)
	{
		super(mantenibleMapper);
		this.reglaContableMapper = (IReglaContableMapper) mantenibleMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReglaContable> buscarTodos()
	{
		return this.buscar(new ReglaContable(), Verbo.GETS);
	}

	@Override
	public List<ReglaContable> buscarPorClave(String idComercio, String idCliente)
	{
		ReglaContable reglaContable = ReglaContable.builder().idComercio(idComercio).idCliente(idCliente).build();
		return this.buscar(reglaContable, Verbo.GET);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existeReglaContable(String idComercio, String idCliente)
	{
		return !this.buscarPorClave(idComercio, idCliente).isEmpty();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarReglaContable(ReglaContable reglaContable)
	{
		this.registrar(reglaContable);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarReglaContable(ReglaContable reglaContable)
	{
		this.actualizar(reglaContable);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarReglaContable(ReglaContable reglaContable)
	{
		this.eliminar(reglaContable);
	}
}
