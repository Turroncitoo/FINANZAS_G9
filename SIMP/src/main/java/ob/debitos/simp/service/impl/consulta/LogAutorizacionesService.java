package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ILogAutorizacionesMapper;
import ob.debitos.simp.model.consulta.LogAutorizacion;
import ob.debitos.simp.model.consulta.LogAutorizacionNoConciliadaPorFecha;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionNoConciliada;
import ob.debitos.simp.service.ILogAutorizacionesService;

@Service
public class LogAutorizacionesService implements ILogAutorizacionesService
{
    private @Autowired ILogAutorizacionesMapper logAutorizacionesMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogAutorizacion> buscarAutorizacionesNoConciliadasPorCriterios(
            CriterioBusquedaTransaccionNoConciliada criterioBusquedaTransaccionNoConciliada)
    {
        return logAutorizacionesMapper.buscarAutorizacionesNoConciliadasPorCriterios(
                criterioBusquedaTransaccionNoConciliada);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogAutorizacionNoConciliadaPorFecha> buscarAutorizacionesNoConciliadasPorDia(
            CriterioBusquedaTransaccionNoConciliada criterioBusquedaTransaccionNoConciliada)
    {
        return logAutorizacionesMapper
                .buscarAutorizacionesNoConciliadasPorDia(criterioBusquedaTransaccionNoConciliada);
    }
}