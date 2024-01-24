package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICuentaMapper;
import ob.debitos.simp.model.consulta.administrativa.Cuenta;
import ob.debitos.simp.model.criterio.CriterioBusquedaCuenta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.service.ICuentaService;

@Service
public class CuentaService implements ICuentaService
{
    
    private @Autowired ICuentaMapper cuentaMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Cuenta> buscarTodos()
    {
        return cuentaMapper.buscarTodos();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Cuenta> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        return this.cuentaMapper.buscarPorTipoDocumento(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Cuenta> buscarPorCriterios(CriterioBusquedaCuenta criterioBusqueda)
    {
        return this.cuentaMapper.buscarPorCriterios(criterioBusqueda);
    }
    
}