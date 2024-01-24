package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IParametroWSMapper;
import ob.debitos.simp.model.mantenimiento.ParametroWS;
import ob.debitos.simp.service.IParametroWSService;


@Service
public class ParametroWSService implements IParametroWSService {
	
	private @Autowired IParametroWSMapper parametroWSMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ParametroWS> buscarTodos() {
    	return this.parametroWSMapper.buscarTodos();
	}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarParametroWS(ParametroWS parametroWS) {
    	 this.parametroWSMapper.actualizarParametroWS(parametroWS);
    	
	}

	
}
