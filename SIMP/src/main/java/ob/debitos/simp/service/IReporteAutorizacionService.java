package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaAutorizacion;
import ob.debitos.simp.model.reporte.ReporteAutorizacion;

public interface IReporteAutorizacionService
{
    public List<ReporteAutorizacion> buscarAutorizaciones(
            CriterioBusquedaAutorizacion criterioBusqueda);
}
