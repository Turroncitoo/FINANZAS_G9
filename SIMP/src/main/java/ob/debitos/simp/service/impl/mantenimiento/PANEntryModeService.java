package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IPANEntryModeMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.PANEntryMode;
import ob.debitos.simp.service.IPANEntryModeService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

/**
 * Ejecuta la lógica de negocio asociada a las operaciones de mantenimiento de
 * modo de entrada de tarjeta.
 * <p>
 * Esta clase al ser un servicio {@literal @Service} de mantenimieno extiende de
 * {@link MantenibleService} e implementa la interface {@link IPANEntryModeService} y
 * delega la función de acceso a la base de datos a la interface
 * {@link IPANEntryModeMapper} solo para operaciones de mantenimiento.
 * 
 * @author Fernando Fonseca
 */
@Service
public class PANEntryModeService extends MantenibleService<PANEntryMode> 
	implements IPANEntryModeService {
	
	@SuppressWarnings("unused")
	private IPANEntryModeMapper panEntryModeMapper;
	
    /**
     * Instancia la interface {@link IPANEntryModeMapper} a partir de la interface
     * genérica {@link IMantenibleMapper}.
     * 
     * @param mapper
     *            interface genérica para las interfaces <b>mapper</b> de
     *            mantenimiento
     */
	public PANEntryModeService(@Qualifier("IPANEntryModeMapper") IMantenibleMapper<PANEntryMode> mapper){
		super(mapper);
		this.panEntryModeMapper = (IPANEntryModeMapper) mapper;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<PANEntryMode> buscarTodos() {
		return this.buscar(new PANEntryMode(), Verbo.GETS);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PANEntryMode> buscarPorCodigo(String codigo) {
		PANEntryMode panEntryMode = PANEntryMode.builder().codigo(codigo).build();
		return this.buscar(panEntryMode, Verbo.GET);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existePANEntryMode(String codigo) {
		return !this.buscarPorCodigo(codigo).isEmpty();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarPANEntryMode(PANEntryMode panEntryMode) {
		this.registrar(panEntryMode);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarPANEntryMode(PANEntryMode panEntryMode) {
		this.actualizar(panEntryMode);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarPANEntryMode(PANEntryMode panEntryMode) {
		this.eliminar(panEntryMode);
	}

}
