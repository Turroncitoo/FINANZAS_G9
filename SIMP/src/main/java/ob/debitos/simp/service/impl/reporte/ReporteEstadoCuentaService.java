package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IReporteEstadoCuentaMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoCuenta;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.reporte.ReporteEstadoCuentaPrepago;
import ob.debitos.simp.service.IReporteEstadoCuentaService;

@Service
public class ReporteEstadoCuentaService implements IReporteEstadoCuentaService {

	private @Autowired IReporteEstadoCuentaMapper reporteEstadoCuentaMapper;
	@Override
    public List<ReporteEstadoCuentaPrepago> buscarPorCriterio(CriterioBusquedaEstadoCuenta criterioBusquedaEstadoCuenta)
    {
        return reporteEstadoCuentaMapper.buscarPorCriterio(criterioBusquedaEstadoCuenta);
    }
    
    @Override
    public List<ReporteEstadoCuentaPrepago> buscarPorNumeroDocumento(
            CriterioBusquedaTipoDocumentoPrepago criterioBusquedaEstadoCuenta)
    {
        return reporteEstadoCuentaMapper.buscarPorNumeroDocumento(criterioBusquedaEstadoCuenta);
    }
	
	
	
}
