package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteTarifarioMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarifario;
import ob.debitos.simp.model.reporte.ReporteTarifario;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IReporteTarifarioService;
import ob.debitos.simp.utilitario.ReporteUtil;

@Service
public class ReporteTarifarioService implements IReporteTarifarioService
{
    private @Autowired IReporteTarifarioMapper reporteTarifarioMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteTarifario> buscarTarifarios(CriterioBusquedaTarifario criterioBusqueda)
    {
        criterioBusqueda.setVerbo(
                ReporteUtil.obtenerVerboPorTipoTarifario(criterioBusqueda.getTipoTarifario()));
        criterioBusqueda.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        return reporteTarifarioMapper.reporte(criterioBusqueda);
    }
}