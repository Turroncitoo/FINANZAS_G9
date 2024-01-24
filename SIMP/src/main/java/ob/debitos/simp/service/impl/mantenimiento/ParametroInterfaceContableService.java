package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ob.debitos.simp.mapper.IParametroInterfaceContableMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosInterfaceContable;
import ob.debitos.simp.service.IParametroInterfaceContableService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ParametroInterfaceContableService extends MantenibleService<ParametrosInterfaceContable>
	implements IParametroInterfaceContableService{
	
	 @SuppressWarnings("unused")
	    private IParametroInterfaceContableMapper parametroInterfaceContableMapper;
	    private static final String GETS_MANT = "GETS_MANT";

	    public ParametroInterfaceContableService(
	            @Qualifier("IParametroInterfaceContableMapper") IMantenibleMapper<ParametrosInterfaceContable> mapper)
	    {
	        super(mapper);
	        this.parametroInterfaceContableMapper = (IParametroInterfaceContableMapper) mapper;
	    }
	    
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosInterfaceContable> buscarTodos() {
		
		return   this.buscar(new ParametrosInterfaceContable(), Verbo.GETS);
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosInterfaceContable> buscarTodosParaMantenimiento() {
		
		return this.buscar(new ParametrosInterfaceContable(), GETS_MANT);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarParametroInterfaceContable(ParametrosInterfaceContable parametroInterfaceContable) {
		this.actualizar(parametroInterfaceContable);
		
	}
	
	
}
