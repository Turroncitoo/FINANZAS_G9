package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICodigoRptaVisaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoRespuestaVisa;
import ob.debitos.simp.service.ICodigoRptaVisaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CodigoRptaVisaService extends MantenibleService<CodigoRespuestaVisa>
        implements ICodigoRptaVisaService
{
    @SuppressWarnings("unused")
    private ICodigoRptaVisaMapper codigoRptaVisaMapper;

    public CodigoRptaVisaService(
            @Qualifier("ICodigoRptaVisaMapper") IMantenibleMapper<CodigoRespuestaVisa> mapper)
    {
        super(mapper);
        this.codigoRptaVisaMapper = (ICodigoRptaVisaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoRespuestaVisa> buscarTodos()
    {
        return this.buscar(new CodigoRespuestaVisa(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CodigoRespuestaVisa> buscarPorCodigoRptaVisa(String codigoRespuestaVisa)
    {
        CodigoRespuestaVisa codigoRptaVisa = CodigoRespuestaVisa.builder()
                .codigoRespuestaVisa(codigoRespuestaVisa).build();
        return this.buscar(codigoRptaVisa, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCodigoRptaVisa(String codigoRespuestaVisa)
    {
        return !this.buscarPorCodigoRptaVisa(codigoRespuestaVisa).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCodigoRptaVisa(CodigoRespuestaVisa codigoRptaVisa)
    {
        this.registrar(codigoRptaVisa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCodigoRptaVisa(CodigoRespuestaVisa codigoRptaVisa)
    {
        this.actualizar(codigoRptaVisa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCodigoRptaVisa(CodigoRespuestaVisa codigoRptaVisa)
    {
        this.eliminar(codigoRptaVisa);
    }
}