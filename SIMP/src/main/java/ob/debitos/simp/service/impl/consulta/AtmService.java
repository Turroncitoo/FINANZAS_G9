package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IAtmMapper;
import ob.debitos.simp.model.consulta.administrativa.Atm;
import ob.debitos.simp.service.IAtmService;
import ob.debitos.simp.service.IParametroGeneralService;

@Service
public class AtmService implements IAtmService
{
    private @Autowired IAtmMapper atmMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Atm> buscarTodos()
    {
        return atmMapper.buscarTodos();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Atm> buscarPorCodigoInstitucion(Integer codigoInstitucion)
    {
        return atmMapper.buscarPorCodigoInstitucion(codigoInstitucion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Atm> buscarPorCodigoInstitucionActual()
    {
        int codigoInstitucion = parametroGeneralService.buscarCodigoInstitucion();
        return this.buscarPorCodigoInstitucion(codigoInstitucion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Atm> buscarPorIdATM(Integer idATM)
    {
        return atmMapper.buscarPorIdATM(idATM);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Atm> buscarPorIdATMCodigoInstitucion(Integer idATM, Integer codigoInstitucion)
    {
        return atmMapper.buscarPorIdATMCodigoInstitucion(idATM, codigoInstitucion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeATM(Integer idATM, boolean esATMInstitucional)
    {
        boolean existe = false;
        if (esATMInstitucional)
        {
            existe = !this.buscarPorIdATMCodigoInstitucion(idATM,
                    parametroGeneralService.buscarCodigoInstitucion()).isEmpty();
        } else
        {
            existe = !this.buscarPorIdATM(idATM).isEmpty();
        }
        return existe;
    }
}