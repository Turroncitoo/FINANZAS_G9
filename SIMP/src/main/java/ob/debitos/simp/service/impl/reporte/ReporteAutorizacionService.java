package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteAutorizacionMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaAutorizacion;
import ob.debitos.simp.model.reporte.ReporteAutorizacion;
import ob.debitos.simp.service.IReporteAutorizacionService;
import ob.debitos.simp.utilitario.ReporteUtil;

@Service
public class ReporteAutorizacionService implements IReporteAutorizacionService
{
    private @Autowired IReporteAutorizacionMapper reporteAutorizacionMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteAutorizacion> buscarAutorizaciones(
            CriterioBusquedaAutorizacion criterioBusqueda)
    {
        criterioBusqueda.setVerbo(ReporteUtil.obtenerVerboPorTipoPresentacion(
                criterioBusqueda.getTipoAutorizacion(), criterioBusqueda.getTipoPresentacion()));
        return reporteAutorizacionMapper.buscarAutorizaciones(criterioBusqueda);
    }
}