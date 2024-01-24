package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICuentaPPMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaCuenta;
import ob.debitos.simp.model.prepago.CuentaPP;
import ob.debitos.simp.service.ICuentaPPService;

@Service
public class CuentaPPService implements ICuentaPPService
{

    private @Autowired ICuentaPPMapper cuentaPPMapper;

    @Override
    public List<CuentaPP> buscarTodos()
    {
        return cuentaPPMapper.buscarTodos();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaPP> buscarPorCriterios(CriterioBusquedaCuenta criterioBusqueda)
    {
        return this.cuentaPPMapper.buscarPorCriterios(criterioBusqueda);
    }

}
