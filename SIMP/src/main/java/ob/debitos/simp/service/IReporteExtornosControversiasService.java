package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import ob.debitos.simp.model.criterio.CriterioBusquedaExtornosControversias;
import ob.debitos.simp.model.reporte.ReporteExtornosControversias;

public interface IReporteExtornosControversiasService
{

    public List<ReporteExtornosControversias> buscar(CriterioBusquedaExtornosControversias criterio);

    public void descargarReporteExtornosControversias(List<ReporteExtornosControversias> list, CriterioBusquedaExtornosControversias criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
