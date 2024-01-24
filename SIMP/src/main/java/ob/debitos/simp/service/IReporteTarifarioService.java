package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaTarifario;
import ob.debitos.simp.model.reporte.ReporteTarifario;

public interface IReporteTarifarioService
{
    public List<ReporteTarifario> buscarTarifarios(
            CriterioBusquedaTarifario criterioBusqueda);
}
