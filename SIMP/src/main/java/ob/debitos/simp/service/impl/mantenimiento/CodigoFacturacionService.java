package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICodigoFacturacionMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoFacturacion;
import ob.debitos.simp.service.ICodigoFacturacionService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;



@Service
public class CodigoFacturacionService extends MantenibleService<CodigoFacturacion>
implements ICodigoFacturacionService{
	@SuppressWarnings("unused")
    private ICodigoFacturacionMapper codigoFacturacionMapper;
	
    /**
     * Instancia la interface {@link ICodigoFacturacionMapper} a partir de la interface
     * genérica {@link IMantenibleMapper}.
     * 
     * @param mapper
     *            interface genérica para las interfaces <b>mapper</b> de
     *            mantenimiento
     */
	public CodigoFacturacionService(
            @Qualifier("ICodigoFacturacionMapper") IMantenibleMapper<CodigoFacturacion> mapper)
    {
        super(mapper);
        this.codigoFacturacionMapper = (ICodigoFacturacionMapper) mapper;
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<CodigoFacturacion> buscarTodos() {
		return this.buscar(new CodigoFacturacion(), Verbo.GETS);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CodigoFacturacion> buscarPorCodigo(Integer idCodigoFacturacion) {
		CodigoFacturacion codigo = CodigoFacturacion.builder().idCodigoFacturacion(idCodigoFacturacion).build();
		return this.buscar(codigo, Verbo.GET);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void registrarCodigoFacturacion(CodigoFacturacion codigoFacturacion) {
		this.registrar(codigoFacturacion);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void actualizarCodigoFacturacion(CodigoFacturacion codigoFacturacion) {
		this.actualizar(codigoFacturacion);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void eliminarCodigoFacturacion(CodigoFacturacion codigoFacturacion) {
		this.eliminar(codigoFacturacion);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean existeCodigoFacturacion(Integer idCodigoFacturacion) {
		return !this.buscarPorCodigo(idCodigoFacturacion).isEmpty();
	}


}
