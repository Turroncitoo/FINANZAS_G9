package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaExtornosControversias;
import ob.debitos.simp.model.reporte.ReporteExtornosControversias;

public interface IExtornosControversiasMapper
{

    public List<ReporteExtornosControversias> buscar(CriterioBusquedaExtornosControversias criterio);

}
