package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaAutorizacion;
import ob.debitos.simp.model.reporte.ReporteAutorizacion;

public interface IReporteAutorizacionMapper
{
    public List<ReporteAutorizacion> buscarAutorizaciones(
            CriterioBusquedaAutorizacion criterioBusqueda);
}