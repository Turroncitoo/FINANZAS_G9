package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoCuenta;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.reporte.ReporteEstadoCuentaPrepago;

public interface IReporteEstadoCuentaService
{

    List<ReporteEstadoCuentaPrepago> buscarPorCriterio(CriterioBusquedaEstadoCuenta criterioBusquedaEstadoCuenta);

    List<ReporteEstadoCuentaPrepago> buscarPorNumeroDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaEstadoCuenta);

    
}
