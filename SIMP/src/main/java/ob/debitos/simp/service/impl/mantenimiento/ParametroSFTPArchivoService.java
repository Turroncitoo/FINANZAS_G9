package ob.debitos.simp.service.impl.mantenimiento;

import ob.debitos.simp.service.IParametroSFTPArchivoService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IParametroSFTPArchivoMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPArchivo;

@Service
public class ParametroSFTPArchivoService extends MantenibleService<ParametrosSFTPArchivo> 
	implements IParametroSFTPArchivoService {
	
	@SuppressWarnings("unused")
	private IParametroSFTPArchivoMapper parametroSFTPArchivoMapper;
	
	public ParametroSFTPArchivoService(
			@Qualifier("IParametroSFTPArchivoMapper")IParametroSFTPArchivoMapper mapper){
		super(mapper);
		this.parametroSFTPArchivoMapper = (IParametroSFTPArchivoMapper)mapper;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosSFTPArchivo> buscarTodos() {
		return this.buscar(new ParametrosSFTPArchivo(), Verbo.GETS);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosSFTPArchivo> buscarPorIdArchivo(String idArchivo) {
		ParametrosSFTPArchivo parametroSFTP = ParametrosSFTPArchivo.builder().codigoArchivo(idArchivo).build();
		return this.buscar(parametroSFTP, Verbo.GET);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarParametroSFTPArchivo(ParametrosSFTPArchivo parametroSFTP) {
		this.registrar(parametroSFTP);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarParametroSFTPArchivo(ParametrosSFTPArchivo parametroSFTP) {
		this.actualizar(parametroSFTP);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarParametroSFTPArchivo(ParametrosSFTPArchivo parametrosSFTP) {
		this.eliminar(parametrosSFTP);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existeParametroSFTPArchivo(String idParametroSFTP) {
		return !this.buscarPorIdArchivo(idParametroSFTP).isEmpty();
	}

}
