package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IPINEntryCapabilityMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.PINEntryCapability;
import ob.debitos.simp.service.IPINEntryCapabilityService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

/**
 * Ejecuta la lógica de negocio asociada a las operaciones de mantenimiento de
 * las capacidades de ingreso de las transacciones de tipo PIN.
 * <p>
 * Esta clase al ser un servicio {@literal @Service} de mantenimieno extiende de
 * {@link MantenibleService} e implementa la interface {@link IPINEntryCapabilityService} y
 * delega la función de acceso a la base de datos a la interface
 * {@link IPINEntryCapabilityMapper} solo para operaciones de mantenimiento.
 * 
 * @author Fernando Fonseca
 */
@Service
public class PINEntryCapabilityService extends MantenibleService<PINEntryCapability> implements IPINEntryCapabilityService
{
	@SuppressWarnings("unused")
	private IPINEntryCapabilityMapper pinEntryCapabilityMapper;
	
    /**
     * Instancia la interface {@link IPINEntryCapabilityMapper} a partir de la interface
     * genérica {@link IMantenibleMapper}.
     * 
     * @param mapper
     *            interface genérica para las interfaces <b>mapper</b> de
     *            mantenimiento
     */
	public PINEntryCapabilityService(@Qualifier("IPINEntryCapabilityMapper") IMantenibleMapper<PINEntryCapability> mapper){
		super(mapper);
		this.pinEntryCapabilityMapper = (IPINEntryCapabilityMapper) mapper;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<PINEntryCapability> buscarTodos() {
		return this.buscar(new PINEntryCapability(), Verbo.GETS);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PINEntryCapability> buscarPorCodigo(String codigo) {
		PINEntryCapability pinEntryCapability = PINEntryCapability.builder().codigo(codigo).build();
		return this.buscar(pinEntryCapability, Verbo.GET);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existePINEntryCapability(String codigo) {
		return !this.buscarPorCodigo(codigo).isEmpty();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarPINEntryCapability(PINEntryCapability pinEntryCapability) {
		this.registrar(pinEntryCapability);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void actualizarPINEntryCapability(PINEntryCapability pinEntryCapability) {
		this.actualizar(pinEntryCapability);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarPINEntryCapability(PINEntryCapability pinEntryCapability) {
		this.eliminar(pinEntryCapability);
	}

}
