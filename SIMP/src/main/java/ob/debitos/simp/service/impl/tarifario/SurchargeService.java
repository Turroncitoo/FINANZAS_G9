package ob.debitos.simp.service.impl.tarifario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ISurchargeMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.tarifario.Surcharge;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.ISurchargeService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class SurchargeService extends MantenibleService<Surcharge>
        implements ISurchargeService
{
    @SuppressWarnings("unused")
    private ISurchargeMapper surchargeMapper;
    
    private @Autowired IParametroGeneralService parametroGeneralService;

    public SurchargeService(@Qualifier("ISurchargeMapper") IMantenibleMapper<Surcharge> mapper)
    {
        super(mapper);
        this.surchargeMapper = (ISurchargeMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Surcharge> buscarTodos()
    {
        return this.buscar(new Surcharge(), Verbo.GETS);
    }

    public List<Surcharge> buscarPorCodigo(Surcharge surcharge)
    {
        surcharge.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        return this.buscar(surcharge, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Surcharge> registrarSurcharge(Surcharge surcharge)
    {
        surcharge.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.registrar(surcharge);
        return this.buscarPorCodigo(surcharge);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Surcharge> actualizarSurcharge(Surcharge surcharge)
    {
        surcharge.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.actualizar(surcharge);
        return this.buscarPorCodigo(surcharge);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarSurcharge(Surcharge surcharge)
    {
        surcharge.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.eliminar(surcharge);
    }
}