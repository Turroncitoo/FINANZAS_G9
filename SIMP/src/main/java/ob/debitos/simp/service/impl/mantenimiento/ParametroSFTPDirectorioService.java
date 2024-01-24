package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IParametroSFTPDirectorioMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPDirectorio;
import ob.debitos.simp.service.IParametroSFTPDirectorioService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ParametroSFTPDirectorioService extends MantenibleService<ParametrosSFTPDirectorio>
	implements IParametroSFTPDirectorioService {

	@SuppressWarnings("unused")
    private IParametroSFTPDirectorioMapper parametrosSFTPDirectorioMapper;

    public ParametroSFTPDirectorioService(
            @Qualifier("IParametroSFTPDirectorioMapper") IMantenibleMapper<ParametrosSFTPDirectorio> mapper)
    {
        super(mapper);
        this.parametrosSFTPDirectorioMapper = (IParametroSFTPDirectorioMapper) mapper;
    }
	
    @Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosSFTPDirectorio> buscarTodos(){
        return this.buscar(new ParametrosSFTPDirectorio(), Verbo.GETS);
	}
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarParametrosSFTPDirectorio(ParametrosSFTPDirectorio parametrosSFTP){
    	this.actualizar(parametrosSFTP);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ParametrosSFTPDirectorio> buscarParametroSFTPDirectorio(String codigoProceso, String tipo){
    	ParametrosSFTPDirectorio parametro = ParametrosSFTPDirectorio.builder()
    			.codigoProceso(codigoProceso)
    			.tipoOperacion(tipo).build();
    	return this.buscar(parametro, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarParametrosSFTPDirectorio(ParametrosSFTPDirectorio parametrosSFTP) {
    	this.registrar(parametrosSFTP);
	}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarParametrosSFTPDirectorio(ParametrosSFTPDirectorio parametrosSFTP) {
		this.eliminar(parametrosSFTP);
	}
    
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeParametroSFTPDirectorio(String codigoProceso, String tipo){
    	return !this.buscarParametroSFTPDirectorio(codigoProceso, tipo).isEmpty();
    }
}
