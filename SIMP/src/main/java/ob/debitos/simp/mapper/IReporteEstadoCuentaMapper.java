package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoCuenta;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.reporte.ReporteEstadoCuentaPrepago;

public interface IReporteEstadoCuentaMapper
{
   
    List<ReporteEstadoCuentaPrepago> buscarPorCriterio(CriterioBusquedaEstadoCuenta criterioBusquedaEstadoCuenta);
    List<ReporteEstadoCuentaPrepago> buscarPorNumeroDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaEstadoCuenta);
}
