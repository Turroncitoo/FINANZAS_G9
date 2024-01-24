package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.criterio.CriterioBusquedaResumen;
import ob.debitos.simp.model.reporte.ReporteMoneda;

public interface IReporteComisionCuadroBancoAdministradorService
{
    public List<ReporteMoneda> buscarComisionesCuadroBancoAdministrador(
            CriterioBusquedaResumen criterioBusquedaResumen);

    public List<ReporteMoneda> buscarSumarioCompensacion(
            CriterioBusquedaResumen criterioBusquedaResumen);

    void descargarResumenCuadreBancoAdministrador(
            CriterioBusquedaResumen criterioBusquedaResumen,
            HttpServletResponse httpServletResponse) throws IOException;
}