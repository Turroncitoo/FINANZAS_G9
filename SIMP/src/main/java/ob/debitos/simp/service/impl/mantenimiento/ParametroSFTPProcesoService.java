package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IParametroSFTPProcesoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPProceso;
import ob.debitos.simp.service.IParametroSFTPProcesoService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ParametroSFTPProcesoService extends MantenibleService<ParametrosSFTPProceso>
	implements IParametroSFTPProcesoService {

	@SuppressWarnings("unused")
    private IParametroSFTPProcesoMapper parametrosSFTPProcesoMapper;

    public ParametroSFTPProcesoService(
            @Qualifier("IParametroSFTPProcesoMapper") IMantenibleMapper<ParametrosSFTPProceso> mapper)
    {
        super(mapper);
        this.parametrosSFTPProcesoMapper = (IParametroSFTPProcesoMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosSFTPProceso> buscarTodos() {
		return this.buscar(new ParametrosSFTPProceso(), Verbo.GETS);
	}

    @Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosSFTPProceso> buscarParametroSFTPProceso(String codigo) {
		ParametrosSFTPProceso parametrosSFTP = ParametrosSFTPProceso.builder().codigo(codigo).build();
		return this.buscar(parametrosSFTP, Verbo.GET);
	}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarParametroSFTPProceso(ParametrosSFTPProceso parametroSFTP) {
		this.registrar(parametroSFTP);
	}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarParametroSFTPProceso(ParametrosSFTPProceso parametroSFTP) {
    	this.actualizar(parametroSFTP);
	}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarParametroSFTPProceso(ParametrosSFTPProceso parametroSFTP) {
    	this.eliminar(parametroSFTP);
	}

    @Transactional(propagation = Propagation.REQUIRED)
	public boolean existeParametroSFTPProceso(String codigo) {
    	return !this.buscarParametroSFTPProceso(codigo).isEmpty();
	}
    
    
}
