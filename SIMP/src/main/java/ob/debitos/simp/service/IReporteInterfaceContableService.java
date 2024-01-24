package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import ob.debitos.simp.model.criterio.CriterioBusquedaReporteInterfaceContable;
import ob.debitos.simp.model.reporte.ReporteInterfaceContable;

public interface IReporteInterfaceContableService
{

    public List<ReporteInterfaceContable> buscarInterfaceContablePorCriterio(CriterioBusquedaReporteInterfaceContable criterioBusquedaReporteInterfaceContable);

    public void generarReporteInterfaceContable(List<ReporteInterfaceContable> list, CriterioBusquedaReporteInterfaceContable criterioBusquedaReporteInterfaceContable, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
