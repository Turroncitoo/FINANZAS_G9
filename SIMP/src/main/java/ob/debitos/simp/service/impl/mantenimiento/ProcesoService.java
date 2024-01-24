package ob.debitos.simp.service.impl.mantenimiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IProcesoMapper;
import ob.debitos.simp.model.proceso.EntradaProceso;
import ob.debitos.simp.service.IProcesoService;

@Service
public class ProcesoService implements IProcesoService
{
    private @Autowired IProcesoMapper procesoMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public Integer ejecutarProceso(EntradaProceso entradaProceso)
    {
        return procesoMapper.ejecutarProceso(entradaProceso);
    }
}